package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;

public class Konto extends AppCompatActivity {

    Button buttonUlubione,buttonPrzepisy,buttonKupony,buttonZmianaHasla,buttonZmianaUsername;
    TextView textViewDisplayName;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    static  String obrazekURL=null;
    private String selectedImagePath;
    static String imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konto);
        buttonUlubione=findViewById(R.id.buttonUlubione);
        buttonPrzepisy=findViewById(R.id.buttonPrzepisy);
        buttonKupony=findViewById(R.id.buttonKupony);
        buttonZmianaUsername=findViewById(R.id.buttonChangeUsername);
        buttonZmianaHasla=findViewById(R.id.buttonChangePassword);
        final ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewDisplayName=findViewById(R.id.textViewUsername);

        ListView listView = findViewById(R.id.listView1);
        final ArrayList<Przepis> przepisList = new ArrayList<>();
        final PrzepisListAdapter adapter= new PrzepisListAdapter(this,R.layout.adapter_view_przepis,przepisList);
        listView.setAdapter(adapter);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query reference = FirebaseDatabase.getInstance().getReference().child("Użytkownicy");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(Integer.parseInt(snapshot.getKey())==user.getEmail().hashCode())
                    {Log.d("UserHash2",snapshot.getKey());
                        textViewDisplayName.setText(snapshot.child("email").getValue().toString());


                        try {
                            if(snapshot.child("avatar").getValue()==null){return;}
                            String linkToAvatar=snapshot.child("avatar").getValue().toString();
                            URL url = new URL(linkToAvatar);
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageViewAvatar.setImageBitmap(image);
                        }

                        catch(IOException e) {Log.e("image error",e.getMessage());}
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });










        buttonUlubione.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Konto.this, Ulubione.class);
                startActivity(intent);
                finish();
            }
        });
        buttonKupony.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Konto.this, Kupony.class);
                startActivity(intent);
                finish();

            }
        });
        buttonPrzepisy.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Konto.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonZmianaHasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Konto.this,ZmianaHasla.class));
            }
        });
        buttonZmianaUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   startActivity(new Intent(Konto.this,ZmianaEmaila.class));

            }
        });

        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);

            }
        });




        przepisList.clear();
        final Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy");
        obrazek.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    String autor = String.valueOf(snapshot.child("autor").getValue());
                    String dataDodania = String.valueOf(snapshot.child("dataDodania").getValue());
                    String obrazek = String.valueOf(snapshot.child("obrazek").getValue());
                    String ocena = String.valueOf(snapshot.child("ocena").getValue());
                    String nazwa = String.valueOf(snapshot.child("nazwa").getValue());

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(autor.equals(user.getEmail()))
                    {
                    Przepis a = new Przepis(obrazek,autor,ocena,dataDodania,nazwa);

                    przepisList.add(a);

                    adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                uploadPicture(String.valueOf(user.getEmail().hashCode()));
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

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Użytkownicy");
                                usersRef.child(String.valueOf(user.getEmail().hashCode())).setValue(new Uzytkownik(user.getEmail()));

                                obrazekURL = uri.toString();
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Użytkownicy").child(String.valueOf(user.getEmail().hashCode()));
                                mDatabase.child("avatar").setValue(uri.toString());

                                Log.d("obrazekURL",obrazekURL);
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


