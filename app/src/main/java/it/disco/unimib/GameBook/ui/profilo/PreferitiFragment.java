package it.disco.unimib.GameBook.ui.profilo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.ui.community.CommunityAdapter;
import it.disco.unimib.GameBook.ui.community.User;


public class PreferitiFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ListaGiochiAdapter listaGiochiAdapter;

    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preferiti, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments().isEmpty()){
            user = null;
        }else{
            user = ProfiloFragmentArgs.fromBundle(getArguments()).getUser();
        }


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerview_preferiti);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null) {

            Query query = null;
            if(user != null){
                query = firebaseFirestore.collection("users").document(user.getId()).collection("preferiti");
            }else {
                query = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("preferiti");
            }

            FirestoreRecyclerOptions<VideoGame> options = new FirestoreRecyclerOptions.Builder<VideoGame>()
                    .setQuery(query, VideoGame.class)
                    .build();

            listaGiochiAdapter = new ListaGiochiAdapter(options, new ListaGiochiAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(VideoGame videoGame) {
                    PreferitiFragmentDirections.ActionPreferitiFragmentToVideoGameFragment action = PreferitiFragmentDirections.actionPreferitiFragmentToVideoGameFragment(videoGame);
                    Navigation.findNavController(view).navigate(action);
                }
            });
            listaGiochiAdapter.startListening();
            recyclerView.setAdapter(listaGiochiAdapter);
        }
    }
}