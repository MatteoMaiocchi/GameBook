package it.disco.unimib.GameBook.ui.esplora;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.repository.IVideoGameResponseCallback;
import it.disco.unimib.GameBook.repository.ResponseCallback;
import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.repository.VideoGameResponseCallback;

public class EsploraFragment extends Fragment implements ResponseCallback {

    private static final String TAG = "VideoGameFragRecView";

    private IVideoGameResponseCallback videogameRepository;
    private NuoviArriviAdapter nuoviArriviViewAdapter;

    private List<VideoGame> videoGamesApi;

    private int page;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_esplora, container, false);

        setHasOptionsMenu(true);

        this.page = 1;

        videogameRepository = new VideoGameResponseCallback(this, requireActivity().getApplication());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videoGamesApi = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.nuoviArrivi);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(nuoviArriviViewAdapter);
        videogameRepository.fetchVideoGame("Fifa"); //Stringa di ricerca
    }


    @Override
    public void onResponse(List<VideoGame> videoGameList) {
        videoGamesApi.addAll(videoGameList);
        nuoviArriviViewAdapter.notifyDataSetChanged(); //Restituisce la notifica dei dati caricati
        Log.d("giochi", videoGameList.toString());
    }

    @Override
    public void onFailure(String msg) {
        Log.d(TAG, "Web Service call failed: " + msg);
        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT).show();
    }

}
