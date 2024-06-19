package com.example.studytreasures.application;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.studytreasures.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Goods {
    private ArrayList<Bitmap> bitmaps;
    private String goodsName;
    private String describe;
    private ArrayList<String> tags;
    private int price;

    public static ArrayList<Goods> GOODSLIST =new ArrayList<>();
    public static Goods DEULT_GOODS = new Goods();

    public Goods(){
        this.bitmaps = new ArrayList<>();
        this.goodsName = "";
        this.describe = "";
        this.tags = new ArrayList<>();
        this.price = -1;
    }

    public Goods(String goodsName, String describe, String[] tags, int price){
        this();
        this.goodsName = goodsName;
        this.describe = describe;
        this.price = price;
        this.tags.addAll(Arrays.asList(tags));
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap>  bitmaps) {
        this.bitmaps = bitmaps;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean equals(Goods goods){
        return this.getGoodsName().equals(goods.getGoodsName())
                && this.getTags().equals(goods.getTags())
                && this.getDescribe().equals(goods.getDescribe())
                && this.getPrice() == goods.getPrice();
    }

    public static String toPriceString(int price){
        StringBuffer sb = new StringBuffer(String.valueOf(price));
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
        return sb.toString();
    }

    public static void getDefaultGoodsList(Context context){

        GOODSLIST.clear();
        Goods goods1 = new Goods("張小鳳狼毫毛笔","文房四宝",new String[]{"毛笔"},30000);
        goods1.setDescribe("張小鳳狼毫毛笔套装兼毫毛笔高级文房四宝书法毛笔专业级高档毛笔中楷高端毛笔礼盒");
        Goods goods2 = new Goods("六品堂毛笔","文房四宝",new String[]{"毛笔"},2500);
        goods2.setDescribe("六品堂毛笔兼毫大白云中楷小楷学生书法国画专用初学者入门毛笔3支装行书小白云毛笔");
        Goods goods3 = new Goods("十方清梵黑色墨汁","文房四宝",new String[]{"墨水"},550);
        goods3.setDescribe("初学者书法书大画墨液国画练习练毛笔墨水，成人初学学生用品文房四宝");
        Goods goods4 = new Goods("爱普生002墨水","文房四宝",new String[]{"墨水"},7000);
        goods4.setDescribe("爱普生（EPSON）002墨水L4158 L4168 6168 L6178 L6198 T03X1-002黑 原装");
        Goods goods5 = new Goods("一得阁宣纸","文房四宝",new String[]{"宣纸"},1100);
        goods5.setDescribe("一得阁宣纸竹浆米字格毛边纸，四尺四开半生半熟文房四宝套装毛笔墨汁书法练习纸");
        Goods goods6 = new Goods("荣宝斋书画宣纸","文房四宝",new String[]{"宣纸"},990);
        goods6.setDescribe("小楷毛笔字帖，文房四宝初学者学生毛笔墨汁书法半生半熟");
        Goods goods7 = new Goods("六品堂澄泥砚","文房四宝",new String[]{"砚台"},3980);
        goods7.setDescribe("天然原石多功能四大名砚可研墨磨墨书法用品墨碟墨池初学者墨盒国画用品书法专用毛笔墨汁砚台");
        Goods goods8 = new Goods("六品堂火锅砚","文房四宝",new String[]{"砚台"},5600);
        goods8.setDescribe("书法专用带盖双圈天然原石料砚台保湿墨池墨盒毛笔初学可磨墨多功能歙砚");

        ArrayList<Bitmap> bitmaps1 = new ArrayList<>();
        bitmaps1.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.maobi1));
        goods1.setBitmaps(bitmaps1);

        ArrayList<Bitmap> bitmaps2 = new ArrayList<>();
        bitmaps2.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.maobi2));
        goods2.setBitmaps(bitmaps2);

        ArrayList<Bitmap> bitmaps3 = new ArrayList<>();
        bitmaps3.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mo1));
        goods3.setBitmaps(bitmaps3);

        ArrayList<Bitmap> bitmaps4 = new ArrayList<>();
        bitmaps4.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mo2));
        goods4.setBitmaps(bitmaps4);

        ArrayList<Bitmap> bitmaps5 = new ArrayList<>();
        bitmaps5.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.zhi1));
        goods5.setBitmaps(bitmaps5);

        ArrayList<Bitmap> bitmaps6 = new ArrayList<>();
        bitmaps6.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.zhi2));
        goods6.setBitmaps(bitmaps6);

        ArrayList<Bitmap> bitmaps7 = new ArrayList<>();
        bitmaps7.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.yan1));
        goods7.setBitmaps(bitmaps7);

        ArrayList<Bitmap> bitmaps8 = new ArrayList<>();
        bitmaps8.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.yan2));
        goods8.setBitmaps(bitmaps8);

        GOODSLIST.add(goods1);
        GOODSLIST.add(goods2);
        GOODSLIST.add(goods3);
        GOODSLIST.add(goods4);
        GOODSLIST.add(goods5);
        GOODSLIST.add(goods6);
        GOODSLIST.add(goods7);
        GOODSLIST.add(goods8);
    }
}
