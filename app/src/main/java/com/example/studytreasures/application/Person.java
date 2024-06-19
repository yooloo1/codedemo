package com.example.studytreasures.application;

import java.io.Serializable;

public class Person implements Serializable {
    private String id;
    private int num;
    private String username;
    private String password;
    private int money =0 ;

    public Person(String name, String password){
        this.username = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String toString(){
        String t;
        t ="{id:%s,num:%s,username:%s,password:%s,money:%s}";
        t =String.format(t,this.id,this.num,this.username,this.password,this.money);
        return t;
    }

}
