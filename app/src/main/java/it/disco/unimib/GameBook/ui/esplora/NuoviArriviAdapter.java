package it.disco.unimib.GameBook.ui.esplora;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;
import it.disco.unimib.GameBook.ui.community.CommunityRecyclerViewAdapter;


public class NuoviArriviAdapter extends RecyclerView.Adapter<NuoviArriviAdapter.NuoviArriviHolder>  {

    ArrayList<VideoGame> videoGameArrayList;

    public NuoviArriviAdapter(ArrayList<VideoGame> videoGameArrayList) {
        this.videoGameArrayList = videoGameArrayList;
    }

    @NonNull
    @Override
    public NuoviArriviHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_nuovi_arrivi, parent, false);

        return new NuoviArriviHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NuoviArriviAdapter.NuoviArriviHolder holder, int position) {
        holder.bind(videoGameArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NuoviArriviHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public NuoviArriviHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewNuoviArrivi);
            textView = itemView.findViewById(R.id.titolo);
        }

        public void bind(VideoGame videoGame) {

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


                /*itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        onItemClickListener.onItemClick(videoGame);
                    }
                });*/
            }
        }
    }


}
