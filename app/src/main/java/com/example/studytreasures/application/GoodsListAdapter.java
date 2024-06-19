package com.example.studytreasures.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studytreasures.GoodsDialog;
import com.example.studytreasures.R;
import com.example.studytreasures.ui.person.PersonFragment;
import com.example.studytreasures.ui.shopping.ShoppingFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GoodsListAdapter extends BaseAdapter{
    private ArrayList<Goods> list_goods;
    private final Context context;
    private boolean delay = false;

//    private final ListItemGoodsBinding listItemGoodsBinding;

    public GoodsListAdapter(ArrayList<Goods> list, Context context){
        this.list_goods = list;
        this.context = context;
//        listItemGoodsBinding =ListItemGoodsBinding.inflate(LayoutInflater.from(context));
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
        item_view = View.inflate(this.context, R.layout.list_item_goods,null);
//        View item_view = listItemGoodsBinding.getRoot();
        //设置列表的显示形式
        ImageView imageViewPreview = item_view.findViewById(R.id.imageViewPreview);
        TextView textViewGoodsName = item_view.findViewById(R.id.textViewGoodsName);
        TextView textViewGoodsTags = item_view.findViewById(R.id.textViewGoodsTags);
        TextView textViewPrice = item_view.findViewById(R.id.textViewPrice);
        final ImageButton imageButtonAdd = item_view.findViewById(R.id.imageButtonAdd);

        imageViewPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(GoodsListAdapter.this.context,list_goods.get(i));
                dialog.show();
            }
        });

        textViewGoodsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(GoodsListAdapter.this.context,list_goods.get(i));
                dialog.show();
            }
        });

        textViewGoodsTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(GoodsListAdapter.this.context,list_goods.get(i));
                dialog.show();
            }
        });

        textViewPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(GoodsListAdapter.this.context,list_goods.get(i));
                dialog.show();
            }
        });



        if (list_goods.get(i).getBitmaps().size() > 0){
            imageViewPreview.setImageBitmap(list_goods.get(i).getBitmaps().get(0));
        }
        textViewGoodsName.setText(list_goods.get(i).getGoodsName());
        textViewGoodsTags.setText(list_goods.get(i).getTags().toString());

        textViewPrice.setText(Goods.toPriceString(list_goods.get(i).getPrice()));


        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if(!PersonFragment.ISLOGIN){
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                }else{
                    if (!delay){
                        ShoppingFragment.shoppingList.add(Goods.GOODSLIST.get(i));
                        Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
                        delay = true;
                        imageButtonAdd.setImageResource(R.drawable.ok);

                        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                    delay=false;
                                    imageButtonAdd.setImageResource(R.drawable.shopping_add);
                                }
                            };
                        TimerTask task = new TimerTask(){
                            public void run() {
                                Message message = new Message();
                                mHandler.sendMessage(message);
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 1000);

                    }
                }

            }
        });
        return item_view;
    }
}
