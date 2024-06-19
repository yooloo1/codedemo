package com.example.studytreasures.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studytreasures.GoodsDialog;
import com.example.studytreasures.R;
import com.example.studytreasures.databinding.FragmentShoppingBinding;

import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private ArrayList<Goods> list_goods ;
    private static ArrayList<Integer> list_select = new ArrayList<>();
    private int allPrice = 0;
    private final Context context;
    private FragmentShoppingBinding binding;

    public ShoppingListAdapter(ArrayList<Goods> list, Context context, FragmentShoppingBinding binding){
        this.list_goods = list;
        this.context = context;
        this.binding = binding;
    }

    public void setListGoods(ArrayList<Goods> list){
        this.list_goods = list;
    }

    public void setSelectList(ArrayList<Integer> list){
        this.list_select = list;
    }

    public ArrayList<Integer> getSelectList(){
        return this.list_select;
    }

    @Override
    public int getCount() {
        return list_goods.size();
    }

    @Override
    public Object getItem(int i) {
        return list_goods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View item_view;
        item_view = View.inflate(this.context, R.layout.list_item_shopping,null);

        final CheckBox checkBoxSelect = item_view.findViewById(R.id.checkBoxSelect);
        ImageView imageViewPreviewShopping = item_view.findViewById(R.id.imageViewPreviewShopping);
        TextView textViewGoodsNameShopping = item_view.findViewById(R.id.textViewGoodsNameShopping);
        TextView textViewGoodsPriceShopping = item_view.findViewById(R.id.textViewGoodsPriceShopping);

        //被选中了就显示选中
        if (list_select.contains(i)){
            checkBoxSelect.setChecked(true);
        }

        checkBoxSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxSelect.isChecked()){
                    list_select.add(i);
                } else {
                    list_select.remove(Integer.valueOf(i));
                }

                allPrice = 0;

                for (int i=0;i<list_goods.size();i++){
                    if (list_select.contains(i)){
                        allPrice+=list_goods.get(i).getPrice();
                    }
                }

                binding.shoppingBottom.textViewAllPrice.setText(Goods.toPriceString(allPrice));

            }
        });


        if (list_goods.get(i).getBitmaps().size() > 0){
            imageViewPreviewShopping.setImageBitmap(list_goods.get(i).getBitmaps().get(0));
        }
        imageViewPreviewShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(context,list_goods.get(i));
                dialog.show();
            }
        });

        textViewGoodsNameShopping.setText(list_goods.get(i).getGoodsName());
        textViewGoodsNameShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(context,list_goods.get(i));
                dialog.show();
            }
        });

        textViewGoodsPriceShopping.setText(Goods.toPriceString(list_goods.get(i).getPrice()));
        textViewGoodsPriceShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(context,list_goods.get(i));
                dialog.show();
            }
        });

        return item_view;
    }
}
