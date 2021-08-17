package it.disco.unimib.GameBook.ui.community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import it.disco.unimib.GameBook.R;


public class CommunityFragment extends Fragment {
    CollectionReference collectionReference;
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    CommunityRecyclerViewAdapter communityRecyclerViewAdapter;
    CommunityAdapter communityAdapter;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //collectionReference = firebaseFirestore.collection("users");
        recyclerView = view.findViewById(R.id.list_users);



        /*
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        userArrayList = new ArrayList<User>();
        communityRecyclerViewAdapter = new CommunityRecyclerViewAdapter(userArrayList);
        recyclerView.setAdapter(communityRecyclerViewAdapter);

         */

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        Query query = firebaseFirestore.collection("users");

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();
        communityAdapter = new CommunityAdapter(options);
        communityAdapter.startListening();
        recyclerView.setAdapter(communityAdapter);




        /*
        firebaseFirestore.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            User obj = d.toObject(User.class);
                            userArrayList.add(obj);
                        }
                        communityRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

         */

        //setUpRecyclerView();

        /*prova 3
        Query query = firebaseFirestore.collection("users");
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<User, UsersViewHolder>(options) {
            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_community, parent, false);

                return new UsersViewHolder(view) ;
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull User model) {
                holder.email.setText(model.getEmail());
            }
        };


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

         */
        // fine prova 3


        /* prova 2
        recyclerView = view.findViewById(R.id.list_users);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(communityAdapter);

         */

        //prova 1
        /*
        recyclerView = view.findViewById(R.id.list_users);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        userArrayList = new ArrayList<User>();
        communityRecyclerViewAdapter = new CommunityRecyclerViewAdapter(userArrayList);
        recyclerView.setAdapter(communityRecyclerViewAdapter);

         */
        /*
      firebaseFirestore.collection("users").orderBy("Email", Query.Direction.ASCENDING)
              .addSnapshotListener(new EventListener<QuerySnapshot>() {
                  @Override
                  public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                      for (DocumentChange dc : value.getDocumentChanges()){
                          if(dc.getType() == DocumentChange.Type.ADDED){
                              userArrayList.add(dc.getDocument().toObject(User.class));
                          }

                          communityRecyclerViewAdapter.notifyDataSetChanged();
                      }
                  }
              });

         */
        //fine prova 1


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);


        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setUpRecyclerView(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setUpRecyclerView(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setUpRecyclerView(String text) {
        // Configure recycler adapter
        Query query = firebaseFirestore.collection("users").orderBy("username").startAt(text).endAt(text + "\uf8ff");

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        communityAdapter = new CommunityAdapter(options);
        communityAdapter.startListening();
        recyclerView.setAdapter(communityAdapter);






    }






    /*
    public class UsersViewHolder extends RecyclerView.ViewHolder{
        TextView email;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
        }
    }

     */


    /*
    @Override
    public void onStart() {
        super.onStart();
       communityAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        communityAdapter.stopListening();
    }

     */


    /*public void search(String text){
        String searchText = text.toLowerCase();
        firebaseFirestore.collection("users").orderBy("email").startAt(searchText).endAt(searchText + "\uf8ff")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        List<DocumentSnapshot> list = value.getDocuments();
                        for (DocumentSnapshot d:list){
                            User obj = d.toObject(User.class);
                            userArrayList.add(obj);
                        }
                        communityRecyclerViewAdapter.notifyDataSetChanged();



                    }
                });
    }*/
}