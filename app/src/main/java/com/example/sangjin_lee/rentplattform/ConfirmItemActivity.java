package com.example.sangjin_lee.rentplattform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmItemActivity extends AppCompatActivity {

    @BindView(R.id.content_confirm_item)
    ListView rent_list;

    private DatabaseReference mRentReference;
    private DatabaseReference mUserReference;
    Intent intent;
    String googleUid;
    User current_user;
    ArrayList<rentInfo> rentList;
    ArrayList<rentInfo> myList = new ArrayList<>();
    String mUserKey;
    myrentAdapter adbmyrents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_item);

        ButterKnife.bind(this);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        intent = getIntent();
        rentList = new ArrayList<rentInfo>();
        rentList = (ArrayList<rentInfo>)intent.getSerializableExtra("itemlist");





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

                    mRentReference = FirebaseDatabase.getInstance().getReference().child("Rents")/*.child(mUserKey)*/;
                    mRentReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot rentdataSnapshot : dataSnapshot.getChildren()) {
                                rentInfo temprents = rentdataSnapshot.getValue(rentInfo.class);
                                if(temprents.getrentUser().equals(current_user.getUserEmail()) && !temprents.getreturnCheck()) {
                                    myList.add(temprents);
                                }
                            }

                            adbmyrents = new myrentAdapter(ConfirmItemActivity.this, android.R.layout.simple_list_item_1, myList) ;
                            rent_list.setAdapter(adbmyrents) ;

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
