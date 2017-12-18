package com.example.sangjin_lee.rentplattform;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawActivity extends AppCompatActivity {

    @BindView(R.id.content_widthdraw)
    ListView widthdraw_list;

    private FirebaseAuth mAtuth;
    private DatabaseReference mWidthDrawApplicationReference;
    private DatabaseReference mItemReference;
    private DatabaseReference mUserReference;
    Intent intent;
    String googleUid;
    User current_user;
    ArrayList<item> itemList;
    ArrayList<item> myList = new ArrayList<>();
    ArrayList<withdrawItem> widthdrawApplicationList = new ArrayList<>();
    String mUserKey;
    withdrawItem selectItem;
    myItemAdapter adbmyitems;
    String lName;
    String lType;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_withdraw);
         ButterKnife.bind(this);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

         mAtuth = FirebaseAuth.getInstance();
         intent = getIntent();
         itemList = new ArrayList<item>();
         itemList = (ArrayList<item>)intent.getSerializableExtra("itemlist");





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

                     mItemReference = FirebaseDatabase.getInstance().getReference().child("Items")/*.child(mUserKey)*/;
                     mItemReference.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             for (DataSnapshot itemdataSnapshot : dataSnapshot.getChildren()) {
                                 item tempItem = itemdataSnapshot.getValue(item.class);
                                 if(tempItem.getitemOwner().equals(current_user.getUserEmail())) {
                                     myList.add(tempItem);
                                 }
                             }

                             adbmyitems = new myItemAdapter(WithdrawActivity.this, android.R.layout.simple_list_item_1, myList) ;
                             widthdraw_list.setAdapter(adbmyitems) ;
                             widthdraw_list.setOnItemClickListener(new ListClickHandler());

                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     });

                     mWidthDrawApplicationReference = FirebaseDatabase.getInstance().getReference().child("WithdrawApplications");
                     mWidthDrawApplicationReference.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             for (DataSnapshot widthdrawapplicationdataSnapshot : dataSnapshot.getChildren()) {
                                 withdrawItem drawItem = widthdrawapplicationdataSnapshot.getValue(withdrawItem.class);
                                 widthdrawApplicationList.add(drawItem);
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

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            TextView listName = (TextView) view.findViewById(R.id.display_name);
            TextView listType = (TextView) view.findViewById(R.id.display_type);
            lName = listName.getText().toString();
            lType = listType.getText().toString();
        }

    }

    @OnClick(R.id.send_widthdraw)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WithdrawActivity.this);
        builder.setTitle("Confirmation Send!").
                setMessage("You sure, that you want to Send?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!lName.isEmpty() && !lType.isEmpty()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c1 = Calendar.getInstance();
                            String strToday = sdf.format(c1.getTime());
                            withdrawItem widthItem = new withdrawItem(lName, lType, false, current_user.getUserEmail(), strToday);
                            widthdrawApplicationList.add(widthItem);
                            //mApplicationReference.setValue(enrollItem);
                            mWidthDrawApplicationReference.push().setValue(widthItem);

                            Toast toast = Toast.makeText(WithdrawActivity.this, "신청이 완료되었습니다.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            finish();
                        }

                        else {
                            Toast toast = Toast.makeText(WithdrawActivity.this, "아이템을 클릭해주세요.", Toast.LENGTH_LONG);
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


