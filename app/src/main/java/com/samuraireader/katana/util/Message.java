package com.samuraireader.katana.util;

import android.content.Context;
import android.widget.Toast;

public class Message {
    public static void show(String msj, Context contexto){
        Toast toast = Toast.makeText(contexto, msj, Toast.LENGTH_LONG);
        toast.show();
    }
}
