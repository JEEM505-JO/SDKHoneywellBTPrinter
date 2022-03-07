package com.example.newsdkhoneywell;

import android.app.Dialog;
import android.content.DialogInterface;

import java.util.ArrayList;


import datamaxoneil.printer.DocumentEZ;
import honeywell.connection.ConnectionBase;


public class printer {

    private ConnectionBase con;
    private DocumentEZ doez;

    String fuente = "FONT0";

    private void fuente(){

        doez = new DocumentEZ(fuente);

    }

    private void imprimir(){
        try{

        }catch (Exception e){

        }

    }
}

