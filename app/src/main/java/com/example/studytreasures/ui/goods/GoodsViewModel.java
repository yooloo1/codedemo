package com.example.studytreasures.ui.goods;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studytreasures.application.Goods;

import java.util.ArrayList;

public class GoodsViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Goods>> list_goods;

    public GoodsViewModel() {
        list_goods = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Goods>> getListGoods() {
        return list_goods;
    }

    public void setListGoods(ArrayList<Goods> list){
        this.list_goods.setValue(list);
    }
}