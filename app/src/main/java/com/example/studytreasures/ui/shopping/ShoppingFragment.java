package com.example.studytreasures.ui.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.studytreasures.R;
import com.example.studytreasures.application.Database;
import com.example.studytreasures.application.Goods;
import com.example.studytreasures.application.MySQLiteHelper;
import com.example.studytreasures.application.Person;
import com.example.studytreasures.application.ShoppingListAdapter;
import com.example.studytreasures.databinding.FragmentShoppingBinding;
import com.example.studytreasures.ui.person.PersonFragment;
import com.example.studytreasures.ui.person.PersonViewModel;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment {

    private ShoppingViewModel shoppingViewModel;
    private FragmentShoppingBinding binding;
    private PersonViewModel personViewModel;
    private ShoppingListAdapter shoppingListAdapter;
    public static ArrayList<Goods> shoppingList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingViewModel =
                new ViewModelProvider(this).get(ShoppingViewModel.class);

        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);

        binding = FragmentShoppingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initShoppingList();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initShoppingList(){
        shoppingViewModel.setShoppingList(shoppingList);
        shoppingListAdapter = new ShoppingListAdapter(shoppingList,getContext(),binding);
        binding.listViewShopping.setAdapter(shoppingListAdapter);
        if (PersonFragment.ISLOGIN){

            shoppingViewModel.getShoppingList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Goods>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Goods> list) {
                    ShoppingFragment.shoppingList = list;
                    shoppingListAdapter.setListGoods(list);
                    binding.listViewShopping.setAdapter(shoppingListAdapter);
                }
            });


            binding.shoppingBottom.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //从购物列表中删除选中的项目
                    deleteListWithIndex(shoppingViewModel.getShoppingList().getValue(),shoppingListAdapter.getSelectList());
                    shoppingListAdapter.getSelectList().clear();
                    shoppingViewModel.setShoppingList(shoppingViewModel.getShoppingList().getValue());

                    binding.shoppingBottom.textViewAllPrice.setText("0.00");
                    Toast.makeText(ShoppingFragment.this.getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                }

            });

            binding.shoppingBottom.checkBoxAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.shoppingBottom.checkBoxAll.isChecked()){
                        shoppingListAdapter.getSelectList().clear();

                        for(int i=0;i<shoppingViewModel.getShoppingList().getValue().size();i++){
                            shoppingListAdapter.getSelectList().add(i);
                        }
                    } else {
                        shoppingListAdapter.getSelectList().clear();
                    }

                    int allPrice = 0;
                    for (int i=0;i<shoppingViewModel.getShoppingList().getValue().size();++i){
                        if (shoppingListAdapter.getSelectList().contains(i)){
                            allPrice+=shoppingViewModel.getShoppingList().getValue().get(i).getPrice();
                        }
                    }

                    binding.shoppingBottom.textViewAllPrice.setText(Goods.toPriceString(allPrice));
                    binding.listViewShopping.setAdapter(shoppingListAdapter);
                }
            });

            binding.shoppingBottom.buttonBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Person person =  personViewModel.getPerson().getValue();
                    int allPrice =  0;

                    for (int i=0;i<shoppingViewModel.getShoppingList().getValue().size();++i){
                        if (shoppingListAdapter.getSelectList().contains(i)){
                            allPrice+=shoppingViewModel.getShoppingList().getValue().get(i).getPrice();
                        }
                    }

                    final int finalAllPrice = allPrice;
                    DialogInterface.OnClickListener buyOkListener =  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int index) {
                            if (person.getMoney() < finalAllPrice){
                                Toast.makeText(getContext(),"余额不足，购买失败",Toast.LENGTH_SHORT).show();
                            } else {

                                Database database = new Database(new MySQLiteHelper(getContext()));
                                person.setMoney(person.getMoney() - finalAllPrice);
                                database.updatePersonToSQLite(person);
                                personViewModel.setPerson(person);

                                for (int i=0;i<shoppingViewModel.getShoppingList().getValue().size();++i){
                                    if (shoppingListAdapter.getSelectList().contains(i)){
                                        database.insertGoodsToSQLite(PersonFragment.person,shoppingViewModel.getShoppingList().getValue().get(i),System.currentTimeMillis());
                                        personViewModel.getBuyingList().getValue().add(shoppingViewModel.getShoppingList().getValue().get(i));
                                    }
                                }

                                personViewModel.setBuyingList(personViewModel.getBuyingList().getValue());

                                //从购物列表中删除选中的项目
                                deleteListWithIndex(shoppingViewModel.getShoppingList().getValue(),shoppingListAdapter.getSelectList());
                                shoppingListAdapter.getSelectList().clear();
                                binding.shoppingBottom.textViewAllPrice.setText("0.00");
                                shoppingViewModel.setShoppingList(shoppingViewModel.getShoppingList().getValue());

                                Toast.makeText(ShoppingFragment.this.getContext(),"购买成功",Toast.LENGTH_SHORT).show();

                            }
                        }
                    };

                    DialogInterface.OnClickListener buyCancelListener =  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(),"取消购买",Toast.LENGTH_SHORT).show();
                        }
                    };

                    String infoMeg = "您当前拥有 %s 元\n购物消费共 %s 元\n确认购买？";
                    infoMeg = String.format(infoMeg,Goods.toPriceString(person.getMoney()),Goods.toPriceString(allPrice));
                    AlertDialog dialogInfo;
                    dialogInfo = new AlertDialog.Builder(getContext()).setTitle("确认付款")
                            .setMessage(infoMeg)
                            .setIcon(R.drawable.question)
                            .setPositiveButton("确定",buyOkListener)
                            .setNegativeButton("取消",buyCancelListener)
                            .create();
                    dialogInfo.show();

                }
            });

            int allPrice = 0;
            for (int i=0;i<shoppingViewModel.getShoppingList().getValue().size();++i){
                if (shoppingListAdapter.getSelectList().contains(i)){
                    allPrice += shoppingViewModel.getShoppingList().getValue().get(i).getPrice();
                }
            }
            binding.shoppingBottom.textViewAllPrice.setText(Goods.toPriceString(allPrice));
        }
    }

    private void deleteListWithIndex(ArrayList<Goods> list,ArrayList<Integer> indexList){
        for (int i=0;i<list.size();i++) {
            if (indexList.contains(i)) {
                list.set(i, Goods.DEULT_GOODS);
            }
        }

        for (int i=0;i<indexList.size();i++){
            for (int j=0;j<list.size();j++){
                if (list.get(j).equals(Goods.DEULT_GOODS)){
                    list.remove(j);
                    break;
                }
            }
        }

    }
}