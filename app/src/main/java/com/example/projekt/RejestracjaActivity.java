package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RejestracjaActivity extends AppCompatActivity {

    EditText etEmail, etHaslo;
    Button btnRejestracja;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        mFirebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.email);
        etHaslo = findViewById(R.id.haslo);
        progressBar = findViewById(R.id.progressBar_rejestracja);
        btnRejestracja = findViewById(R.id.rejestracja);

        btnRejestracja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String haslo = etHaslo.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email jest wymagany");
                    return;
                }
                if (TextUtils.isEmpty(haslo)) {
                    etHaslo.setError("Hasło jest wymagane");
                    return;
                }
                if (etHaslo.length() < 6) {
                    etHaslo.setError("hasło musi być dłuższe niż 6 znaków!");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                mFirebaseAuth.createUserWithEmailAndPassword(email, haslo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RejestracjaActivity.this, "Konto Stworzone", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RejestracjaActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RejestracjaActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
