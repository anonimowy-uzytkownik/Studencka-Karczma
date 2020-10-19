package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    Button buttonUlubione,buttonKonto,buttonKupony,buttonCzyszczenie,buttonDodaniePrzepisu;
    //String[] tablica={"a","a","a","a"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView=(ListView) findViewById(R.id.listView1);
        buttonUlubione=findViewById(R.id.buttonUlubione);
        buttonKonto=findViewById(R.id.buttonKonto);
        buttonKupony=findViewById(R.id.buttonKupony);
        buttonCzyszczenie=findViewById(R.id.button);
        buttonDodaniePrzepisu=findViewById(R.id.buttonDodaniePrzepisu);


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
        Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy");

        Query autor = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(5);
        Query ocena = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(2);
        Query data_dodania = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToFirst(4);



        przepisList.clear();
        obrazek.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.i("dasda", "String");
                    
                    String autor = String.valueOf(snapshot.child("autor").getValue());
                    String dataDodania = String.valueOf(snapshot.child("dataDodania").getValue());
                    String obrazek = String.valueOf(snapshot.child("obrazek").getValue());
                    String ocena = String.valueOf(snapshot.child("ocena").getValue());

                    Przepis a = new Przepis(obrazek,autor,ocena,dataDodania);
                    przepisList.add(a);

                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewTytuł.setText(databaseError.getMessage().toString()); }});

        buttonDodaniePrzepisu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Pop.class));
            }
        });

    }

}
