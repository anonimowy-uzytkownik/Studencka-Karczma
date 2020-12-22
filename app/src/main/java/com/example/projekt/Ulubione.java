package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.projekt.MainActivity.EXTRA_MESSAGE;

public class Ulubione extends AppCompatActivity {
    Button buttonPrzepisy,buttonKonto,buttonKupony;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

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



        final PrzepisListAdapterUlubione adapter= new PrzepisListAdapterUlubione(this,R.layout.adapter_view_ulubione,przepisList);
        mListView.setAdapter(adapter);

        final Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy");

        przepisList.clear();
        obrazek.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                przepisList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    Log.i("dasda", "String");

                    String autor = String.valueOf(snapshot.child("autor").getValue());
                    String dataDodania = String.valueOf(snapshot.child("dataDodania").getValue());
                    String obrazek = String.valueOf(snapshot.child("obrazek").getValue());
                    String ocena = String.valueOf(snapshot.child("ocena").getValue());
                    String nazwa = String.valueOf(snapshot.child("nazwa").getValue());

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String nickname =  user.getEmail();
                    Boolean ulubione = snapshot.child("ulubione").child(String.valueOf(nickname.hashCode())).exists();

                    if (ulubione)
                    {
                        Przepis a = new Przepis(obrazek,autor,ocena,dataDodania,nazwa);

                        przepisList.add(a);

                        adapter.notifyDataSetChanged();
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //textViewTytuł.setText(databaseError.getMessage().toString());
            }});

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(MainActivity.this, "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), "Clicked at id = " + id, Toast.LENGTH_SHORT).show();



                String nazwaDania = adapter.getItem(position).getNazwa();
                Log.d("nazwaDania",nazwaDania);
                Intent intent = new Intent(Ulubione.this, PrzepisSzczegolyUlubione.class);
                intent.putExtra("nazwaDania", nazwaDania);
                startActivity(intent);

            }
        });

    }


}
