package com.example.sangjin_lee.rentplattform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckReportActivity extends AppCompatActivity {

    @BindView(R.id.content_check_report)
    ListView report_list;

    private FirebaseAuth mAtuth;
    private DatabaseReference mWidthDrawApplicationReference;
    private DatabaseReference mReportReference;
    private DatabaseReference mUserReference;
    Intent intent;
    String googleUid;
    User current_user;
    ArrayList<report> reportList;
    ArrayList<report> myList = new ArrayList<>();
    ArrayList<CheckReportActivity> checkReportList = new ArrayList<>();
    String mUserKey;
    myReportAdapter adbmyreports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);


        ButterKnife.bind(this);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        mAtuth = FirebaseAuth.getInstance();
        intent = getIntent();
        reportList = new ArrayList<report>();
        reportList = (ArrayList<report>)intent.getSerializableExtra("itemlist");





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
                            for (DataSnapshot reportdataSnapshot : dataSnapshot.getChildren()) {
                                report tempReport = reportdataSnapshot.getValue(report.class);
                                if(tempReport.getuserId().equals(current_user.getUserEmail())) {
                                    myList.add(tempReport);
                                }
                            }

                            adbmyreports = new myReportAdapter(CheckReportActivity.this, android.R.layout.simple_list_item_1, myList) ;
                            report_list.setAdapter(adbmyreports) ;

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


}
