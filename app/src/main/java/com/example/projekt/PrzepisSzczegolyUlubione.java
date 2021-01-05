package com.example.projekt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;

public class PrzepisSzczegolyUlubione  extends AppCompatActivity {

    ImageView IVobrazek;
    TextView TVnazwa,TVskladniki,TVsposobPrzygotowania,TVocena,TVautor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepis_szczegoly);

        IVobrazek=findViewById(R.id.obrazek);
        TVnazwa=findViewById(R.id.nazwa);
        TVskladniki=findViewById(R.id.skladniki);
        TVsposobPrzygotowania=findViewById(R.id.sposobPrzygotowania);
        TVautor=findViewById(R.id.autor);


        Intent intent = getIntent();
        String message = intent.getStringExtra("nazwaDania");
       // String message2 = intent.getStringExtra(Ulubione.)

        Log.d("nazwa dania w szcz",message);



        final Query szczegolowyPrzepis = FirebaseDatabase.getInstance().getReference().child("Przepisy").child(message);

        szczegolowyPrzepis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("snapszot",snapshot.getValue().toString());
                    Log.d("datasnapszot",dataSnapshot.getValue().toString());

                    String autor = String.valueOf(dataSnapshot.child("autor").getValue());
                    String ocena = String.valueOf(dataSnapshot.child("ocena").getValue());
                    String nazwa = String.valueOf(dataSnapshot.child("nazwa").getValue());
                    String obrazek = String.valueOf(dataSnapshot.child("obrazek").getValue());
                    String skladniki = String.valueOf(dataSnapshot.child("skladniki").getValue());
                    String sposobPrzygotowania = String.valueOf(dataSnapshot.child("sposobPrzygotowania").getValue());

                    Log.d("nazwa",nazwa);



                    try {
                        URL url = new URL(obrazek);
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        IVobrazek.setImageBitmap(image);
                    }

                    catch(IOException e) {Log.e("image error",e.getMessage());}


                    TVnazwa.setText(nazwa);
                    TVskladniki.setText(skladniki);
                    TVsposobPrzygotowania.setText(sposobPrzygotowania);
                    TVautor.setText(autor);

                    Log.d("nazwa w przepisSzcz",nazwa);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("databaseError", String.valueOf(databaseError));
            }
        });

    }
}