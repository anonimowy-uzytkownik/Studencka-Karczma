package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView textViewSkładniki, textViewOpis,textViewTytuł, textViewPrzygotowanie, textViewPorcja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    textViewSkładniki =findViewById(R.id.textViewSkładniki);
    textViewOpis = findViewById(R.id.textViewOpis);
    textViewTytuł = findViewById(R.id.textViewTytuł);
    textViewPrzygotowanie = findViewById(R.id.textViewPrzygotowanie);
    textViewPorcja = findViewById(R.id.textViewPorcja);

    Query składniki = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").child("składniki");
    Query przygotowanie = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").child("sposób przygotowania");
    Query tytuł = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").limitToLast(1);
    Query opis = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").limitToFirst(1);
    Query porcja = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").limitToFirst(1); //Błędne! - trzeba zrobić tak, żeby wypisało porcję, ale nie wiem jeszcze jak

        składniki.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    textViewSkładniki.append(snapshot.getValue().toString()+"\n"); }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewSkładniki.setText(databaseError.getMessage().toString()); }});


        opis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                { textViewOpis.append(snapshot.getValue().toString()+"\n"); }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewOpis.setText(databaseError.getMessage().toString()); }});


        tytuł.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                { textViewTytuł.append(snapshot.getValue().toString()+"\n"); }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewTytuł.setText(databaseError.getMessage().toString()); }});

        /* na razie wypisuje opis a nie porcję
        porcja.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                { textViewPorcja.append(snapshot.getValue().toString()+"\n"); }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewPorcja.setText(databaseError.getMessage().toString()); }});
           */

        przygotowanie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                { textViewPrzygotowanie.append(snapshot.getValue().toString()+"\n"); }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewPrzygotowanie.setText(databaseError.getMessage().toString()); }});

    }
}
