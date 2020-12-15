package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Kupony extends AppCompatActivity {
    Button buttonPrzepisy,buttonKonto,buttonUlubione;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kupony);

        ListView mListView=(ListView) findViewById(R.id.listView3);
        final ArrayList<Kupon> kuponList = new ArrayList<>();
        buttonPrzepisy=findViewById(R.id.buttonPrzepisy);
        buttonKonto=findViewById(R.id.buttonKonto);
        buttonUlubione=findViewById(R.id.buttonUlubione);


        buttonUlubione.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Kupony.this, Ulubione.class);
                startActivity(intent);
                finish();
            }
        });
        buttonKonto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Kupony.this, Konto.class);
                startActivity(intent);
                finish();

            }
        });
        buttonPrzepisy.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Kupony.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final KuponListAdapter adapter = new KuponListAdapter(this,R.layout.adapter_view_kupony,kuponList);
        mListView.setAdapter(adapter);
        final Query obrazek = FirebaseDatabase.getInstance().getReference().child("Kupony");
        kuponList.clear();
        obrazek.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("test",dataSnapshot.getValue().toString());
                    String kodPromocyjny = String.valueOf(snapshot.child("KodPromocyjny").getValue());
                    String nazwaKuponu = String.valueOf(snapshot.child("NazwaKuponu").getValue());
                    String opis = String.valueOf(snapshot.child("Opis").getValue());
                    String restauracja = String.valueOf(snapshot.child("Restauracja").getValue());


                    Log.d("test",snapshot.child("NazwaKuponu").getValue().toString());
                    Kupon a = new Kupon(restauracja,kodPromocyjny,opis,nazwaKuponu);


                    kuponList.add(a);

                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {




            }});


    }
}
