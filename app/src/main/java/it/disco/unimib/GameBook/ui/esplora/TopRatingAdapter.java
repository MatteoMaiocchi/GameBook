package it.disco.unimib.GameBook.ui.esplora;

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


public class TopRatingAdapter extends RecyclerView.Adapter<TopRatingAdapter.TopRatingHolder>  {

    private List<VideoGame> videoGameArrayList;
    private OnItemClickListener onItemClickListener;

    // Custom interface to intercept the click on an item of the RecyclerView
    public interface OnItemClickListener {
        void onItemClick(VideoGame videoGame);
    }

    public TopRatingAdapter(List<VideoGame> videoGameArrayList, OnItemClickListener onItemClickListener) {
        Log.d("supremo", "momo");
        this.videoGameArrayList = videoGameArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TopRatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("debug", "onCreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_nuovi_arrivi, parent, false);

        return new TopRatingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatingHolder holder, int position) {
        Log.d("sono in", "onbind");
        holder.bind(videoGameArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        if (videoGameArrayList != null) {
            return videoGameArrayList.size();
        }
        return 0;
    }

    public void addData(List<VideoGame> videoGameArrayList) {
        this.videoGameArrayList = videoGameArrayList;
    }


    public class TopRatingHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;


        public TopRatingHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewNuoviArrivi);
            textView = itemView.findViewById(R.id.titolo);
        }

        public void bind(VideoGame videoGame) {
            Log.d("pippo", "e paperino");
            if (videoGame != null) {

                String url = videoGame.getBackground_image();
                String newUrl = null;

                if (url != null) {

                    newUrl = url.replace("http://", "https://").trim();

                    //Download the image associated with the videogame
                    Glide.with(itemView.getContext()).load(newUrl).
                            placeholder(R.drawable.ic_baseline_cloud_download_24).into(imageView);
                }
                textView.setText(videoGame.getName());
                Log.d("nome", "videogame" + videoGame.getName());
                Log.d("image", "url: " + newUrl);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        onItemClickListener.onItemClick(videoGame);
                    }
                });


                /*itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        onItemClickListener.onItemClick(videoGame);
                    }
                });*/
            }
        }
    }


}
