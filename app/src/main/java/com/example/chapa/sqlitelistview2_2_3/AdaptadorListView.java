package com.example.chapa.sqlitelistview2_2_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by chapa on 18/09/2017.
 */

public class AdaptadorListView extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] nombres;
    String[] cor;
    String[] tel;
    String[] ini;
    LayoutInflater inflater;

    public AdaptadorListView(Context context, String[] nom,String[] cor,String[] tel) {
        this.context = context;
        this.nombres = nom;
        this.cor = cor;
        this.tel = tel;
        ini=new String[nom.length];
        getIni();
    }

    public void getIni(){
        for (int i=0;i<nombres.length;i++){
            ini[i]=nombres[i].substring(0,1).toUpperCase();
        }
    }

    @Override
    public int getCount() {
        return nombres.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtNumero;
        TextView txtNombre;
        TextView txtTelefono;
        TextView txtCorreo;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_view_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtNombre = (TextView) itemView.findViewById(R.id.textView_superior);
        txtNumero = (TextView) itemView.findViewById(R.id.numero);
        txtTelefono = (TextView) itemView.findViewById(R.id.textView_inferior);
        txtCorreo = (TextView) itemView.findViewById(R.id.textView_bajo);

        // Capture position and set to the TextViews
        txtNombre.setText(nombres[position]);
        txtNumero.setText(ini[position]);
        txtCorreo.setText(cor[position]);
        txtTelefono.setText(tel[position]);

        return itemView;
    }
}
