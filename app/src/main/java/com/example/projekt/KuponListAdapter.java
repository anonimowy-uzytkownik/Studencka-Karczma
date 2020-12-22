package com.example.projekt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class KuponListAdapter  extends ArrayAdapter<Kupon> {

    private Context mContext;
    int mResource;
    int a;
    public KuponListAdapter(android.content.Context context, int resource, ArrayList<Kupon> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String kodKuponu = getItem(position).getKodKuponu();
        final String nazwaKuponu = getItem(position).getNazwaKuponu();
        final String opisKuponu = getItem(position).getOpisKuponu();
        final String nazwaRestauracji = getItem(position).getNazwaRestauracji();



        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        TextView tvKodKuponu=(TextView) convertView.findViewById(R.id.textViewKodKuponu);
        TextView tvNazwaKuponu=(TextView) convertView.findViewById(R.id.textViewNazwaKuponu);
        TextView tvOpisKuponu=(TextView) convertView.findViewById(R.id.textViewOpisKuponu);
        TextView tvNazwaRestauracji=(TextView) convertView.findViewById(R.id.textViewNazwaRestauracji);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tvKodKuponu.setText("kod: " + kodKuponu);
        tvNazwaKuponu.setText(nazwaKuponu);
        tvOpisKuponu.setText(opisKuponu);
        tvNazwaRestauracji.setText(nazwaRestauracji);



        return convertView;

    }
}

