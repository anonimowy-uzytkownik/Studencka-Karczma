package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public final String [] tablica={"a","a","a","a"};
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    private StorageReference mStorageRef;
    TextView textViewSkładniki, textViewOpis,textViewTytuł, textViewPrzygotowanie, textViewPorcja;
    Button buttonUlubione,buttonKonto,buttonKupony,buttonCzyszczenie,buttonDodaniePrzepisu;
    Bitmap obrazekPrzepisu=null;
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
        buttonCzyszczenie=findViewById(R.id.button);
        buttonDodaniePrzepisu=findViewById(R.id.buttonDodaniePrzepisu);
        mStorageRef = FirebaseStorage.getInstance().getReference();


            mySong= MediaPlayer.create(MainActivity.this,R.raw.maintheme);

           // mySong.start();
            mySong.setLooping(true);





      /*  Przepis ziemniaki=new Przepis("jakiś obrazek","Rojex","1,6","20.04.1950");
        Przepis buraki=new Przepis("jakiś obrazek","Kotlex","1,7","2.09.1960");
        Przepis kotlet=new Przepis("jakiś obrazek","kotlarski","4,5","2.09.1960");
        Przepis kotlet1=new Przepis("jakiś obrazek","kotlarski","4,5","2.09.1960");


        przepisList.add(buraki);
        przepisList.add(kotlet);
        przepisList.add(kotlet1); */

        final ArrayList<Przepis> przepisList = new ArrayList<>();
        buttonUlubione.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Ulubione.class));
            }
        });
        buttonKonto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Konto.class));
            }
        });
        buttonKupony.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Kupony.class));
            }
        });


        final PrzepisListAdapter adapter= new PrzepisListAdapter(this,R.layout.adapter_view_przepis,przepisList);
        mListView.setAdapter(adapter);


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


       // test
        final Query obrazek = FirebaseDatabase.getInstance().getReference().child("Przepisy");

        Query autor = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(5);
        Query ocena = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToLast(2);
        Query data_dodania = FirebaseDatabase.getInstance().getReference().child("przepisy").child("3").limitToFirst(4);



        przepisList.clear();
        obrazek.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    
                    String autor = String.valueOf(snapshot.child("autor").getValue());
                    String dataDodania = String.valueOf(snapshot.child("dataDodania").getValue());
                    String ocena = String.valueOf(snapshot.child("ocena").getValue());
                    String nazwa = String.valueOf(snapshot.child("nazwa").getValue());

                    String obrazek = String.valueOf(snapshot.child("obrazek").getValue());
                    //String obrazek = "https://firebasestorage.googleapis.com/v0/b/projekt-zpi-ad1f3.appspot.com/o/images%2F"+String.valueOf(snapshot.child("nazwa").getValue())+"?alt=media";
                   // Log.d("obrazek sciezka ostat",obrazek);
/*
                   try {
                        getObrazek(String.valueOf(snapshot.child("obrazek").getValue()));
                        Log.d("CZY JESTES TU","jestem tutaj");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
*/
                   // Log.d("sciezka do pliku",obrazekPrzepisuPath);
                    Przepis a = new Przepis(obrazek,nazwa,autor,dataDodania);
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
