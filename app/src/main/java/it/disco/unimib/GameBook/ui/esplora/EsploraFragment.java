package it.disco.unimib.GameBook.ui.esplora;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.disco.unimib.GameBook.models.Response;
import it.disco.unimib.GameBook.models.ResponseTopRating;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.repository.IVideogameRepositoryWithLiveData;
import it.disco.unimib.GameBook.repository.IVideogameTopRatingRepositoryWithLiveData;
import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.repository.VideogameRepositoryWithLiveData;
import it.disco.unimib.GameBook.repository.VideogameTopRatingRepositoryWithLiveData;
import it.disco.unimib.GameBook.viewModel.VideoGameTopRatingViewModelFactory;
import it.disco.unimib.GameBook.viewModel.VideoGameViewModelFactory;
import it.disco.unimib.GameBook.viewModel.VideogameTopRatingViewModel;
import it.disco.unimib.GameBook.viewModel.VideogameViewModel;

public class EsploraFragment extends Fragment {

    private static final String TAG = "VideoGameFragRecView";

    private NuoviArriviAdapter nuoviArriviViewAdapter = null;
    private TopRatingAdapter topRatingAdapter = null;
    private IVideogameRepositoryWithLiveData iVideogameRepositoryWithLiveData;
    private IVideogameTopRatingRepositoryWithLiveData iVideogameTopRatingRepositoryWithLiveData;
    private VideogameViewModel videogameViewModel;
    private VideogameTopRatingViewModel videogameViewModelTopRating;

    private boolean oK = false;

    private List<VideoGame> videoGamesApi, videoGamesTopRating;
    private boolean ok;

    RecyclerView recyclerView, recyclerViewTopRating;

    private int page;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_esplora, container, false);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iVideogameRepositoryWithLiveData = new VideogameRepositoryWithLiveData(requireActivity().getApplication());
        iVideogameTopRatingRepositoryWithLiveData = new VideogameTopRatingRepositoryWithLiveData(requireActivity().getApplication());

        videoGamesApi = new ArrayList<>();
        videoGamesTopRating = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.prova);
        recyclerView.setHasFixedSize(true);
        recyclerViewTopRating = view.findViewById(R.id.videoGamesTopRating);
        recyclerViewTopRating.setHasFixedSize(true);

        nuoviArriviViewAdapter = new NuoviArriviAdapter(videoGamesApi, new NuoviArriviAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoGame videoGame) {
                EsploraFragmentDirections.ActionEsploraFragmentToVideoGameFragment action = EsploraFragmentDirections.actionEsploraFragmentToVideoGameFragment(videoGame);
                Navigation.findNavController(view).navigate(action);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(nuoviArriviViewAdapter);

        topRatingAdapter = new TopRatingAdapter(videoGamesTopRating, new TopRatingAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(VideoGame videoGame) {
                EsploraFragmentDirections.ActionEsploraFragmentToVideoGameFragment action = EsploraFragmentDirections.actionEsploraFragmentToVideoGameFragment(videoGame);
                Navigation.findNavController(view).navigate(action);
            }
        });
        recyclerViewTopRating.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTopRating.setAdapter(topRatingAdapter);


        videogameViewModel = new ViewModelProvider(this, new VideoGameViewModelFactory(
                requireActivity().getApplication(), iVideogameRepositoryWithLiveData, "")).get(VideogameViewModel.class);


        final Observer<Response> observer = new Observer<Response>() {
            @Override
            public void onChanged(@Nullable final Response response) {
                // Update the UI
                if (response != null) {
                    if (response.getCount() != -1) {
                        ok = true;
                        videoGamesApi.addAll(response.getVideoGameList());
                        progressBar.setVisibility(View.GONE);

                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nuoviArriviViewAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        updateUIForFailure("Error");
                    }
                }
            }

        };

        videogameViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), observer);


        videogameViewModelTopRating = new ViewModelProvider(this, new VideoGameTopRatingViewModelFactory(
                requireActivity().getApplication(), iVideogameTopRatingRepositoryWithLiveData, "-rating")).get(VideogameTopRatingViewModel.class);

        final Observer<ResponseTopRating> observer1 = new Observer<ResponseTopRating>() {
            @Override
            public void onChanged(ResponseTopRating responseTopRating) {
                if (responseTopRating != null) {
                    if (responseTopRating.getCount() != -1) {
                        ok = true;
                        videoGamesTopRating.addAll(responseTopRating.getVideoGameListTopRating());
                        progressBar.setVisibility(View.GONE);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                topRatingAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        updateUIForFailure("Error");
                    }
                }
            }
        };

        videogameViewModelTopRating.getResponseLiveData().observe(getViewLifecycleOwner(), observer1);

    }


    private void updateUIForSuccess(List<VideoGame> articleList) {
        videoGamesApi.addAll(articleList);
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nuoviArriviViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateUIForFailure(String msg) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            inflater.inflate(R.menu.search_menu_esplora, menu);
            MenuItem item = menu.findItem(R.id.search_esplora);


            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    setUpRecyclerView(query);
                    oK = false;
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    oK = false;
                    return false;
                }
            });
            super.onCreateOptionsMenu(menu, inflater);
    }

    public void setUpRecyclerView(String stringa) {
        videoGamesApi.clear();
        videogameViewModel.getMoreVideoGame(stringa);

    }


}
