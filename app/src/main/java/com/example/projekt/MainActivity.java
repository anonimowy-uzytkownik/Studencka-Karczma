package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final String [] tablica={"a","a","a","a"};

    TextView textViewSkładniki, textViewOpis,textViewTytuł, textViewPrzygotowanie, textViewPorcja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView=(ListView) findViewById(R.id.listView1);

        Przepis ziemniaki=new Przepis("jakiś obrazek","Rojex","1,6","20.04.1950");
        Przepis buraki=new Przepis("jakiś obrazek","Kotlex","1,7","2.09.1960");
        Przepis kotlet=new Przepis("jakiś obrazek","kotlarski","4,5","2.09.1960");
        Przepis kotlet1=new Przepis("jakiś obrazek","kotlarski","4,5","2.09.1960");
        ArrayList<Przepis> przepisList = new ArrayList<>();
        przepisList.add(ziemniaki);
        przepisList.add(buraki);
        przepisList.add(kotlet);
        przepisList.add(kotlet1);

       // test
        Query obrazek = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(3);

        Query autor = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(5);
        Query ocena = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(2);
        Query data_dodania = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToFirst(4);




        obrazek.addValueEventListener(new ValueEventListener() {
           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    tablica[0]=snapshot.getValue().toString()+"\n";


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewTytuł.setText(databaseError.getMessage().toString()); }});



        Przepis czarnina = new Przepis(tablica[0],tablica[1],tablica[2],tablica[3]);
        przepisList.add(czarnina);
       // test

        PrzepisListAdapter adapter= new PrzepisListAdapter(this,R.layout.adapter_view_przepis,przepisList);
        mListView.setAdapter(adapter);


   /* textViewSkładniki =findViewById(R.id.textViewSkładniki);
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
*/
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
/*
        przygotowanie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                { textViewPrzygotowanie.append(snapshot.getValue().toString()+"\n"); }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewPrzygotowanie.setText(databaseError.getMessage().toString()); }});
*/
    }
}
