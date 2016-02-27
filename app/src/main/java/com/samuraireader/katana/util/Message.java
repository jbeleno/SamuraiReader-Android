/**
 * Title: SamuraiReader Android
 * Version: 1.0
 * Author: Juan Sebastián Beleño Díaz
 * Email: jsbeleno@gmail.com
 * Date: 27/02/2016
 *
 * This is a util class that help to print data in TOAST elements
 */
package com.samuraireader.katana.util;

import android.content.Context;
import android.widget.Toast;

public class Message {
    public static void show(String msj, Context contexto){
        Toast toast = Toast.makeText(contexto, msj, Toast.LENGTH_LONG);
        toast.show();
    }
}
