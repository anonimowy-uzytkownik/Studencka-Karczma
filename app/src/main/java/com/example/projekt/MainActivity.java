package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button buttonUlubione,buttonKonto,buttonKupony,buttonCzyszczenie;
    //String[] tablica={"a","a","a","a"};

    MediaPlayer mySong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySong=MediaPlayer.create(MainActivity.this,R.raw.maintheme);
        mySong.start();
        mySong.setLooping(true);

        ListView mListView=(ListView) findViewById(R.id.listView1);
        buttonUlubione=findViewById(R.id.buttonUlubione);
        buttonKonto=findViewById(R.id.buttonKonto);
        buttonKupony=findViewById(R.id.buttonKupony);
        buttonCzyszczenie=findViewById(R.id.button);




        Przepis ziemniaki=new Przepis("jakiś obrazek","Rojex","1,6","20.04.1950");
        Przepis buraki=new Przepis("jakiś obrazek","Kotlex","1,7","2.09.1960");
        Przepis kotlet=new Przepis("jakiś obrazek","kotlarski","4,5","2.09.1960");
        Przepis kotlet1=new Przepis("jakiś obrazek","kotlarski","4,5","2.09.1960");
        final ArrayList<Przepis> przepisList = new ArrayList<>();

        przepisList.add(buraki);
        przepisList.add(kotlet);
        przepisList.add(kotlet1);

        buttonUlubione.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Ulubione.class));
            }
        });
        buttonKonto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Konto.class));
            }
        });
        buttonKupony.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Kupony.class));
            }
        });




        final PrzepisListAdapter adapter= new PrzepisListAdapter(this,R.layout.adapter_view_przepis,przepisList);
        mListView.setAdapter(adapter);


       // test
        Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy").child("Przepis1");

        Query autor = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(5);
        Query ocena = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(2);
        Query data_dodania = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToFirst(4);




        obrazek.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.i("dasda", "String");
           przepisList.clear();

  //                  Przepis a = new Przepis(dataSnapshot.child("3").child("obrazek").getValue().toString(), dataSnapshot.child("3").child("autor").getValue().toString()
   //                         , dataSnapshot.child("3").child("ocena").getValue().toString(), dataSnapshot.child("3").child("data dodania").getValue().toString());
   //                 przepisList.add(a);

                    Przepis a = new Przepis(dataSnapshot.child("Autor").getValue().toString(), dataSnapshot.child("Ocena").getValue().toString()
                            , dataSnapshot.child("NazwaPrzepisu").getValue().toString(), dataSnapshot.child("data dodania").getValue().toString());
                    przepisList.add(a);
                    Przepis b = new Przepis(dataSnapshot.child("Autor").getValue().toString(), dataSnapshot.child("Ocena").getValue().toString()
                            , dataSnapshot.child("NazwaPrzepisu").getValue().toString(), dataSnapshot.child("data dodania").getValue().toString());
                    przepisList.add(b);
                    Przepis c = new Przepis(dataSnapshot.child("Autor").getValue().toString(), dataSnapshot.child("Ocena").getValue().toString()
                            , dataSnapshot.child("NazwaPrzepisu").getValue().toString(), dataSnapshot.child("data dodania").getValue().toString());
                    przepisList.add(c);
                    Przepis d = new Przepis(dataSnapshot.child("Autor").getValue().toString(), dataSnapshot.child("Ocena").getValue().toString()
                            , dataSnapshot.child("NazwaPrzepisu").getValue().toString(), dataSnapshot.child("data dodania").getValue().toString());
                    przepisList.add(d);
                    Przepis e = new Przepis(dataSnapshot.child("Autor").getValue().toString(), dataSnapshot.child("Ocena").getValue().toString()
                            , dataSnapshot.child("NazwaPrzepisu").getValue().toString(), dataSnapshot.child("data dodania").getValue().toString());
                    przepisList.add(e);               
                    Przepis f = new Przepis(dataSnapshot.child("Autor").getValue().toString(), dataSnapshot.child("Ocena").getValue().toString()
                            , dataSnapshot.child("NazwaPrzepisu").getValue().toString(), dataSnapshot.child("data dodania").getValue().toString());
                    przepisList.add(f);
                    Przepis g = new Przepis(dataSnapshot.child("Autor").getValue().toString(), dataSnapshot.child("Ocena").getValue().toString()
                            , dataSnapshot.child("NazwaPrzepisu").getValue().toString(), dataSnapshot.child("data dodania").getValue().toString());
                    przepisList.add(g);

/*
                    Przepis b = new Przepis(dataSnapshot.child("4").child("obrazek").getValue().toString(),dataSnapshot.child("4").child("autor").getValue().toString()
                            ,dataSnapshot.child("4").child("ocena").getValue().toString(),dataSnapshot.child("4").child("data dodania").getValue().toString());
                    przepisList.add(b);
 */


                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewTytuł.setText(databaseError.getMessage().toString()); }});





       // Przepis czarnina = new Przepis(tablica[0],tablica[1],tablica[2],tablica[3]);
       // przepisList.add(czarnina);
       // test




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


    public void playIT(View view) {
        if(mySong.isPlaying()){
            mySong.pause();
        }
        else
        {
            mySong.start();
        }


    }
}
