package com.example.sangjin_lee.rentplattform;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sangjin-Lee on 2017-11-10.
 */


public class ItemRentActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    //대여하기
    @OnClick(R.id.rentbanner)
    public void rent () {
        Intent intent = new Intent(ItemRentActivity.this, RentActivity.class);
        startActivity(intent);
    }

    private String googleUid;

    @OnClick(R.id.confirmitem)
    public void confirmitem () {
        Intent intent = new Intent(ItemRentActivity.this, ConfirmItemActivity.class);
        startActivity(intent);
    }

    //광고배너
    @OnClick(R.id.advertise)
    public void advertise() {
        String url = "https://searchad.naver.com/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    //로그아웃
    @OnClick(R.id.logout)
    public void logoutClick () {
        // Firebase sign out
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemRentActivity.this);
        builder.setTitle("Confirmation Logout!").
                setMessage("You sure, that you want to logout?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Firebase sign out
                        mAuth.signOut();
                        // Google sign out
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        //    updateUI(null);
                                        Toast.makeText(ItemRentActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ItemRentActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        Intent i = new Intent(getApplicationContext(),
                                LoginActivity.class);
                        startActivity(i);
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

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_item_rent);
        ButterKnife.bind(this);

        googleUid = getIntent().getStringExtra("id");
        mAuth = FirebaseAuth.getInstance();
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, null /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.action_enrall:
                        Intent intent_enrall = new Intent(ItemRentActivity.this,EnrallActivity.class);
                        startActivity(intent_enrall);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.action_withdraw:
                        Intent intent_withdraw = new Intent(ItemRentActivity.this,WithdrawActivity.class);
                        startActivity(intent_withdraw);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.action_report:
                        Intent intent_report = new Intent(ItemRentActivity.this,BadReportActivity.class);
                        startActivity(intent_report);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.action_check:
                        Intent intent_check = new Intent(ItemRentActivity.this,CheckReportActivity.class);
                        startActivity(intent_check);
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }
}
