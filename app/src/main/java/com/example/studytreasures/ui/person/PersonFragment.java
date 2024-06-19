package com.example.studytreasures.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.studytreasures.RegisteredActivity;
import com.example.studytreasures.application.BuyingListAdapter;
import com.example.studytreasures.application.Database;
import com.example.studytreasures.application.Goods;
import com.example.studytreasures.application.MySQLiteHelper;
import com.example.studytreasures.application.Person;
import com.example.studytreasures.databinding.FragmentPersonBinding;

import java.util.ArrayList;

public class PersonFragment extends Fragment {

    private PersonViewModel personViewModel;
    private FragmentPersonBinding binding;
    public static boolean  ISLOGIN = false;
    public static int REQUESTCODE_NEWPERSON = 1;
    public static Person person;
    private ArrayList<Goods> goodsArrayList = new ArrayList<>();
    private BuyingListAdapter buyingListAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personViewModel =
                new ViewModelProvider(this).get(PersonViewModel.class);

        binding = FragmentPersonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (!ISLOGIN){
            binding.linearLayoutLogin.setVisibility(View.VISIBLE);
            binding.linearLayoutLoginSuccess.setVisibility(View.GONE);
        } else
        {
            binding.linearLayoutLogin.setVisibility(View.GONE);
            binding.linearLayoutLoginSuccess.setVisibility(View.VISIBLE);

            goodsArrayList.clear();
            Database database = new Database(new MySQLiteHelper(getContext()));
            ArrayList<String> goodsNameList = database.findGoodsListFromSQLite(person);
            for (String t:goodsNameList
            ) {
                for (Goods goods:Goods.GOODSLIST
                ) {
                    if (t.equals(goods.getGoodsName())){
                        goodsArrayList.add(goods);
                    }
                }
            }

        }


        personViewModel.getPerson().observe(getViewLifecycleOwner(), new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person person) {
                if (person != null){
                    PersonFragment.person = person;

                    binding.includeLogin.editTextUsername.setText(person.getUsername());
                    binding.includeLogin.editTextPassword.setText(person.getPassword());

                    binding.includeLoginSuccess.textViewPersonName.setText(person.getUsername());

                    //购物清单也要跟着变
                    Database database = new Database(new MySQLiteHelper(getContext()));
                    ArrayList<String> goodsNameList = database.findGoodsListFromSQLite(person);
                    ArrayList<Goods> goodsArrayList = new ArrayList<>();
                    for (String t:goodsNameList
                         ) {
                        for (Goods goods:Goods.GOODSLIST
                             ) {
                            if (t.equals(goods.getGoodsName())){
                                goodsArrayList.add(goods);
                            }
                        }
                    }
                    personViewModel.setBuyingList(goodsArrayList);
                }
            }
        });

        personViewModel.setBuyingList(goodsArrayList);
        buyingListAdapter = new BuyingListAdapter(goodsArrayList,getContext());
        binding.includeLoginSuccess.buyingListView.setAdapter(buyingListAdapter);

        personViewModel.getBuyingList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Goods>>(){
            @Override
            public void onChanged(ArrayList<Goods> list) {
                buyingListAdapter.setListGoods(list);
                binding.includeLoginSuccess.buyingListView.setAdapter(buyingListAdapter);
            }
        });


        initLayoutLogin();
        initLayoutLoginSuccess();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
