package it.disco.unimib.GameBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton ibuttonP;
    private ImageButton ibuttonC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibuttonP = findViewById(R.id.Profilo);
        ibuttonC = findViewById(R.id.Community);

        ibuttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_profilo();
            }
        });

        ibuttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_community();
            }
        });

    }

    public void openActivity_profilo() {
        Intent intent = new Intent(MainActivity.this, ActivityProfilo.class);
        startActivity(intent);
    }
    public void openActivity_community() {
        Intent intent = new Intent(MainActivity.this, ActivityCommunity.class);
        startActivity(intent);
    }
}