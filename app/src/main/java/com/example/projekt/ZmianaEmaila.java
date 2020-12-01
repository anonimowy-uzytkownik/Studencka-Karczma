package com.example.projekt;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ZmianaEmaila extends Activity {

    EditText editTextNowyEmail;
    Button buttonZmianaEmaila;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow_zmiana_emaila);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String staryEmail = user.getEmail();
        buttonZmianaEmaila=(Button)findViewById(R.id.buttonZmianaEmaila);
        editTextNowyEmail= findViewById(R.id.editTextNowyEmail);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.85),(int)(height * 0.3));
        final String email = editTextNowyEmail.getText().toString().trim();

        buttonZmianaEmaila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                if (TextUtils.isEmpty(email)) {
                    editTextNowyEmail.setError("Email nie może być pusty");
                    return;
                }

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(email).build();
                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Użytkownicy").child(String.valueOf(user.getEmail().hashCode()));
                mDatabase.child("email").setValue(email);

            }
        });


        final Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy");
        obrazek.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(staryEmail==dataSnapshot.child("email").getValue())
                    {
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Przepisy").child(snapshot.getKey());
                    mDatabase.child("email").setValue(email);
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}