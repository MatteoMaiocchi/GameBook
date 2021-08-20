package it.disco.unimib.GameBook.ui.esplora;

import android.media.Rating;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.ui.MainActivity;


public class VideoGameFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean choose = false;

    private VideoGame videoGame;
    private ProgressBar progressBar;
    private Button rimuovi;
    private Button button;
    Animation anim;

    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        /*((MainActivity)getActivity()).setTitle("Video Game");

        getActivity().setTitle("bau");

        Toolbar bar = Toolbar.class.cast(getActivity().findViewById(R.id.favorite));*/

        /*Toolbar actionBar = getActivity().findViewById(R.id.favorite);
        actionBar.setTitle("Buongiorno");*/

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("debug", "2");
        super.onViewCreated(view, savedInstanceState);

        /*Toolbar tbar = view.findViewById(R.id.favorite);
        ((MainActivity) getActivity()).getSupportActionBar().hide();

        ((MainActivity) getActivity()).setSupportActionBar(tbar);*/

        videoGame = VideoGameFragmentArgs.fromBundle(getArguments()).getVideoGame();
        TextView textView = view.findViewById(R.id.titoloVideoGame);
        ImageView imageView = view.findViewById(R.id.imageViewVideoGame);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView released = view.findViewById(R.id.dataUscita);
        CheckBox checkBox = view.findViewById(R.id.favorite);
        button = view.findViewById(R.id.aggiungi);
        rimuovi = view.findViewById(R.id.rimuovi);
        progressBar = view.findViewById(R.id.progressbar);

        if(firebaseUser.isAnonymous()){
            button.setVisibility(View.GONE);
            rimuovi.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
        }


        if (videoGame != null) {

            String url = videoGame.getBackground_image();
            String newUrl = null;

            if (url != null) {

                newUrl = url.replace("http://", "https://").trim();

                //Download the image associated with the videogame
                Glide.with(view.getContext()).load(newUrl).
                        placeholder(R.drawable.ic_baseline_cloud_download_24).into(imageView);
            }
            textView.setText(videoGame.getName());
            ratingBar.setRating(videoGame.getRating());
            released.setText(videoGame.getReleased());
            db.document("users" + "/" + firebaseAuth.getCurrentUser().getUid() + "/preferiti" + "/" + videoGame.getName())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.exists() && documentSnapshot != null){
                            if(documentSnapshot.getBoolean("tba") == null){
                                checkBox.setChecked(false);
                            }else {
                                boolean tba = documentSnapshot.getBoolean("tba");
                                if (tba) {
                                    checkBox.setChecked(true);
                                } else {
                                    checkBox.setChecked(false);
                                }
                            }
                        }
                    }
                }
            });
            //button.setVisibility(view.INVISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_bottom);
                    button.startAnimation(anim);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            button.setVisibility(View.GONE);
                            anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);
                            rimuovi.startAnimation(anim);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    rimuovi.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("raccolta").document(videoGame.getName());

                    Map<String, Object> user = new HashMap<>();
                    user.put("name", videoGame.getName());
                    user.put("background_image", videoGame.getBackground_image());
                    documentReference.set(user);
                    button.setVisibility(View.GONE);

                }
            });

            rimuovi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_bottom);
                    rimuovi.startAnimation(anim);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            rimuovi.setVisibility(View.GONE);
                            anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up);
                            button.startAnimation(anim);
                            button.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("raccolta").document(videoGame.getName()).delete();
                }
            });


        }



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("preferiti").document(videoGame.getName());

                    Map<String, Object> user = new HashMap<>();
                    user.put("name", videoGame.getName());
                    user.put("background_image", videoGame.getBackground_image());
                    user.put("tba", buttonView.isChecked());
                    documentReference.set(user);
                    choose = true;

                } else {
                    db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("preferiti").document(videoGame.getName()).delete();
                    choose = false;
                }
            }});
        readDB();




    }


    /*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_videogame, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.favorite)
        {
            if(db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("preferiti").document(videoGame.getName()).toString() == videoGame.getName()) {
                db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("preferiti").document(videoGame.getName()).delete();
                choose = false;
            }else {

                DocumentReference documentReference = db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("preferiti").document(videoGame.getName());

                Map<String, Object> user = new HashMap<>();
                user.put("name", videoGame.getName());
                user.put("background_image", videoGame.getBackground_image());
                documentReference.set(user);
                choose = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

     */

    @Override
    public void onResume() {
        super.onResume();
        readDB();

    }

    public void readDB(){
        db.collection("users").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).collection("raccolta")
                .whereEqualTo("name",videoGame.getName())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("raccolta", "bottone gone");
                        if(!queryDocumentSnapshots.getDocuments().isEmpty()){

                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            Log.d("raccolta",documentSnapshots.toString());
                            if(!firebaseUser.isAnonymous()){
                                progressBar.setVisibility(View.GONE);
                                rimuovi.setVisibility(View.VISIBLE);
                            }

                            for(DocumentSnapshot documentSnapshot: documentSnapshots){
                                Log.d("raccolta", Objects.requireNonNull(documentSnapshot.getData()).toString());
                            }
                        }else{
                            if(!firebaseUser.isAnonymous()){
                                progressBar.setVisibility(View.GONE);
                                button.setVisibility(View.VISIBLE);
                            }
                        }

                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        button.setVisibility(View.VISIBLE);
                        Log.d("raccolta", "bottone visible");
                        progressBar.setVisibility(View.GONE);
                    }
                });


    }
}