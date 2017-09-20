package com.example.chapa.sqlitelistview2_2_3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Upd extends AppCompatActivity {

    EditText nom,tel,cor;
    Button btn;
    DBAdapter db;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        nom=(EditText)findViewById(R.id.txtNombre);
        tel=(EditText)findViewById(R.id.txtTel);
        cor=(EditText)findViewById(R.id.txtCor);
        btn=(Button)findViewById(R.id.btn);
        titulo=(TextView)findViewById(R.id.textView);
        db=new DBAdapter(this);

        int op=0,id=0;

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            op = parametros.getInt("op");
            id = parametros.getInt("id");
        }

        if(op==1){
            db.open();
            Cursor result=db.getContact(id);
            db.close();
            String name="",email="",phone="";
            result.moveToFirst();
            while (!result.isAfterLast()) {
                name=result.getString(1);
                email=result.getString(2);
                phone=result.getString(3);
                result.moveToNext();
            }
            result.close();
            final int auxid=id;
            nom.setText(name);
            tel.setText(phone);
            cor.setText(email);
            titulo.setText("Actualizar");
            btn.setText("Actualizar");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String n=nom.getText().toString();
                    String t=tel.getText().toString();
                    String c=cor.getText().toString();
                    if (!(n.isEmpty()  || t.isEmpty()  || c.isEmpty())){
                        db.open();
                        db.updateContact(auxid,n,c,t);
                        db.close();
                        Toast.makeText(getApplicationContext(),"Actualizado",Toast.LENGTH_SHORT).show();

                        Intent in=new Intent(Add_Upd.this, MainActivity.class);
                        startActivity(in);
                    }else{
                        Toast.makeText(getApplicationContext(),"Complete todos los campos",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else{
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
}
