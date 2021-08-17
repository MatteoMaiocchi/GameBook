package it.disco.unimib.GameBook.ui.profilo;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;

public class VideoGameRecyclerViewAdapter extends RecyclerView.Adapter<VideoGameRecyclerViewAdapter.VideoGameViewHolder> {

    private List<VideoGame> videoGameList;

    private final OnItemClickListener onItemClickListener;

    // Custom interface to intercept the click on an item of the RecyclerView
    public interface OnItemClickListener {
        void onItemClick(VideoGame videoGame);
    }



    public VideoGameRecyclerViewAdapter(List<VideoGame> videoGameList, OnItemClickListener onItemClickListener){
        this.videoGameList = videoGameList;
        this.onItemClickListener = onItemClickListener;
    }

    //rappresenta la view che verrà popolata con un elemento
    @NonNull
    @Override
    public VideoGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_videogame, parent, false);

        return new VideoGameViewHolder(view);
    }

    //associa il dato alla view
    @Override
    public void onBindViewHolder(@NonNull VideoGameViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return videoGameList.size();
    }

    //riferimenti alla rappresentazione visiva
    public class VideoGameViewHolder extends RecyclerView.ViewHolder{
        TextView stringa;
        ImageView imageview;

        public VideoGameViewHolder(@NonNull View itemView) {
            super(itemView);
            stringa = itemView.findViewById(R.id.titolo_videogame);
            imageview = itemView.findViewById(R.id.imageView_videogame);
        }

        public void bind(VideoGame videoGame) {

            if (videoGame != null) {

                String url = videoGame.getBackground_image();
                String newUrl = null;

                if (url != null) {

                    newUrl = url.replace("http://", "https://").trim();

                    //Download the image associated with the videogame
                    Glide.with(itemView.getContext()).load(newUrl).
                            placeholder(R.drawable.ic_baseline_cloud_download_24).into(imageview);
                }
                stringa.setText(videoGame.getName());
                Log.d("nome", "videogame" + videoGame.getName());
                Log.d("image", "url: " + newUrl);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        onItemClickListener.onItemClick(videoGame);
                    }
                });
            }


        }
    }
}