//        String result = (personViewModel.getPerson().getValue() == null)?"null":personViewModel.getPerson().getValue().toString();
//        Log.i("TAG", "onDestroyView: "+result);
    }


    public void initLayoutLogin(){
        if (personViewModel.getPerson().getValue() != null){
            binding.includeLogin.editTextUsername.setText(personViewModel.getPerson().getValue().getUsername());
        }

        binding.includeLogin.buttonRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentStartRegistered = new Intent(getActivity(), RegisteredActivity.class);
                startActivityForResult(intentStartRegistered,REQUESTCODE_NEWPERSON);
            }
        });
        binding.includeLogin.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.includeLogin.editTextUsername.getText().toString().trim();
                String password = binding.includeLogin.editTextPassword.getText().toString().trim();

                if (userName.isEmpty()){
                    Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()){
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                Database database = new Database(new MySQLiteHelper(getContext()));
                Person person = database.findPersonFromSQLite(null,userName,password);
                if (person == null){
                    Toast.makeText(getContext(), "用户名或密码错误，请核对后重新输入", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    personViewModel.setPerson(person);
                    binding.includeLoginSuccess.textViewPersonName.setText(personViewModel.getPerson().getValue().getUsername());
                    StringBuffer sb = new StringBuffer(String.valueOf(personViewModel.getPerson().getValue().getMoney()));
                    //小于2位数
                    if (sb.length()<=2)
                    {
                        if (sb.length() == 0){
                            sb.insert(0,"0.00");
                        } else if(sb.length() == 1){
                            sb.insert(0,"0.0");
                        } else if(sb.length() == 2){
                            sb.insert(0,"0.");
                        }
                    } else {
                        sb.insert(sb.length()-2,'.');
                    }
                    binding.includeLoginSuccess.textViewPersonMoney.setText(sb.toString());
                    ISLOGIN = true;
                    binding.linearLayoutLogin.setVisibility(View.GONE);
                    binding.linearLayoutLoginSuccess.setVisibility(View.VISIBLE);

                    goodsArrayList.clear();
                    ArrayList<String> goodsNameList = database.findGoodsListFromSQLite(person);
                    for (String t:goodsNameList
                    ) {
                        for (Goods goods:Goods.GOODSLIST
                        ) {
                            if (t.equals(goods.getGoodsName())){
                                goodsArrayList.add(goods);
                            }
                        }
                    }
                    personViewModel.setBuyingList(goodsArrayList);
                    Log.i("TAG", "onClick: "+person.toString());
                }
            }
        });


    }

    public void initLayoutLoginSuccess(){
        if (personViewModel.getPerson().getValue() != null){
            binding.includeLoginSuccess.textViewPersonName.setText(personViewModel.getPerson().getValue().getUsername());

            StringBuffer sb = new StringBuffer(String.valueOf(personViewModel.getPerson().getValue().getMoney()));
            //小于2位数
            if (sb.length()<=2)
            {
                if (sb.length() == 0){
                    sb.insert(0,"0.00");
                } else if(sb.length() == 1){
                    sb.insert(0,"0.0");
                } else if(sb.length() == 2){
                    sb.insert(0,"0.");
                }
            } else {
                sb.insert(sb.length()-2,'.');
            }
            binding.includeLoginSuccess.textViewPersonMoney.setText(sb.toString());
        }

       binding.includeLoginSuccess.buttonEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.linearLayoutLoginSuccess.setVisibility(View.GONE);
                binding.linearLayoutLogin.setVisibility(View.VISIBLE);

                if (personViewModel.getPerson().getValue()!= null){
                    binding.includeLogin.editTextUsername.setText(personViewModel.getPerson().getValue().getUsername());
                    binding.includeLogin.editTextPassword.setText(personViewModel.getPerson().getValue().getPassword());
                }

                ISLOGIN = false;

            }
        });

        binding.includeLoginSuccess.buttonCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"充值成功",Toast.LENGTH_SHORT).show();
                Person person = personViewModel.getPerson().getValue();
                person.setMoney(person.getMoney()+10000);
                Database database = new Database(new MySQLiteHelper(getContext()));
                database.updatePersonToSQLite(person);
                personViewModel.setPerson(person);

                binding.includeLoginSuccess.textViewPersonMoney.setText(Goods.toPriceString(personViewModel.getPerson().getValue().getMoney()));
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PersonFragment.REQUESTCODE_NEWPERSON){
            if (resultCode == RegisteredActivity.RESULTCODE_NEWPERSON){
                Person person =(Person) data.getSerializableExtra("newPerson");
//                Navigation.findNavController(MainActivity.this.binding.getRoot()).navigate(R.id.navigation_person);
                personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
                personViewModel.setPerson(person);
//                Log.i("MainActivity", "onActivityResult: "+personViewModel.getPerson().toString());
            }
        }
    }
}