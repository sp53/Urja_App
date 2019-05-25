package com.levirs.example.slidinguppanel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class assistdialog extends AppCompatDialogFragment {
    TextView tv;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=requireActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.assist_dialog,null);
        builder.setView(view);
        tv=(TextView)(view.findViewById(R.id.load));
        assistdata.calc();
        tv.setText(assistdata.ans);
        if(assistdata.suf==1)builder.setMessage("Sufficient");
        else if(assistdata.suf==2)builder.setMessage("Use Reserved");
        else builder.setMessage("Not Sufficient");


        builder.setTitle("Load Management")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
