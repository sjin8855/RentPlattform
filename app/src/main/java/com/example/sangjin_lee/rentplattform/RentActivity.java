package com.example.sangjin_lee.rentplattform;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentActivity extends AppCompatActivity {

    @BindView(R.id.content_rent)
    ListView all_item_list;

    private FirebaseAuth mAtuth;
    private DatabaseReference mRentReference;
    private DatabaseReference mItemReference;
    private DatabaseReference mUserReference;
    Intent intent;
    String googleUid;
    User current_user;
    ArrayList<item> itemList;
    ArrayList<item> allList = new ArrayList<>();
    ArrayList<rentInfo> rentsList = new ArrayList<>();
    String mUserKey;
    AllItemAdapter adbmyitems;
    String lName;
    String lType;
    String lKey;
    String available;
    String lFee;
    String startDate;
    String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);


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
                                   allList.add(tempItem);
                            }

                            adbmyitems = new AllItemAdapter(RentActivity.this, android.R.layout.simple_list_item_1, allList) ;
                            all_item_list.setAdapter(adbmyitems);
                            all_item_list.setOnItemClickListener(new RentActivity.ListClickHandler());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mRentReference = FirebaseDatabase.getInstance().getReference().child("Rents");
                    mRentReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot rentsdataSnapshot : dataSnapshot.getChildren()) {
                                rentInfo rentItem = rentsdataSnapshot.getValue(rentInfo.class);
                                rentsList.add(rentItem);
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
            TextView listName = (TextView) view.findViewById(R.id.all_display_name);
            TextView listType = (TextView) view.findViewById(R.id.all_display_type);
            TextView listKey = (TextView) view.findViewById(R.id.all_display_key);
            TextView listFee = (TextView) view.findViewById(R.id.all_display_fee);
            TextView listAvailable = (TextView) view.findViewById(R.id.all_display_availability);
            lName = listName.getText().toString();
            lType = listType.getText().toString();
            lKey = listKey.getText().toString();
            lFee = listFee.getText().toString();
            available = listAvailable.getText().toString();
        }

    }

    @OnClick(R.id.apply_button_rent)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RentActivity.this);
        builder.setTitle("Confirmation Send!").
                setMessage("You sure, that you want to Send?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!lName.isEmpty() && !lType.isEmpty() && available.equals("대여가능")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c1 = Calendar.getInstance();
                            Calendar c2 = Calendar.getInstance();
                            c2.add(c2.HOUR, 168);
                            String strToday = sdf.format(c1.getTime());
                            String dstDay = sdf.format(c2.getTime());
                            rentInfo rent_Item = new rentInfo(lName, current_user.getUserEmail(), lKey , strToday, dstDay, lFee, false, mUserKey);
                            rentsList.add(rent_Item);
                            //mApplicationReference.setValue(enrollItem);
                            mRentReference.push().setValue(rent_Item);
                            item update_item = new item(lName, current_user.getUserEmail(), (long)1, lType, lKey, lFee);
                            mItemReference = FirebaseDatabase.getInstance().getReference().child("Items").child(lKey);
                            mItemReference.setValue(update_item);

                            Toast toast = Toast.makeText(RentActivity.this, "대여가 완료되었습니다.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            finish();
                        }

                        else if(!available.equals("대여가능")){
                            Toast toast = Toast.makeText(RentActivity.this, "아이템을 빌릴 수 있는 상태가 아닙니다.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                        else {
                            Toast toast = Toast.makeText(RentActivity.this, "아이템을 클릭해주세요.", Toast.LENGTH_LONG);
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
