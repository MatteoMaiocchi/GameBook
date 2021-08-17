package it.disco.unimib.GameBook.ui.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import it.disco.unimib.GameBook.R;

public class CommunityRecyclerViewAdapter extends RecyclerView.Adapter<CommunityRecyclerViewAdapter.CommunityViewHolder> {

    ArrayList<User> userArrayList;

    public CommunityRecyclerViewAdapter(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_community, parent, false);

        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityRecyclerViewAdapter.CommunityViewHolder holder, int position) {

        holder.email.setText(userArrayList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder{

        TextView email;

        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.Email);
        }
    }
}
