package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textViewOpis ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //super komentarz


    textViewOpis=findViewById(R.id.textView);










       // DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("przepisy");
        Query reference = FirebaseDatabase.getInstance().getReference().child("przepisy").child("2").child("składniki");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    textViewOpis.setText(snapshot.getValue().toString());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewOpis.setText(databaseError.getMessage().toString());
            }
        });

    }
}
