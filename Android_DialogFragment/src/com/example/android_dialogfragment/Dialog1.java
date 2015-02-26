package com.example.android_dialogfragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Dialog1 extends DialogFragment implements OnClickListener {

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
  {
    getDialog().setTitle("Super dialog!");
    View v = inflater.inflate(R.layout.dialog1, null);
    v.findViewById(R.id.yesButton).setOnClickListener(this);
    v.findViewById(R.id.noButton).setOnClickListener(this);
    v.findViewById(R.id.neutralButton).setOnClickListener(this);
    return v;
  }

  public void onClick(View v) {
    Toast.makeText(getActivity(), ((Button) v).getText(), Toast.LENGTH_SHORT).show();
    dismiss();
  }

  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    Toast.makeText(getActivity(), "Dialog 1: onDismiss", Toast.LENGTH_SHORT).show();
  }

  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    Toast.makeText(getActivity(), "Dialog 1: onCancel", Toast.LENGTH_SHORT).show();
  }
}