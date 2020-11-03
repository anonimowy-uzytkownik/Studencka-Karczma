package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Konto extends AppCompatActivity {

    Button buttonUlubione,buttonPrzepisy,buttonKupony;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);
        buttonUlubione=findViewById(R.id.buttonUlubione);
        buttonPrzepisy=findViewById(R.id.buttonPrzepisy);
        buttonKupony=findViewById(R.id.buttonKupony);

        buttonUlubione.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Konto.this, Ulubione.class);
                startActivity(intent);
                finish();
            }
        });
        buttonKupony.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Konto.this, Kupony.class);
                startActivity(intent);
                finish();

            }
        });
        buttonPrzepisy.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Konto.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

}}
