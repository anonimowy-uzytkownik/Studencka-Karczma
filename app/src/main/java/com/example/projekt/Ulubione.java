package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Ulubione extends AppCompatActivity {
    Button buttonPrzepisy,buttonKonto,buttonKupony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulubione);
        ListView mListView=(ListView) findViewById(R.id.listView2);
        buttonPrzepisy=findViewById(R.id.buttonPrzepisy);
        buttonKonto=findViewById(R.id.buttonKonto);
        buttonKupony=findViewById(R.id.buttonKupony);
        final ArrayList<Przepis> przepisList = new ArrayList<>();

        buttonPrzepisy.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ulubione.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonKonto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ulubione.this, Konto.class);
                startActivity(intent);
                finish();

            }
        });
        buttonKupony.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ulubione.this, Kupony.class);
                startActivity(intent);
                finish();
            }
        });



        final PrzepisListAdapter adapter= new PrzepisListAdapter(this,R.layout.adapter_view_przepis,przepisList);
        mListView.setAdapter(adapter);

    }


}
