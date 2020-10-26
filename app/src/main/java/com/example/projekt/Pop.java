package com.example.projekt;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class Pop extends Activity {


    private String selectedImagePath;
    static String imagePath = null;
  //  public Uri imageUri;
  //  private FirebaseStorage storage;
  //  private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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

        final EditText nazwa,skladniki,sposobPrzygotowania;
        nazwa=findViewById(R.id.nazwa);
        skladniki=findViewById(R.id.skladniki);
        sposobPrzygotowania=findViewById(R.id.sposobPrzygotowania);
        Date dzisiejszaData = Calendar.getInstance().getTime();
        String obrazek = "";

        if(selectedImagePath!=null)
            obrazek=selectedImagePath;


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String nickname =  user.getEmail();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Przepisy");
        usersRef.child(nazwa.getText().toString()).setValue(new Przepis(obrazek, nickname,"5",dzisiejszaData.toString(),skladniki.getText().toString(),sposobPrzygotowania.getText().toString()));
    }


    public void DodanieZdjecia(View view) {

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 1234;
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1234) {
                Uri selectedImageUri = data.getData();

                selectedImagePath = getPath(selectedImageUri);
                imagePath=selectedImagePath;
                Log.d("sciezka", selectedImagePath);
                Log.d("sciezka", "dsasdasadsad");
                System.out.println("Image Path : " + selectedImagePath);
     /*           imageUri = data.getData();

                storage= FirebaseStorage.getInstance();
                storageReference= storage.getReference(); */
              //  uploadPicture();

            }
        }
    }
/*
    private void uploadPicture() {

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //pd.dissmiss;
                        //Snackbar.make(find)
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "nie udalo się zuploadować pliku", Toast.LENGTH_SHORT).show();
                    }
                });
    } */



    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
