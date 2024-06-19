package com.example.studytreasures.ui.person;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studytreasures.application.Goods;
import com.example.studytreasures.application.Person;

import java.util.ArrayList;

public class PersonViewModel extends ViewModel {

    private MutableLiveData<Person> personMutableLiveData;
    private MutableLiveData<ArrayList<Goods>> buyingListMutableLiveData;

    public PersonViewModel() {
        personMutableLiveData = new MutableLiveData<>();
        buyingListMutableLiveData = new MutableLiveData<>();
        buyingListMutableLiveData.setValue(new ArrayList<Goods>());
        personMutableLiveData.setValue(PersonFragment.person);    //从MainActivity中恢复数据
    }

    public void setPerson(Person person){
        personMutableLiveData.setValue(person);
        PersonFragment.person = person;   //防止Fragment销毁的时候数据也被清空

    }

    public LiveData<Person> getPerson() {
        return personMutableLiveData;
    }

    public void setBuyingList(ArrayList<Goods> list){
        buyingListMutableLiveData.setValue(list);
    }

    public LiveData<ArrayList<Goods>> getBuyingList() {
        return buyingListMutableLiveData;
    }
}