package com.example.projekt;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Pop extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Button buttonDodawanie;
        final EditText autor,dataDodania,obrazek,ocena;
        buttonDodawanie=findViewById(R.id.dodawanie);
        autor=findViewById(R.id.autor);
        dataDodania=findViewById(R.id.dataDodania);
        obrazek=findViewById(R.id.obrazek);
        ocena=findViewById(R.id.ocena);


        setContentView(R.layout.popwindows);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height * 0.8));


/*

        buttonDodawanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Query Referencja = FirebaseDatabase.getInstance().getReference().child("Przepisy");
               // DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Przepisy");
              //  usersRef.child("Groch z kapusta").setValue(new Przepis(obrazek.getText().toString(), autor.getText().toString(),ocena.getText().toString(),dataDodania.getText().toString()));
             //   usersRef.child("Groch z kapusta").setValueAsync(new Przepis(obrazek.getText().toString(), autor.getText().toString(),ocena.getText().toString(),dataDodania.getText().toString()));

            }
        });
     */




    }


    public void DodaniePrzepisu(View view) {

        final EditText autor,dataDodania,obrazek,ocena;
        autor=findViewById(R.id.autor);
        dataDodania=findViewById(R.id.dataDodania);
        obrazek=findViewById(R.id.obrazek);
        ocena=findViewById(R.id.ocena);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Przepisy");
        usersRef.child("Groch z kapusta").setValue(new Przepis(obrazek.getText().toString(), autor.getText().toString(),ocena.getText().toString(),dataDodania.getText().toString()));
    }
}
