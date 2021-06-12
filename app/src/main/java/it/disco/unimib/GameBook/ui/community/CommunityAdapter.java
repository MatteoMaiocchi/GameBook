package it.disco.unimib.GameBook.ui.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.disco.unimib.GameBook.R;

public class CommunityAdapter extends FirestoreRecyclerAdapter<User, CommunityAdapter.CommunityHolder> {


    public CommunityAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    //associa il dato alla view
    @Override
    protected void onBindViewHolder(@NonNull CommunityHolder holder, int position, @NonNull User model) {
        holder.email.setText(model.getEmail());
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
        TextView email;

        public CommunityHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.Email);
        }
    }
}
