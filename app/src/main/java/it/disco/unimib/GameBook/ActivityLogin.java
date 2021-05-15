package it.disco.unimib.GameBook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import it.disco.unimib.GameBook.databinding.ActivityMainLoginBinding;

public class ActivityLogin extends AppCompatActivity {
    ActivityMainLoginBinding binding;
    //SignInButton sign_in_button;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_login);

        binding = ActivityMainLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();

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


    }
    /*
    private void signIn() {
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
            Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
            startActivity(intent);
            Log.v("Error", "FANCULO2");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.v("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }


    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth auth doodle account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)

                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: Logged in");
                        //get loggged in user
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        //get user info
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        //check id user is new or exsisting
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            Log.d(TAG, "onSuccess: Account created" +email);
                            Toast.makeText(ActivityLogin.this, "Account created"+email, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.d(TAG, "onSuccess: Existing User" + email);
                            Toast.makeText(ActivityLogin.this, "onSuccess: Existing User"+email, Toast.LENGTH_SHORT).show();
                        }

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