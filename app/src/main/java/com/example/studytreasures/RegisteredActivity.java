package com.example.studytreasures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studytreasures.application.Database;
import com.example.studytreasures.application.MySQLiteHelper;
import com.example.studytreasures.application.Person;
import com.example.studytreasures.databinding.RegisteredLayoutBinding;

public class RegisteredActivity extends AppCompatActivity {
    private RegisteredLayoutBinding registeredLayoutBinding;
    public static int RESULTCODE_NEWPERSON = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registeredLayoutBinding = RegisteredLayoutBinding.inflate(getLayoutInflater());
        setContentView(registeredLayoutBinding.getRoot());

        registeredLayoutBinding.buttonCancelR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisteredActivity.this.finish();
            }
        });

        registeredLayoutBinding.buttonRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = registeredLayoutBinding.editTextUsernameR.getText().toString().trim();
                String password = registeredLayoutBinding.editTextPasswordR.getText().toString().trim();
                String password2 = registeredLayoutBinding.editTextPasswordR2.getText().toString().trim();

                if (!password.equals(password2)){
                    Toast.makeText(RegisteredActivity.this,"两次密码输入不一致!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Database database = new Database(new MySQLiteHelper(RegisteredActivity.this));
                Person person = database.findPersonFromSQLite(null,userName,password);
                if (person != null){
                    Toast.makeText(RegisteredActivity.this,"该账号已经注册过了!",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    userName = registeredLayoutBinding.editTextUsernameR.getText().toString().trim();
                    password = registeredLayoutBinding.editTextPasswordR.getText().toString().trim();

                    person = new Person(userName,password);
                    person.setNum(database.getPersonMaxNumFromSQLite()+1);
                    person.setId(String.valueOf(person.getNum()));
                    person.setMoney(0);

                    database.insertPersonToSQLite(person);
                    Toast.makeText(RegisteredActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                    Intent intentRegisterSuccess = new Intent();
                    intentRegisterSuccess.putExtra("newPerson",person);
                    setResult(RESULTCODE_NEWPERSON,intentRegisterSuccess);
                    finish();
                }
            }
        });
    }
}