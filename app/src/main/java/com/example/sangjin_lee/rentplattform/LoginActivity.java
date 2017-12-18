package com.example.sangjin_lee.rentplattform;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LoginActivity extends DialogActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {


    public static User currentUser;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 1;    // 로그인화면

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    // Write a message to the database
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private DatabaseReference mDBRef;
    private DatabaseReference mUserRef;


    private GoogleApiClient mGoogleApiClient;
    private String googleUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.sign_in_button).setOnClickListener(LoginActivity.this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mDBRef = mDB.getReference();
        mUserRef = mDBRef.child("Users");

        final FirebaseUser user = mAuth.getCurrentUser();



        // [END initialize_auth]
        if(user != null) {
            loginUser(user);
            Log.d(TAG, "이미 인증정보가 있음!!");
        } else {
            Log.d(TAG, "현재 인증정보가 없음");
        }
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mAuth = firebaseAuth;
                if (mAuth.getCurrentUser() != null) { // 인증정보가 없는 상태에서 로그인한 경우
                    Log.d(TAG, mAuth.getCurrentUser().getUid());
                    Log.d(TAG, mAuth.getCurrentUser().getEmail());
                    loginUser(mAuth.getCurrentUser());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };




    }

    // [START on_start_add_listener]
    // 사용자가
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }


    }
    // [END on_stop_remove_listener]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);


            } else {
                System.out.println(result.getStatus().getStatusMessage());
                //mAuth.removeAuthStateListener(mAuthListener);
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                //  updateUI(null);// [END_EXCLUDE]

            }
        }
    }
    // [END onactivityresult]




    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            setDatabaseUser(firebaseUser);
                        }
                        else{
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public void loginUser(final FirebaseUser firebaseUser) {

        checkDatabaseUser();
        googleUid = firebaseUser.getUid();
        Intent intent = new Intent(LoginActivity.this, ItemRentActivity.class);
        intent.putExtra("id", googleUid);
        if(getIntent().getExtras() != null)
            intent.putExtras(getIntent().getExtras());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        /*
        ClientSelector clientSelector = ClientInstance.getInstanceClient();

        //새로 등록할 유저
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        String photoUrl = firebaseUser.getPhotoUrl().toString();

        Log.i("user information", uid);
        User currentUser = new User(uid, name, email, photoUrl);
        clientSelector.uploadUserDataToServer(currentUser, getApplicationContext());
        */




    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }

    }


    public void setDatabaseUser(FirebaseUser firebaseUser) {

        final User user = new User(firebaseUser.getEmail(), 0);

        mUserRef.child(googleUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    mUserRef.child(googleUid).push().setValue(user);
                    currentUser = user;
                }
                else
                    currentUser = dataSnapshot.getChildren().iterator().next().getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void checkDatabaseUser() {


        System.out.println("providerID" + mAuth.getCurrentUser().getUid());

        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    if (dataSnapshot.child(googleUid).exists()) {
                        currentUser = dataSnapshot.child(googleUid).getChildren().iterator().next().getValue(User.class);
                    } else {
                        System.out.println("존재하지 User입니다");
                        signIn();
                    }
                } else {
                    signIn();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }
    /*
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
    */

}