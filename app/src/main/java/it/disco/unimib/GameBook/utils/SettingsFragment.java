package it.disco.unimib.GameBook.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.ui.ActivityLogin;
import it.disco.unimib.GameBook.ui.MainActivity;

import static android.app.Activity.RESULT_OK;


public class SettingsFragment extends PreferenceFragmentCompat {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient mGoogleSignInClient;
    String TAG = "SettingsFragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Boolean check = false, ok;
    NiftyDialogBuilder niftyDialogBuilder;
    private static final int REQUEST_PICK_IMAGE = 1;
    private StorageReference mStoraeRef;
    private static final int PERMISSION_FILE = 1;
    private static final int ACCESS_FILE = 2;
    private static final int ACCESS_CAMERA = 3;
    SharedPreferences prefs;





    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        google();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();//serve per aprire la fotocamera(sito stackoverflow
        StrictMode.setVmPolicy(builder.build());


        niftyDialogBuilder = NiftyDialogBuilder.getInstance(requireContext());
        if(firebaseUser != null){
            mStoraeRef = FirebaseStorage.getInstance().getReference("imageUser");
        }

        Preference photo = findPreference("photo");
        assert photo != null;
        if (!firebaseUser.isAnonymous()){
            photo.setEnabled(true);

        }
        photo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, PERMISSION_FILE);
                }else{
                    bottomSheetDialog();
                }

                return false;
            }
        });
        ListPreference night = findPreference(Constants.NIGHT);
        assert night != null;

        night.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                switch ((String) newValue){
                    case "1":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case "2":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        preference.setIcon(R.drawable.ic_baseline_nights_stay_24);
                        break;
                }
                return true;
            }
        });
        Preference log_out = findPreference(Constants.LOG_OUT);
        assert log_out != null;
        if(firebaseUser.isAnonymous()){
            log_out.setTitle("Log In");
            log_out.setIcon(R.drawable.ic_baseline_login_24);


        }
        log_out.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(firebaseUser.isAnonymous()){
                    alertLogin();
                }else{
                    alertLogout();
                }

                return false;
            }
        });

        EditTextPreference editTextPreference = findPreference(Constants.USERNAME);
        if(firebaseUser.isAnonymous()){
            editTextPreference.setEnabled(false);
        }

        db.document("users" + "/" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot != null && documentSnapshot.exists() ){
                        String username = documentSnapshot.getString("username");
                        assert editTextPreference != null;
                        editTextPreference.setText(username);
                        editTextPreference.setDefaultValue(username);
                        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                            @Override
                            public boolean onPreferenceChange(Preference preference, Object newValue) {
                                String name = (String) newValue;
                                readUser(name, editTextPreference);
                                return false;
                            }
                        });
                    }
                }
            }
        });

        Preference delete = findPreference(Constants.DELETE);
        if(firebaseUser.isAnonymous()){
            delete.setEnabled(false);
        }
        assert delete != null;
        delete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                delete();
                return false;
            }
        });


    }

    private void bottomSheetDialog(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        LinearLayout camera = bottomSheetDialog.findViewById(R.id.camera);
        LinearLayout galleria = bottomSheetDialog.findViewById(R.id.galleria);
        assert camera != null;
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri outputFileUri = getCaptureImageOutputUri();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, ACCESS_CAMERA);
                bottomSheetDialog.dismiss();
            }
        });
        assert galleria != null;
        galleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, ACCESS_FILE);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();;
    }
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = requireContext().getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       switch (requestCode){
           case PERMISSION_FILE:{
               if(!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                   for(int i = 0, len = permissions.length; i < len; i++){
                       String permission = permissions[i];
                       if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                           boolean showRationale = shouldShowRequestPermissionRationale(permission); //checkbox non chiedermelo più
                           if(!showRationale){
                               break;
                           }
                       }
                   }
               } else
               {
                   bottomSheetDialog();
               }
                break;
           }
       }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if(requestCode == ACCESS_CAMERA || requestCode == ACCESS_FILE){
                Uri imageUri = getPickImageResultUri(data);
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setActivityTitle("Ritaglia immagine")
                        .setFixAspectRatio(true)
                        .setCropMenuCropButtonTitle("Fatto")
                        .start(requireContext(), this);

            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                    uploadImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void delete(){

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(requireContext())
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setCancelable(false)
                .setDialogBackgroundColor(requireActivity().getResources().getColor(R.color.background))
                .setTitle("Eliminare?")
                .setTextGravity(Gravity.CENTER)
                .setTextColor(requireActivity().getResources().getColor(R.color.background_tv))
                .setMessage("Sei sicuro di voler eliminare l'account?")
                .addButton("Elimina", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {
                    ReAuthenticate();
                    dialog.dismiss();
                })
                .addButton("Annulla", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                    dialog.dismiss();
                });
        builder.show();

    }
    private void readUser(String name, EditTextPreference editTextPreference){
        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        String user1;
                        check = false;
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot doc: querySnapshot){
                            user1 = doc.getString("username");

                            if(user1.equals(name) & !editTextPreference.getText().equals(name)){
                                check = true;
                            }
                        }
                        if (!check){
                            editTextPreference.setText(name);
                            DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", name);
                            documentReference.set(user, SetOptions.merge());
                        }
                        alert(check);
                    }
                });

    }

    private boolean alert(boolean result){
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(requireContext())
                .setHeaderView(R.layout.negative)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setDialogBackgroundColor(requireActivity().getResources().getColor(R.color.background))
                .setTitle("Errore")
                .setTextGravity(Gravity.CENTER)
                .setTextColor(requireActivity().getResources().getColor(R.color.background_tv))
                .setMessage("Username già utilizzato, riprovare con un altro")
                .addButton("Riprova", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                    dialog.dismiss();
                });
        if (!result)
            builder = new CFAlertDialog.Builder(requireContext())
                    .setHeaderView(R.layout.positive)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                    .setDialogBackgroundColor(requireActivity().getResources().getColor(R.color.background))
                    .setTitle("Successo")
                    .setTextGravity(Gravity.CENTER)
                    .setTextColor(requireActivity().getResources().getColor(R.color.background_tv))
                    .setMessage("Username registrato correttamente")
                    .addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                        dialog.dismiss();
                    });

        builder.show();
        return result;
    }

    private void google() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    private void logout()
    {
        firebaseAuth.signOut();
        startActivity(new Intent(getActivity(), ActivityLogin.class));
        mGoogleSignInClient.signOut();
        requireActivity().finish();
    }

    private void alertLogout(){
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(requireContext())
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setDialogBackgroundColor(requireActivity().getResources().getColor(R.color.background))
                .setTitle("Log Out")
                .setTextGravity(Gravity.CENTER)
                .setTextColor(requireActivity().getResources().getColor(R.color.background_tv))
                .setMessage("Sei sicuro di volerti disconnettere?")
                .addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                    logout();
                })
                .addButton("Annulla", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                    dialog.dismiss();
                });

        builder.show();

    }
    private void alertLogin()
    {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(requireContext())
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setDialogBackgroundColor(requireActivity().getResources().getColor(R.color.background))
                .setTitle("Log In")
                .setTextGravity(Gravity.CENTER)
                .setTextColor(requireActivity().getResources().getColor(R.color.background_tv))
                .setMessage("Vuoi connetterti per salvare i tuoi giochi?")
                .addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                    logout();
                })
                .addButton("Annulla", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, (dialog, which) -> {

                    dialog.dismiss();
                });
        builder.show();
    }

    private void uploadImage(Uri uri){ // (String imagePath)

        if(uri != null){
            StorageReference fileReference = mStoraeRef.child(Objects.requireNonNull(firebaseAuth.getUid()));
            StorageTask mUploadTask = fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            getUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {

                        }
                    });

        }
    }

    private void getUrl(){
        mStoraeRef.child(firebaseAuth.getUid()).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                             writeImage(uri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                    }
                });
    }

    private void writeImage(Uri uri){
        String url = uri.toString();
        DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        Map<String, Object> user = new HashMap<>();
        user.put("foto", url);
        documentReference.set(user, SetOptions.merge());
    }

    private AuthCredential getGoogleCredential(){
        String token = prefs.getString(Constants.LOGIN_CREDENTIAL, "");
        return GoogleAuthProvider.getCredential(token, null);
    }
    private void ReAuthenticate(){
        firebaseUser.reauthenticate(getGoogleCredential()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                    db.document("users" + "/" + uid + "preferiti").delete();
                    db.document("users" + "/" + uid + "raccolta").delete();
                    db.collection("users").document(uid).delete();
                    firebaseUser.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getActivity(), ActivityLogin.class));
                                        requireActivity().finish();
                                    }
                                }
                            });
                }
            }
        });
    }

}