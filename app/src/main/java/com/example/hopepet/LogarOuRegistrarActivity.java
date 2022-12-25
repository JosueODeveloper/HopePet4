package com.example.hopepet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogarOuRegistrarActivity extends AppCompatActivity {

    private Button mLogin, mRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logar_ou_registrar);

        mLogin = findViewById(R.id.login);
        mRegistrar = findViewById(R.id.registrar);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogarOuRegistrarActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogarOuRegistrarActivity.this, RegistrarActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}