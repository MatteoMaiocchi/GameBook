package it.disco.unimib.GameBook.ui.community;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.ui.esplora.NuoviArriviAdapter;

public class CommunityAdapter extends FirestoreRecyclerAdapter<User, CommunityAdapter.CommunityHolder> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public CommunityAdapter(@NonNull FirestoreRecyclerOptions<User> options, OnItemClickListener onItemClickListener) {
        super(options);
        this.onItemClickListener = onItemClickListener;
    }


    //associa il dato alla view
    @Override
    protected void onBindViewHolder(@NonNull CommunityHolder holder, int position, @NonNull User model) {
        holder.bind(model);
    }

    //rappresenta la view che verrà popolata con un elemento
    @Override
    public CommunityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_community, parent, false);

        return new CommunityHolder(view) ;
    }

    //riferimenti alla rappresentazione visiva
    public class CommunityHolder extends RecyclerView.ViewHolder{
        TextView username;

        public CommunityHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.Email);
        }

        public void bind(User user) {
            if(user != null){
                username.setText(user.getUsername());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(user);
                    }
                });
            }
        }
    }
}
