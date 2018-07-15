package movies.training.udacity.com.popularmovies.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import movies.training.udacity.com.popularmovies.R;

/**
 * Created by ederson.js on 14/07/2018.
 */

public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message, Boolean status){

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Set Dialog title
        alertDialog.setTitle(title);

        // Set Dialog message
        alertDialog.setMessage(message);

        if(status != null){
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        }

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }
}
