package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;

public class PrzepisSzczegoly extends AppCompatActivity {

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
        TVocena=findViewById(R.id.ocena);
        TVautor=findViewById(R.id.autor);


        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Log.d("position in PrzepisSzcz",message);
        int idPrzepisu=Integer.parseInt(message)+1;


        final Query szczegolowyPrzepis = FirebaseDatabase.getInstance().getReference().child("Przepisy").limitToFirst(idPrzepisu);

        szczegolowyPrzepis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {


                    String autor = String.valueOf(snapshot.child("autor").getValue());
                    String ocena = String.valueOf(snapshot.child("ocena").getValue());
                    String nazwa = String.valueOf(snapshot.child("nazwa").getValue());
                    String obrazek = String.valueOf(snapshot.child("obrazek").getValue());
                    String skladniki = String.valueOf(snapshot.child("skladniki").getValue());
                    String sposobPrzygotowania = String.valueOf(snapshot.child("sposobPrzygotowania").getValue());


                    try {
                        URL url = new URL(obrazek);
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        IVobrazek.setImageBitmap(image);
                        }

                    catch(IOException e) {Log.e("image error",e.getMessage());}


                    TVnazwa.setText(nazwa);
                    TVskladniki.setText(skladniki);
                    TVsposobPrzygotowania.setText(sposobPrzygotowania);
                    TVocena.setText(ocena);
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