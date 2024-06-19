package com.example.studytreasures;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.studytreasures.application.Goods;
import com.example.studytreasures.databinding.DialogGoodsBinding;

public class GoodsDialog extends Dialog implements View.OnClickListener {
    private Goods goods;
    private Context context;
    private DialogGoodsBinding binding;
    public GoodsDialog(@NonNull Context context, Goods goods) {
        super(context);
        this.goods = goods;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogGoodsBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());

//        binding.imageViewPreviewDialog
        if (goods.getBitmaps().size() > 0){
            binding.imageViewPreviewDialog.setImageBitmap(goods.getBitmaps().get(0));
        }
        binding.getRoot().setOnClickListener(this);


        binding.textViewGoodsDescribeDialog.setText(goods.getDescribe());

        binding.textViewGoodsNameDialog.setText(goods.getGoodsName());

        binding.textViewGoodsTagsDialog.setText(goods.getTags().toString());

        binding.textViewGoodsPriceDialog.setText(Goods.toPriceString(goods.getPrice()));

    }

    @Override
    public void onClick(View view) {
        hide();
    }
}
