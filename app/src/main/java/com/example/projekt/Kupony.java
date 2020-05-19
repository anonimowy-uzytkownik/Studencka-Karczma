package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Kupony extends AppCompatActivity {
    Button buttonPrzepisy,buttonKonto,buttonUlubione;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kupony);

        ListView mListView=(ListView) findViewById(R.id.listView3);
        buttonPrzepisy=findViewById(R.id.buttonPrzepisy);
        buttonKonto=findViewById(R.id.buttonKonto);
        buttonUlubione=findViewById(R.id.buttonUlubione);


        buttonPrzepisy.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kupony.this, MainActivity.class));
            }
        });
        buttonKonto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kupony.this, Konto.class));
            }
        });
        buttonUlubione.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kupony.this, Ulubione.class));
            }
        });




    }
}
