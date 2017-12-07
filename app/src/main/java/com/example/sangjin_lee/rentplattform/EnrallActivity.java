package com.example.sangjin_lee.rentplattform;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnrallActivity extends AppCompatActivity {

    @BindView(R.id.item_name)
    EditText inputEnrallItem;

    @BindView(R.id.content_enrall)
    EditText inputEnrallDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrall);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.send_enrall)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EnrallActivity.this);
        builder.setTitle("Confirmation Send!").
                setMessage("You sure, that you want to Send?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();

    }






}
