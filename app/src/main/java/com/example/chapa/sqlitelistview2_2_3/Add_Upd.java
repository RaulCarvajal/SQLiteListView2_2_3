package com.example.chapa.sqlitelistview2_2_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Upd extends AppCompatActivity {

    EditText nom,tel,cor;
    Button btn;
    DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        nom=(EditText)findViewById(R.id.txtNombre);
        tel=(EditText)findViewById(R.id.txtTel);
        cor=(EditText)findViewById(R.id.txtCor);
        btn=(Button)findViewById(R.id.btn);
        db=new DBAdapter(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=nom.getText().toString();
                String t=tel.getText().toString();
                String c=cor.getText().toString();
               if (!(n.isEmpty()  || t.isEmpty()  || c.isEmpty())){
                   db.open();
                   db.insertContact(n,t,c);
                   db.close();
                   Toast.makeText(getApplicationContext(),"Guardado",Toast.LENGTH_SHORT).show();

                   Intent in=new Intent(Add_Upd.this, MainActivity.class);
                   startActivity(in);
               }else{
                   Toast.makeText(getApplicationContext(),"Complete todos los campos",Toast.LENGTH_SHORT).show();
               }

            }
        });
    }
}
