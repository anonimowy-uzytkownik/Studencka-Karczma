package com.example.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        Przepis przepis = new Przepis(obrazek, autor, ocena, dataDodania);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);
        TextView tvObrazek =(TextView) convertView.findViewById(R.id.textView1);
        TextView tvAutor=(TextView) convertView.findViewById(R.id.textView2);
        TextView tvOcena=(TextView) convertView.findViewById(R.id.textView3);
        TextView tvDataDodania=(TextView) convertView.findViewById(R.id.textView4);

        tvObrazek.setText(obrazek);
        tvAutor.setText(autor);
        tvOcena.setText(ocena);
        tvDataDodania.setText(dataDodania);

        return convertView;

    }
}

