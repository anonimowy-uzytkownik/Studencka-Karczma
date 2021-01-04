package com.example.projekt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.AdapterView;
import android.widget.Toast;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import static android.provider.AlarmClock.EXTRA_MESSAGE;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final String [] tablica={"a","a","a","a"};
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private StorageReference mStorageRef;
    Button buttonUlubione,buttonKonto,buttonKupony,buttonCzyszczenie,buttonDodaniePrzepisu;
    Bitmap obrazekPrzepisu=null;


    TextView textViewSkładniki, textViewOpis,textViewTytuł, textViewPrzygotowanie, textViewPorcja, buttonPlayer;
    //String[] tablica={"a","a","a","a"};
    static String obrazekPrzepisuPath = null;
    MediaPlayer mySong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView=(ListView) findViewById(R.id.listView1);
        buttonUlubione=findViewById(R.id.buttonUlubione);
        buttonKonto=findViewById(R.id.buttonKonto);
        buttonKupony=findViewById(R.id.buttonKupony);
       // buttonCzyszczenie=findViewById(R.id.button);
        buttonDodaniePrzepisu=findViewById(R.id.buttonDodaniePrzepisu);
        buttonPlayer=findViewById(R.id.buttonPlayer);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        final ArrayList<Przepis> przepisList = new ArrayList<>();
        buttonUlubione.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Ulubione.class);
                startActivity(intent);
                finish();
            }
        });
        buttonKonto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Konto.class);
                startActivity(intent);
                finish();

            }
        });


        buttonKupony.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Kupony.class);
                startActivity(intent);
                finish();
            }
        });

        buttonKupony.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Kupony.class);
                startActivity(intent);
                finish();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(MainActivity.this, "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), "Clicked at id = " + id, Toast.LENGTH_SHORT).show();

                Log.d("position in MainAct", String.valueOf(position));
                Intent intent = new Intent(MainActivity.this, PrzepisSzczegoly.class);
                intent.putExtra(EXTRA_MESSAGE, String.valueOf(position));
                startActivity(intent);

            }
        });





        final PrzepisListAdapter adapter= new PrzepisListAdapter(this,R.layout.adapter_view_przepis,przepisList);
        mListView.setAdapter(adapter);


       // test
        final Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy");
        Query autor = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(5);
        Query ocena = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(2);
        Query data_dodania = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToFirst(4);



        przepisList.clear();
        obrazek.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               przepisList.clear();
               for (DataSnapshot snapshot : dataSnapshot.getChildren())
               {


                   Log.i("dasda", "String");

                   String autor = String.valueOf(snapshot.child("autor").getValue());
                   String dataDodania = String.valueOf(snapshot.child("dataDodania").getValue());
                   String obrazek = String.valueOf(snapshot.child("obrazek").getValue());
                   String ocena = String.valueOf(snapshot.child("ocena").getValue());
                   String nazwa = String.valueOf(snapshot.child("nazwa").getValue());

                   Przepis a = new Przepis(obrazek,autor,ocena,dataDodania,nazwa);

                   przepisList.add(a);

                   adapter.notifyDataSetChanged();
               }
           }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                textViewTytuł.setText(databaseError.getMessage().toString()); }});

        buttonDodaniePrzepisu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Pop.class));


            }
        });

    }

    public void playIT(View view) {
        if(mySong.isPlaying()){
            mySong.pause();
        }
        else
        {
            mySong.start();
        }


    }

    public void getObrazek(String nazwaObrazka) throws IOException {

        final File localFile = File.createTempFile("images", "jpg");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("images/").child(nazwaObrazka);
        Log.d("mStorageRef",mStorageRef.toString());
        mStorageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        obrazekPrzepisuPath = localFile.getPath();


                        //  Log.d("getObrazek",);
                        //  obrazekPrzepisu= BitmapFactory.decodeFile(filePath);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("getObrazek","nie udalo sie pozyskac pliku");
            }
        });
    }

}
