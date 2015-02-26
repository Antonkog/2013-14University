package com.example.android_dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Dialog2 extends DialogFragment implements OnClickListener 
{

  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("Привет!");
    builder.setPositiveButton("Да, очень!", this);
    builder.setNegativeButton("Нет, конечно!", this);
    builder.setNeutralButton("Не знаю...", this);
    builder.setMessage("Как погода, док?");
    return builder.create();
  }

  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
    case Dialog.BUTTON_POSITIVE:
    	Toast.makeText(getActivity(), "POSITIVE", Toast.LENGTH_SHORT).show();
      break;
    case Dialog.BUTTON_NEGATIVE:
    	Toast.makeText(getActivity(), "NEGATIVE", Toast.LENGTH_SHORT).show();
      break;
    case Dialog.BUTTON_NEUTRAL:
    	Toast.makeText(getActivity(), "NEUTRAL", Toast.LENGTH_SHORT).show();
      break;
    }
  }

  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    Log.d("Dialog2", "Dialog 2: onDismiss");
  }

  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    Log.d("Dialog2", "Dialog 2: onCancel");
  }
}
