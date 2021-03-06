package it.disco.unimib.GameBook.ui.profilo;

import android.util.Log;
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
import it.disco.unimib.GameBook.ui.community.CommunityAdapter;
import it.disco.unimib.GameBook.ui.community.User;
import it.disco.unimib.GameBook.ui.esplora.NuoviArriviAdapter;


public class PreferitiAdapter extends FirestoreRecyclerAdapter<VideoGame, PreferitiAdapter.PreferitiHolder> {

    private OnItemClickListener onItemClickListener;

    // Custom interface to intercept the click on an item of the RecyclerView
    public interface OnItemClickListener {
        void onItemClick(VideoGame videoGame);
    }

    public PreferitiAdapter(FirestoreRecyclerOptions<VideoGame> options, OnItemClickListener onItemClickListener) {
        super(options);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBindViewHolder(PreferitiAdapter.PreferitiHolder holder, int position, VideoGame model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public PreferitiAdapter.PreferitiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_videogame_preferiti, parent, false);

        return new PreferitiHolder(view) ;
    }

    public class PreferitiHolder extends RecyclerView.ViewHolder{
        TextView stringa;
        ImageView imageview;

        public PreferitiHolder(@NonNull View itemView) {
            super(itemView);
            stringa = itemView.findViewById(R.id.titolo_preferiti);
            imageview = itemView.findViewById(R.id.imageViewPreferiti);

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
