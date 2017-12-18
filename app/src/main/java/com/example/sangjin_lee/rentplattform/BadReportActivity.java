package com.example.sangjin_lee.rentplattform;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BadReportActivity extends AppCompatActivity {
    @BindView(R.id.title_bad_report)
    EditText reportTitle;

    @BindView(R.id.content_bad_report)
    EditText reportContent;

    private DatabaseReference mUserReference;
    private DatabaseReference mReportReference;
    static public String mUserKey;
    Intent intent;
    String googleUid;
    User current_user;
    ArrayList<report> ReportList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_report);

        ButterKnife.bind(this);
        intent = getIntent();





        googleUid = getIntent().getStringExtra("googleid");
        mUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(googleUid);


        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    mUserKey = dataSnapshot.getChildren().iterator().next().getKey().toString();

                    for(DataSnapshot currentUserdataSnapshot : dataSnapshot.getChildren()) {
                        current_user = currentUserdataSnapshot.getValue(User.class);
                    }

                    mReportReference = FirebaseDatabase.getInstance().getReference().child("BadReports")/*.child(mUserKey)*/;
                    mReportReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot applicationdataSnapshot : dataSnapshot.getChildren()) {
                                report tempReport= applicationdataSnapshot.getValue(report.class);
                                ReportList.add(tempReport);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Attach a listener to read the data at our posts reference
 /*       mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               final User tempUser = dataSnapshot.getValue(User.class);
                System.out.println(tempUser);
                user.setUserAccount(tempUser.getUserAccount());
                user.setUserEmail(tempUser.getUserEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


*/
    }

    @OnClick(R.id.send_bad_report)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BadReportActivity.this);
        builder.setTitle("Confirmation Send!").
                setMessage("You sure, that you want to Send?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = reportTitle.getText().toString();
                        String Descriptor = reportContent.getText().toString();

                        if(!name.isEmpty() && !Descriptor.isEmpty()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c1 = Calendar.getInstance();
                            String strToday = sdf.format(c1.getTime());
                            report reportItem = new report(name, Descriptor, current_user.getUserEmail(), false, strToday);
                            ReportList.add(reportItem);
                            //mApplicationReference.setValue(enrollItem);
                            mReportReference.push().setValue(reportItem);
                            Toast toast = Toast.makeText(BadReportActivity.this, "신청이 완료되었습니다.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            finish();
                        }

                        else {
                            Toast toast = Toast.makeText(BadReportActivity.this, "정보를 모두 입력해주세요.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

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
