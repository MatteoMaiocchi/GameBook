package it.disco.unimib.GameBook.ui.profilo;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;

public class VideoGameRecyclerViewAdapter extends RecyclerView.Adapter<VideoGameRecyclerViewAdapter.VideoGameViewHolder> {

    private List<VideoGame> videoGameList;
    /*
    private final OnItemClickListener onItemClickListener;

    // Custom interface to intercept the click on an item of the RecyclerView
    public interface OnItemClickListener {
        void onItemClick(VideoGame videoGame);
    }

     */

    public VideoGameRecyclerViewAdapter(List<VideoGame> videoGameList){
        this.videoGameList = videoGameList;
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

        public VideoGameViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }
}
