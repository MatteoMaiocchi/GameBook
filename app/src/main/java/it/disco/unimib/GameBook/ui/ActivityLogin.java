package it.disco.unimib.GameBook.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.databinding.ActivityMainLoginBinding;
import it.disco.unimib.GameBook.utils.Constants;


public class ActivityLogin extends AppCompatActivity {
    ActivityMainLoginBinding binding;
    //SignInButton sign_in_button;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    SharedPreferences prefs;
   

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_login);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        binding = ActivityMainLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //firebaseUser = null;

        //sign_in_button = findViewById(R.id.sign_in_button);
        //sign_in_button.setSize(SignInButton.SIZE_STANDARD);
        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                switch (v.getId()) {

                    case R.id.sign_in_button:
                        signIn();
                        break;
                }

                 */

                Log.d(TAG, "onClick : begin Google SignIn");
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
        /*
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "fanculo2");
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            //Toast.makeText(ActivityLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

         */



        binding.anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "fanculo1");
                    if(firebaseUser == null){
                        anonymous();
                        Log.d(TAG, "fanculo3");
                        startActivity(new Intent(ActivityLogin.this, MainActivity.class));
                    }


        }});




    }
    public void anonymous(){
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "fanculo2");
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            //Toast.makeText(ActivityLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }
    /*
    private void signedIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            startActivity(new Intent(ActivityLogin.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            firebaseAuthWithGoogleAccount(account);
            /*
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
            }

             */
            Log.v("Error", "FANCULO");
            // Signed in successfully, show authenticated UI.
            //Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
            //startActivity(intent);
            Log.v("Error", "FANCULO2");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.v("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    @Override
    public void onBackPressed() {
        if (firebaseAuth != null)
            super.onBackPressed();
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth auth doodle account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)

                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: Logged in");
                        prefs.edit().putString(Constants.LOGIN_CREDENTIAL, account.getIdToken()).apply();
                        //get loggged in user
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        //get user info
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        Uri foto = firebaseUser.getPhotoUrl();


                        // Create a new user
                        String[] split = firebaseAuth.getCurrentUser().getEmail().split("@");
                        String username = split[0];
                        Log.d("UID", "id: " + username);


                        //check id user is new or exsisting
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            Log.d(TAG, "onSuccess: Account created" +email);
                            Toast.makeText(ActivityLogin.this, "Account created"+email, Toast.LENGTH_SHORT).show();
                            //CollectionReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("game");
                            DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", firebaseAuth.getCurrentUser().getEmail());
                            user.put("foto", firebaseAuth.getCurrentUser().getPhotoUrl().toString());
                            user.put("username", username);
                            user.put("id", firebaseAuth.getCurrentUser().getUid());
                            documentReference.set(user);


                        }
                        else{
                            Log.d(TAG, "onSuccess: Existing User" + email);
                            //Toast.makeText(ActivityLogin.this, "onSuccess: Existing User"+email, Toast.LENGTH_SHORT).show();

                        }


                        Log.d("UID", "id: " + firebaseAuth.getUid());



                        //start profile activity

                        startActivity(new Intent(ActivityLogin.this, MainActivity.class));
                        finish();
                    }



                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Loggin failed"+ e.getMessage());
                    }
                });
    }

}