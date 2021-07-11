package it.disco.unimib.GameBook.ui.esplora;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;

public class VideoGameFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {





        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("debug", "2");
        super.onViewCreated(view, savedInstanceState);

        VideoGame videoGame = VideoGameFragmentArgs.fromBundle(getArguments()).getVideoGame();
        TextView textView = view.findViewById(R.id.titoloVideoGame);
        ImageView imageView = view.findViewById(R.id.imageViewVideoGame);

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
        }

    }
}