package com.example.projekt;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EdycjaPrzepisuActivity extends AppCompatActivity {

    ImageView IVobrazek;
    Button buttonEdytuj;
    TextView TVnazwa,TVskladniki,TVsposobPrzygotowania,TVocena,TVautor;
    static  String obrazekURL=null;
    private String selectedImagePath;
    static String imagePath = null;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edycja);

        IVobrazek=findViewById(R.id.obrazek);
        TVnazwa=findViewById(R.id.nazwa);
        TVskladniki=findViewById(R.id.skladniki);
        TVsposobPrzygotowania=findViewById(R.id.sposobPrzygotowania);
        buttonEdytuj=findViewById(R.id.buttonEdytuj);



        Intent intent = getIntent();
        final String message = intent.getStringExtra("nazwa");

        Log.d("position in PrzepisSzcz",message);
    //    int idPrzepisu=Integer.parseInt(message)+1;


        final Query szczegolowyPrzepis = FirebaseDatabase.getInstance().getReference().child("Przepisy").child(message);

        szczegolowyPrzepis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

                //edytowanyPrzepis.setValue(new Przepis(String.valueOf(TVautor.getText()), String.valueOf(TVocena.getText()), dzisiejszaData, String.valueOf(TVskladniki.getText()), String.valueOf(TVsposobPrzygotowania.getText()), String.valueOf(TVnazwa.getText())));
                try
                {
                    edytowanyPrzepis.child("autor").setValue(String.valueOf(TVautor.getText()));
                    edytowanyPrzepis.child("dataDodania").setValue(dzisiejszaData);
                    // edytowanyPrzepis.child("nazwa").setValue();
                    // edytowanyPrzepis.child("obrazek").setValue();
                    // edytowanyPrzepis.child("ocena").setValue();
                    edytowanyPrzepis.child("skladniki").setValue(String.valueOf(TVskladniki.getText()));
                    edytowanyPrzepis.child("sposobPrzygotowania").setValue(String.valueOf(TVsposobPrzygotowania.getText()));
                }
                catch (Error e){}

                finish();
            }
        });

        IVobrazek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);

            }
        });





    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        TVnazwa=findViewById(R.id.nazwa);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1234) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                imagePath=selectedImagePath;
                //Log.d("sciezka", selectedImagePath);
                System.out.println("Image Path : " + selectedImagePath);
                imageUri = data.getData();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                storage= FirebaseStorage.getInstance();
                storageReference= storage.getReference();
                uploadPicture(String.valueOf(String.valueOf(TVnazwa.getText())));
            }
        }

    }

    private void uploadPicture(String nazwaPliku) {

        StorageReference riversRef = storageReference.child("images/" + nazwaPliku);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                               // Log.d("obrazekURL",obrazekURL);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });


    }
}