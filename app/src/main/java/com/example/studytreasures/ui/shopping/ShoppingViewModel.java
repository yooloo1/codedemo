package com.example.studytreasures.ui.shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studytreasures.application.Goods;

import java.util.ArrayList;

public class ShoppingViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Goods>> shoppingList;

    public ShoppingViewModel() {
        shoppingList = new MutableLiveData<>();
    }

    public void setShoppingList(ArrayList<Goods> list){
        this.shoppingList.setValue(list);
        ShoppingFragment.shoppingList = list;
    }

    public LiveData<ArrayList<Goods>> getShoppingList() {
        return shoppingList;
    }
}