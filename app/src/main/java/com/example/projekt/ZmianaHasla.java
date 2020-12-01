package com.example.projekt;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ZmianaHasla extends Activity {

    EditText editTextNoweHaslo,editTextNoweHasloPowtorzone;
    Button buttonZmianaHasla;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.popwindow_zmiana_hasla);

        buttonZmianaHasla=(Button)findViewById(R.id.buttonZmianaHasla);
        editTextNoweHaslo= findViewById(R.id.editTextNoweHaslo);
        editTextNoweHasloPowtorzone= findViewById(R.id.editTextNoweHasloPowtorzone);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.85),(int)(height * 0.3));

        buttonZmianaHasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String newPasswordTypedPowtorzone = editTextNoweHasloPowtorzone.getText().toString().trim();
                final String newPasswordTyped = editTextNoweHaslo.getText().toString().trim();

                if (TextUtils.isEmpty(newPasswordTyped)) {
                    editTextNoweHaslo.setError("Musisz podać nowe hasło!");
                    return;
                }
                if (newPasswordTyped.length() < 6) {
                    editTextNoweHaslo.setError("hasło musi być dłuższe niz 6 znaków!");
                    return;
                }

                if (!newPasswordTypedPowtorzone.equals(newPasswordTyped))
                {
                    editTextNoweHasloPowtorzone.setError("hasła muszą być identyczne!");
                    return;
                }

                user.updatePassword(newPasswordTyped);


            }
        });
    }
}
