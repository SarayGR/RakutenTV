package com.example.rakutentv.service.events;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.rakutentv.R;
import com.example.rakutentv.model.ErrorDTO;

public class StrongErrorEvent extends GenericError {

    public StrongErrorEvent(ErrorDTO errorDTO) {
        super(errorDTO);
    }

    public void showErrorDialog(final Context context) {
        String message = "";
        if (getErrorDTO() != null) {
            if (getErrorDTO().getDescription() != null) {
                message = getErrorDTO().getDescription();
            }
            if (getErrorDTO().getMessage() != null) {
                message = message + "\n" + getErrorDTO().getMessage();
            }
        }
        if (message.equalsIgnoreCase("")) {
            message = context.getString(R.string.error_not_found_generic);
        }

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
    }
}
