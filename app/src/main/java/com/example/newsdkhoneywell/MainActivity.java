package com.example.newsdkhoneywell;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private ListView listview;
    private List<String> name;
    MainActivity context;
    Bluetooth bluetooth;
    Button btn;
    Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        listview = (ListView)findViewById(R.id.listdevices);
        btn = (Button)findViewById(R.id.btnimprimir);
        btn1 = (Button)findViewById(R.id.activarbt);
        AlertDialog.Builder mensaje = new AlertDialog.Builder(getApplicationContext());

        bluetooth = new ViewModelProvider(this).get(Bluetooth.class);

        Observer<? super List<String>> list = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_adapter, strings);

            }
        };
        bluetooth.getDevice().observe(context,list);

        final Observer<Boolean> status = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                aBoolean.equals(bluetooth.enable);
            }
        };

        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                BluetoothAdapter adap = BluetoothAdapter.getDefaultAdapter();
                if(adap.isEnabled()){
                    System.out.println("ESTA ENCENDIDO");
                    adap.disable();
                }else{
                    adap.enable();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals(true)){
                    mensaje.setMessage("Bluetooth desactivado")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                 dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Toast.makeText(getApplicationContext(), "Solicitud Cancelada", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog dialog = mensaje.create();
                    dialog.show();

                }else{

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_adapter, name);
                }
            }
        });




    }
    public  MainActivity(){

    }

}