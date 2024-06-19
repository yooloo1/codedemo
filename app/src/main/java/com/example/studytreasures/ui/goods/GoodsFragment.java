package com.example.studytreasures.ui.goods;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.studytreasures.R;
import com.example.studytreasures.application.Goods;
import com.example.studytreasures.application.GoodsListAdapter;
import com.example.studytreasures.databinding.FragmentGoodsBinding;

import java.util.ArrayList;


public class GoodsFragment extends Fragment {

    private GoodsViewModel goodsViewModel;
    private FragmentGoodsBinding binding;
    private ArrayList<Goods> list;
    private ArrayList<Goods> resultList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goodsViewModel =
                new ViewModelProvider(this).get(GoodsViewModel.class);

        binding = FragmentGoodsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        list = new ArrayList<>();
        resultList = new ArrayList<>();

        initListView();
        initGoodsFragment();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void initGoodsFragment(){
        binding.topBar.imageButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(GoodsFragment.this.getView()).navigate(R.id.navigation_person);
            }
        });
        binding.topBar.imageButtonShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(GoodsFragment.this.getView()).navigate(R.id.navigation_shopping);
            }
        });

        binding.topBar.editTextSearch.addTextChangedListener(new TextWatcher() {
        /*
            beforeTextChanged(CharSequence s, int start, int count, int after)
                s: 修改之前的文字。
                start: 字符串中即将发生修改的位置。
                count: 字符串中即将被修改的文字的长度。如果是新增的话则为0。
                after: 被修改的文字修改之后的长度。如果是删除的话则为0。
            onTextChanged(CharSequence s, int start, int before, int count)
                s: 改变后的字符串
                start: 有变动的字符串的序号
                before: 被改变的字符串长度，如果是新增则为0。
                count: 添加的字符串长度，如果是删除则为0。
            afterTextChanged(Editable s)
                s: 修改后的文字
        */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString().trim();
                if(search.isEmpty()){
                    goodsViewModel.setListGoods(list);
                } else {
                    resultList.clear();
                    for (Goods t:list) {
                        if(t.getGoodsName().contains(search)){
                            resultList.add(t);
                        } else if (t.getTags().toString().contains(search)){
                            resultList.add(t);
                        } else if (t.getDescribe().contains(search)){
                            resultList.add(t);
                        }
                    }
                    goodsViewModel.setListGoods(resultList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initListView(){
        if (Goods.GOODSLIST.size() == 0){
            Goods.getDefaultGoodsList(getContext());
        }
        list = new ArrayList<>(Goods.GOODSLIST);
        goodsViewModel.setListGoods(list);
        final GoodsListAdapter goodsListAdapter = new GoodsListAdapter(list,this.getContext());
        binding.listViewGoods.setAdapter(goodsListAdapter);

        goodsViewModel.getListGoods().observe(getViewLifecycleOwner(), new Observer<ArrayList<Goods>>() {
            @Override
            public void onChanged(ArrayList<Goods> list) {
                goodsListAdapter.setListGoods(list);
                binding.listViewGoods.setAdapter(goodsListAdapter);
            }
        });
    }
}