package it.disco.unimib.GameBook.ui.profilo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hari.bounceview.BounceView;
import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.ui.community.CommunityFragment;
import it.disco.unimib.GameBook.ui.community.CommunityFragmentDirections;
import it.disco.unimib.GameBook.ui.community.User;
import it.disco.unimib.GameBook.utils.Constants;


public class ProfiloFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GoogleSignInClient mGoogleSignInClient;
    ProfiloFragment profiloFragment = this;
    PreferitiAdapter preferitiAdapter;
    RecyclerView recyclerView2, recyclerView1;
    SharedPreferences preferences;
    TextView textView;
    ImageView imageView;
    Context context;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context = requireContext();
        View root = inflater.inflate(R.layout.fragment_profilo, container, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments().isEmpty()){
            user = null;
        }else{
            user = ProfiloFragmentArgs.fromBundle(getArguments()).getUser();
        }



        imageView = view.findViewById(R.id.imageViewProfilo);
        //ImageButton button_preferiti = view.findViewById(R.id.button_preferiti);
        textView = view.findViewById(R.id.username);
        Button button1_veditutti = view.findViewById(R.id.veditutto_lista);
        Button button2_veditutti = view.findViewById(R.id.veditutto_lista_preferiti);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        recyclerView1 =view.findViewById(R.id.recyclerview_lista);
        recyclerView2 =view.findViewById(R.id.recyclerview_lista_preferiti);

        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        setUpRecyclerView1(view, user);
        setUpRecyclerView2(view, user);

        if (firebaseUser.isAnonymous()){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.incognito));
            textView.setText("Ospite");
            button1_veditutti.setVisibility(View.GONE);
            button2_veditutti.setVisibility(View.GONE);
            view.findViewById(R.id.testo_lista_giochi).setVisibility(View.GONE);
            view.findViewById(R.id.testo_lista_preferiti).setVisibility(View.GONE);
            view.findViewById(R.id.testo_ospite_giochi).setVisibility(View.VISIBLE);
        }


        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

        BounceView.addAnimTo(button1_veditutti);
        button1_veditutti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user != null){
                            ProfiloFragmentDirections.ActionProfiloFragmentToRaccoltaFragment actionProfiloFragmentToRaccoltaFragment =
                                    ProfiloFragmentDirections.actionProfiloFragmentToRaccoltaFragment(user);
                            Navigation.findNavController(view).navigate(actionProfiloFragmentToRaccoltaFragment);
                        }else{
                            NavController navController = NavHostFragment.findNavController(profiloFragment);
                            navController.navigate(
                                    R.id.action_ProfiloFragment_to_raccoltaFragment);
                        }

                    }
                },400);


            }
        });

        BounceView.addAnimTo(button2_veditutti);
        button2_veditutti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user != null){
                            ProfiloFragmentDirections.ActionProfiloFragmentToPreferitiFragment actionProfiloFragmentToPreferitiFragment =
                                    ProfiloFragmentDirections.actionProfiloFragmentToPreferitiFragment(user);
                            Navigation.findNavController(view).navigate(actionProfiloFragmentToPreferitiFragment);
                        }else{
                            NavController navController = NavHostFragment.findNavController(profiloFragment);
                            navController.navigate(
                                    R.id.action_ProfiloFragment_to_preferitiFragment);
                        }

                    }
                },400);

            }
        });

    }


    private void setUpRecyclerView1(View view, User user) {
        Query query = null;

        if(user != null){
            query = db.collection("users").document(user.getId()).collection("raccolta")
                    .limit(5);
        }else {
            query = db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("raccolta")
                    .limit(5);
        }
        FirestoreRecyclerOptions<VideoGame> options = new FirestoreRecyclerOptions.Builder<VideoGame>()
                .setQuery(query, VideoGame.class)
                .build();
        preferitiAdapter = new PreferitiAdapter(options, new PreferitiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoGame videoGame) {
                ProfiloFragmentDirections.ActionProfiloFragmentToVideoGameFragment action = ProfiloFragmentDirections.actionProfiloFragmentToVideoGameFragment(videoGame);
                Navigation.findNavController(view).navigate(action);
            }
        });
        preferitiAdapter.startListening();
        recyclerView1.setAdapter(preferitiAdapter);

    }
    @Override
    public void onResume() {
        super.onResume();

        if(user != null){
            db.document("users" + "/" + Objects.requireNonNull(user.getId()))
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot != null && documentSnapshot.exists()){
                            String username = documentSnapshot.getString("username");
                            textView.setText(username);
                            String photo = documentSnapshot.getString("foto");
                            if (photo != null){
                                getPhotoUrl(photo);
                            }
                        }
                    }
                }
            });
        }else {

            db.document("users" + "/" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("username");
                            String newValue = preferences.getString(Constants.USERNAME, username);
                            textView.setText(newValue);
                            String photo = documentSnapshot.getString("foto");
                            if (photo != null) {
                                getPhotoUrl(photo);
                            }

                        }
                    }
                }
            });
        }

    }
    private void getPhotoUrl(String url){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(()-> {
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                e.printStackTrace();
            }
            Bitmap finalMIcon = mIcon;
            handler.post(() -> {
                try {
                    if(finalMIcon != null && context != null) {
                        Glide.with(context).load(finalMIcon).
                                placeholder(R.drawable.ic_baseline_cloud_download_24).into(imageView);
                    }
                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setUpRecyclerView2(View view, User user) {
        Query query = null;
        if(user != null){
            query = db.collection("users").document(user.getId()).collection("preferiti")
                    .limit(5);
        }else {
            query = db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("preferiti")
                    .limit(5);
        }

        FirestoreRecyclerOptions<VideoGame> options = new FirestoreRecyclerOptions.Builder<VideoGame>()
                .setQuery(query, VideoGame.class)
                .build();
        preferitiAdapter = new PreferitiAdapter(options, new PreferitiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoGame videoGame) {

                ProfiloFragmentDirections.ActionProfiloFragmentToVideoGameFragment action = ProfiloFragmentDirections.actionProfiloFragmentToVideoGameFragment(videoGame);
                Navigation.findNavController(view).navigate(action);
            }
        });
        preferitiAdapter.startListening();
        recyclerView2.setAdapter(preferitiAdapter);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.modifica_profilo){
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(
                    R.id.action_ProfiloFragment_to_settingsActivity);

        }
        return super.onOptionsItemSelected(item);
    }
}