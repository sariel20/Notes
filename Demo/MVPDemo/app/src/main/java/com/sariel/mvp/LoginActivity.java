package com.sariel.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sariel.mvp.presenter.LoginPresenter;
import com.sariel.mvp.presenter.LoginPresenterImpl;
import com.sariel.mvp.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    LoginPresenter loginPresenter;
    LoginView loginView;
    EditText edit_username;
    EditText edit_pwd;
    Button btn_login;
    Button btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginView = this;
        loginPresenter = new LoginPresenterImpl(loginView);
        edit_username = findViewById(R.id.edit_username);
        edit_pwd = findViewById(R.id.edit_pwd);
        btn_login = findViewById(R.id.btn_login);
        btn_clear = findViewById(R.id.btn_clear);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(edit_username.getText().toString(),
                        edit_pwd.getText().toString());
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.clear();
            }
        });
    }

    @Override
    public void clear() {
        edit_username.setText("");
        edit_pwd.setText("");
    }

    @Override
    public void loginResult(Boolean result, int code) {
        if (result) {
            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loadingVis(Boolean isVis) {

    }
}
