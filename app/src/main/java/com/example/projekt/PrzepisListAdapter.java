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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PrzepisListAdapter  extends ArrayAdapter<Przepis> {
    private static final String TAG = "PrzepisListAdapter";

    private Context mContext;
    int mResource;

  /*  public PrzepisListAdapter(@NonNull android.content.Context context, int resource, @NonNull ArrayList<Przepis> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }
*/
    public PrzepisListAdapter(android.content.Context context, int resource, ArrayList<Przepis> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        String obrazek = getItem(position).getObrazek();
        String autor = getItem(position).getAutor();
        String ocena = getItem(position).getOcena();
        String dataDodania = getItem(position).getDataDodania();

        //Przepis przepis = new Przepis(obrazek, autor, ocena, dataDodania);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        ImageView ivObrazek = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView tvAutor=(TextView) convertView.findViewById(R.id.textView2);
        TextView tvOcena=(TextView) convertView.findViewById(R.id.textView3);
        TextView tvDataDodania=(TextView) convertView.findViewById(R.id.textView4);

//        Log.d("obrazek2", obrazek);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL(obrazek);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ivObrazek.setImageBitmap(image);
        } catch(IOException e) {}



        //ivObrazek.setImageBitmap(obrazek);
        tvAutor.setText(autor);
        tvOcena.setText(ocena);
        tvDataDodania.setText(dataDodania);

        return convertView;

    }
}

