package it.disco.unimib.GameBook.ui.profilo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.models.VideoGame;


public class ListaGiochiAdapter extends FirestoreRecyclerAdapter<VideoGame, ListaGiochiAdapter.ListaGiochiHolder> {

    private OnItemClickListener onItemClickListener;

    // Custom interface to intercept the click on an item of the RecyclerView
    public interface OnItemClickListener {
        void onItemClick(VideoGame videoGame);
    }

    public ListaGiochiAdapter(FirestoreRecyclerOptions<VideoGame> options, OnItemClickListener onItemClickListener) {
        super(options);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBindViewHolder(ListaGiochiAdapter.ListaGiochiHolder holder, int position, VideoGame model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public ListaGiochiAdapter.ListaGiochiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_videogame, parent, false);

        return new ListaGiochiHolder(view) ;
    }

    public class ListaGiochiHolder extends RecyclerView.ViewHolder{
        TextView stringa;
        ImageView imageview;

        public ListaGiochiHolder(@NonNull View itemView) {
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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        onItemClickListener.onItemClick(videoGame);
                    }
                });
            }
        }
    }
}
