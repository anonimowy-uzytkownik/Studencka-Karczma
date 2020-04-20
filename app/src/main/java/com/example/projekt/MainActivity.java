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

    TextView textViewSkładniki, textViewOpis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //super komentarz


    textViewSkładniki =findViewById(R.id.textViewSkładniki);
    textViewOpis = findViewById(R.id.textViewOpis);








       // DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("przepisy");
        Query reference = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").child("składniki");
        Query opis = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").limitToFirst(1);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                   // textViewSkładniki.setText(snapshot.getValue().toString());
                    textViewSkładniki.append(snapshot.getValue().toString()+"\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewSkładniki.setText(databaseError.getMessage().toString());
            }
        });

        opis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    // textViewSkładniki.setText(snapshot.getValue().toString());
                    textViewOpis.append(snapshot.getValue().toString()+"\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewOpis.setText(databaseError.getMessage().toString());
            }
        });

    }
}
