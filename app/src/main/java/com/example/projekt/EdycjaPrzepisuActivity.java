package com.example.projekt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EdycjaPrzepisuActivity extends AppCompatActivity {

    ImageView IVobrazek;
    Button buttonEdytuj;
    TextView TVnazwa,TVskladniki,TVsposobPrzygotowania,TVocena,TVautor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edycja);

        IVobrazek=findViewById(R.id.obrazek);
        TVnazwa=findViewById(R.id.nazwa);
        TVskladniki=findViewById(R.id.skladniki);
        TVsposobPrzygotowania=findViewById(R.id.sposobPrzygotowania);
        TVocena=findViewById(R.id.ocena);
        TVautor=findViewById(R.id.autor);
        buttonEdytuj=findViewById(R.id.buttonEdytuj);


        Intent intent = getIntent();
        final String message = intent.getStringExtra("nazwa");

        Log.d("position in PrzepisSzcz",message);
    //    int idPrzepisu=Integer.parseInt(message)+1;


        final Query szczegolowyPrzepis = FirebaseDatabase.getInstance().getReference().child("Przepisy").child(message);

        szczegolowyPrzepis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                    String autor = String.valueOf(dataSnapshot.child("autor").getValue());
                    String ocena = String.valueOf(dataSnapshot.child("ocena").getValue());
                    String nazwa = String.valueOf(dataSnapshot.child("nazwa").getValue());
                    String obrazek = String.valueOf(dataSnapshot.child("obrazek").getValue());
                    String skladniki = String.valueOf(dataSnapshot.child("skladniki").getValue());
                    String sposobPrzygotowania = String.valueOf(dataSnapshot.child("sposobPrzygotowania").getValue());


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
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("databaseError", String.valueOf(databaseError));
            }
        });


        buttonEdytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference edytowanyPrzepis = FirebaseDatabase.getInstance().getReference().child("Przepisy").child(message);

                IVobrazek=findViewById(R.id.obrazek);
                TVnazwa=findViewById(R.id.nazwa);
                TVskladniki=findViewById(R.id.skladniki);
                TVsposobPrzygotowania=findViewById(R.id.sposobPrzygotowania);
                TVocena=findViewById(R.id.ocena);
                TVautor=findViewById(R.id.autor);
                buttonEdytuj=findViewById(R.id.buttonEdytuj);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dzisiejszaData = dateFormat.format(Calendar.getInstance().getTime());

                edytowanyPrzepis.setValue(new Przepis(String.valueOf(TVautor.getText()), String.valueOf(TVocena.getText()), dzisiejszaData, String.valueOf(TVskladniki.getText()), String.valueOf(TVsposobPrzygotowania.getText()), String.valueOf(TVnazwa.getText())));

            }
        });

    }
}