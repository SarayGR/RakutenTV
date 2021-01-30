package com.example.rakutentv.service.events;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.rakutentv.R;


public class ConnectionNotAvailableEvent {

    public AlertDialog showErrorDialog(Context context) {
        String message = context.getString(R.string.error_connection_not_available);

        AlertDialog dialogBase;
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.dialog_title_attention));
        dialog.setMessage(message);
        dialog.setPositiveButton(context.getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBase = dialog.show();
        return dialogBase;
    }

}
