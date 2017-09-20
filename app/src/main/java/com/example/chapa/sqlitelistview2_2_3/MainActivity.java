package com.example.chapa.sqlitelistview2_2_3;


import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    FloatingActionButton fab,fabb,fabup;
    DBAdapter bd;
    int idAux[];

    AdaptadorListView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.lista);
        fab=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        fabb=(FloatingActionButton)findViewById(R.id.fabB);
        fabup=(FloatingActionButton)findViewById(R.id.fabUp);

        eventoFab();

        bd=new DBAdapter(getApplicationContext());

        llenarLista();
        eventoList();


    }

    private void eventoList(){
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView adapterView, View view,int i, long l) {
                fabb.setVisibility(View.VISIBLE);
                fabup.setVisibility(View.VISIBLE);
                new CountDownTimer(3000, 1000){
                    public void onTick(long millisUntilFinished){}
                    public  void onFinish(){
                        fabb.setVisibility(View.INVISIBLE);
                        fabup.setVisibility(View.INVISIBLE);
                    }
                }.start();
                fabb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bd.open();
                        if(bd.deleteContactById(0)){
                            Toast.makeText(getApplicationContext(),"Eliminado",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        }
                        bd.close();
                        llenarLista();
                    }
                });
                fabup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Actualizar",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void eventoFab(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this, Add_Upd.class);
                startActivity(in);
            }
        });
    }

    private void llenarLista(){
        bd.open();

        int n=bd.lengthQuery();
        int ids []= new int[n];
        String [] nombre = new String[n];
        String [] email = new String[n];
        String [] telefono = new String[n];

        int i=0;
        Cursor result=bd.getAllContacts();
        result.moveToFirst();
        while (!result.isAfterLast()) {
            int id = result.getInt(0);
            String name=result.getString(1);
            String email1=result.getString(2);
            String phone=result.getString(3);

            ids[i]=id;
            nombre[i]=name;
            email[i]=email1;
            telefono[i]=phone;

            i++;

            result.moveToNext();
        }
        result.close();
        idAux=ids;
        adapter = new AdaptadorListView(this, nombre,email,telefono);
        lista.setAdapter(adapter);

        bd.close();
    }

}
