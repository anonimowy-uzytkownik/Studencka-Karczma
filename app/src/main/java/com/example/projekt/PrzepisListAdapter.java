package com.example.projekt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PrzepisListAdapter  extends ArrayAdapter<Przepis> {
    private static final String TAG = "PrzepisListAdapter";

    private Context mContext;
    int mResource;

    public PrzepisListAdapter(android.content.Context context, int resource, ArrayList<Przepis> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final String obrazek = getItem(position).getObrazek();
        final String autor = getItem(position).getAutor();
        final String ocena = getItem(position).getOcena();
        final String dataDodania = getItem(position).getDataDodania();
        final String nazwaDania = getItem(position).getNazwa();

        //Przepis przepis = new Przepis(obrazek, autor, ocena, dataDodania);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        ImageView ivObrazek = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView tvAutor=(TextView) convertView.findViewById(R.id.textView2);
        TextView tvDataDodania=(TextView) convertView.findViewById(R.id.textView4);
        Button DodawanieDoUlubionych = convertView.findViewById(R.id.dodawanieDoUlubionych);
        Button OdejmowanieZUlubionych = convertView.findViewById(R.id.odejmowanieZUlubionych);

//        Log.d("obrazek2", obrazek);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL(obrazek);
            Log.d("url",String.valueOf(url));
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ivObrazek.setImageBitmap(image);
        } catch(IOException e) {}




        DodawanieDoUlubionych.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String nickname =  user.getEmail();
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Przepisy").child(nazwaDania).child("ulubione");
                usersRef.child(String.valueOf(nickname.hashCode())).setValue(String.valueOf(nickname.hashCode()));

            }
        });



        //ivObrazek.setImageBitmap(obrazek);
        tvAutor.setText(nazwaDania);
        tvDataDodania.setText("Data dodania: "+ dataDodania);

        return convertView;

    }
}

