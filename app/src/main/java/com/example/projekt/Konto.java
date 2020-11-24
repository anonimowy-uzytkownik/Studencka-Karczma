package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Konto extends AppCompatActivity {

    Button buttonUlubione,buttonPrzepisy,buttonKupony,buttonEmail;
    TextView textViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);
        buttonUlubione=findViewById(R.id.buttonUlubione);
        buttonPrzepisy=findViewById(R.id.buttonPrzepisy);
        buttonKupony=findViewById(R.id.buttonKupony);
        buttonEmail=findViewById(R.id.buttonChangeEmail);
        textViewEmail=findViewById(R.id.textViewEmail);
        ListView listView = findViewById(R.id.listView1);
        final ArrayList<Przepis> przepisList = new ArrayList<>();
        final PrzepisListAdapter adapter= new PrzepisListAdapter(this,R.layout.adapter_view_przepis,przepisList);
        listView.setAdapter(adapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        textViewEmail.setText(user.getEmail());

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

        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        przepisList.clear();
        final Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy");
        obrazek.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    String autor = String.valueOf(snapshot.child("autor").getValue());
                    String dataDodania = String.valueOf(snapshot.child("dataDodania").getValue());
                    String obrazek = String.valueOf(snapshot.child("obrazek").getValue());
                    String ocena = String.valueOf(snapshot.child("ocena").getValue());
                    String nazwa = String.valueOf(snapshot.child("nazwa").getValue());

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(autor.equals(user.getEmail()))
                    {
                    Przepis a = new Przepis(obrazek,autor,ocena,dataDodania,nazwa);

                    przepisList.add(a);

                    adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




}}
