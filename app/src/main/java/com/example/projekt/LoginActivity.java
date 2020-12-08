package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    MediaPlayer mySong;

    Button btnLogowanie, btnRejestracja;
    EditText etEmail, etHaslo;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = findViewById(R.id.textView2);

        btnRejestracja = findViewById(R.id.rejestracja);
        btnLogowanie = findViewById(R.id.logowanie);
        etEmail = findViewById(R.id.email);
        etHaslo = findViewById(R.id.haslo);
        progressBar = findViewById(R.id.progressBar_logowanie);
        mFirebaseAuth = FirebaseAuth.getInstance();

        String text = "Nie masz jeszcze konta? Zarejstruj się!";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(LoginActivity.this, RejestracjaActivity.class));

            }
        };

        ss.setSpan(clickableSpan, 24, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        btnLogowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String email = etEmail.getText().toString().trim();
                String haslo = etHaslo.getText().toString().trim();

                //mySong= MediaPlayer.create(LoginActivity.this,R.raw.maintheme);
                //mySong.start();
                //mySong.setLooping(true);


                mFirebaseAuth.signInWithEmailAndPassword(email, haslo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if (task.isSuccessful()) {

                            if (mFirebaseAuth.getCurrentUser().isEmailVerified() == false) {
                                Toast.makeText(LoginActivity.this, "musisz potwierdzic adres email! ", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                return;
                            }

                            Toast.makeText(LoginActivity.this, "Zalogowany! ", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();


                        } else {
                            Toast.makeText(LoginActivity.this, "Błąd! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }
}
