package com.example.studytreasures.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studytreasures.GoodsDialog;
import com.example.studytreasures.R;

import java.util.ArrayList;

public class BuyingListAdapter extends BaseAdapter {
    private ArrayList<Goods> list_goods ;
    private final Context context;

    public BuyingListAdapter(ArrayList<Goods> list,Context context){
        this.list_goods = list;
        this.context = context;
    }

    public void setListGoods(ArrayList<Goods> list){
        this.list_goods = list;
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
        item_view = View.inflate(this.context, R.layout.list_item_buying,null);

        ImageView imageViewPreviewBuying = item_view.findViewById(R.id.imageViewPreviewBuying);
        TextView textViewGoodsNameBuying = item_view.findViewById(R.id.textViewGoodsNameBuying);
        TextView textViewGoodsPriceBuying = item_view.findViewById(R.id.textViewGoodsPriceBuying);

        item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(context,list_goods.get(i));
                dialog.show();
            }
        });

        String t="-"+Goods.toPriceString(list_goods.get(i).getPrice());
        textViewGoodsPriceBuying.setText(t);

        textViewGoodsNameBuying.setText(list_goods.get(i).getGoodsName());

        if (list_goods.get(i).getBitmaps().size() > 0){
            imageViewPreviewBuying.setImageBitmap(list_goods.get(i).getBitmaps().get(0));
        }


        return item_view;
    }
}
