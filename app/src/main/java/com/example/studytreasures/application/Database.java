package com.example.studytreasures.application;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Database {
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase database;

    public Database(MySQLiteHelper mySQLiteHelper){
        this.mySQLiteHelper = mySQLiteHelper;
    }

    public void insertPersonToSQLite(Person person){
        database = mySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",person.getId());
        values.put("num",person.getNum());
        values.put("username",person.getUsername());
        values.put("password",person.getPassword());
        values.put("money",person.getMoney());
        long id = database.insert("person",null,values);
        database.close();
    }

    public int getPersonMaxNumFromSQLite(){
        database = mySQLiteHelper.getWritableDatabase();
        int result;
        Cursor cursor = database.rawQuery("SELECT MAX(num) FROM person",null);
        if(cursor.getCount() ==0){
            return 0;
        } else {
            cursor.moveToFirst();
            result = cursor.getInt(0);
        }
        return result;
    }

    public int updatePersonToSQLite(Person person){
        database = mySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("num",person.getNum());
        values.put("username",person.getUsername());
        values.put("password",person.getPassword());
        values.put("money",person.getMoney());
        int number = database.update("person",values,"id=?",new String[]{person.getId()});
        database.close();
        return number;
    }

    public int deletePersonToSQLite(Person person){
        database = mySQLiteHelper.getWritableDatabase();
        int number = database.delete("person","id =?",new String[]{person.getId()});
        database.close();
        return number;
    }

    //此方法用来查询数据库中是否存在person
    //优先查询id，id==null，查询username
    //password为空时返回第一个username的信息 不为空时匹配用户名和密码
    //当查询不到时返回null
    public Person findPersonFromSQLite(String id,String username,String password){
        database = mySQLiteHelper.getReadableDatabase();
        Person person = new Person(username,null);
        Cursor cursor ;
        if(id != null && (!id.isEmpty())){
            cursor = database.query("person",null,"id=?",new String[]{id},null,null,null);
            if (cursor.getCount() ==0){
                database.close();
                return null;
            }
            cursor.moveToFirst();
            person.setId(cursor.getString(0));
            person.setNum(cursor.getInt(1));
            person.setUsername(cursor.getString(2));
            person.setPassword(cursor.getString(3));
            person.setMoney(cursor.getInt(4));
            cursor.close();
            database.close();
            return person;

        } else if(username != null && (!username.isEmpty())){
            cursor = database.query("person",null,"username=?",new String[]{username},null,null,null);
            if (cursor.getCount() ==0){
                database.close();
                return null;
            } else if(cursor.getCount() ==1){
                cursor.moveToFirst();
                if (password != null){
                    if (password.equals(cursor.getString(3))){  //如果密码相等
                        person.setId(cursor.getString(0));
                        person.setNum(cursor.getInt(1));
                        person.setUsername(cursor.getString(2));
                        person.setPassword(cursor.getString(3));
                        person.setMoney(cursor.getInt(4));
                    } else {    //如果密码不相等
                        person = null;
                    }
                } else {
                    person.setId(cursor.getString(0));
                    person.setNum(cursor.getInt(1));
                    person.setUsername(cursor.getString(2));
                    person.setPassword(cursor.getString(3));
                    person.setMoney(cursor.getInt(4));
                }
                cursor.close();
                database.close();
                return person;
            } else {
                cursor.moveToFirst();
                if (cursor.getString(3).equals(password)){
                    person.setId(cursor.getString(0));
                    person.setNum(cursor.getInt(1));
                    person.setUsername(cursor.getString(2));
                    person.setPassword(cursor.getString(3));
                    person.setMoney(cursor.getInt(4));
                } else {
                    boolean isExist = false;
                    while(cursor.moveToNext()){
                        if (cursor.getString(3).equals(password)){
                            cursor.moveToFirst();
                            person.setId(cursor.getString(0));
                            person.setNum(cursor.getInt(1));
                            person.setUsername(cursor.getString(2));
                            person.setPassword(cursor.getString(3));
                            person.setMoney(cursor.getInt(4));
                            isExist = true;
                            break;
                        }
                    }
                    if(!isExist) {
                        person = null;
                    }
                }
                cursor.close();
                database.close();
                return person;
            }

        }else {
            database.close();
            return null;
        }
    }

    public void insertGoodsToSQLite(Person person,Goods goods,long time){
        database = mySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",person.getId());
        values.put("goodsName",goods.getGoodsName());
        values.put("time",time);
        long id = database.insert("shopping",null,values);
        database.close();
    }

    public int deleteGoodsToSQLite(Person person,Goods goods){
        database = mySQLiteHelper.getWritableDatabase();
        int number = database.delete("shopping","id =? and goodsName =?",new String[]{person.getId(),goods.getGoodsName()});
        database.close();
        return number;
    }

    public ArrayList<String> findGoodsListFromSQLite(Person person){
        database = mySQLiteHelper.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.query("shopping",null,"id=?",new String[]{person.getId()},null,null,null);
        if (cursor.getCount() == 0){
            cursor.close();
            database.close();
            return list;
        } else {
            cursor.moveToFirst();
            list.add(new String(cursor.getString(1)));
            while (cursor.moveToNext()){
                list.add(new String(cursor.getString(1)));
            }
            cursor.close();
            database.close();
            return list;
        }
    }
}
