package it.disco.unimib.GameBook.ui.profilo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.ui.community.User;


public class RaccoltaFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ListaGiochiAdapter adapter;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_raccolta, container, false);

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
        recyclerView = view.findViewById(R.id.recyclerview_raccolta);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null) {
            Query query = null;
            if(user != null){
                query = firebaseFirestore.collection("users").document(user.getId()).collection("raccolta");
            }else {
                query = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("raccolta");
            }

            FirestoreRecyclerOptions<VideoGame> options = new FirestoreRecyclerOptions.Builder<VideoGame>()
                    .setQuery(query, VideoGame.class)
                    .build();

            adapter = new ListaGiochiAdapter(options, new ListaGiochiAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(VideoGame videoGame) {
                    RaccoltaFragmentDirections.ActionRaccoltaFragmentToVideoGameFragment action = RaccoltaFragmentDirections.actionRaccoltaFragmentToVideoGameFragment(videoGame);
                    Navigation.findNavController(view).navigate(action);
                }
            });
            adapter.startListening();
            recyclerView.setAdapter(adapter);

        }
    }


}