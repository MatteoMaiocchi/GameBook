package it.disco.unimib.GameBook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCommunity extends AppCompatActivity {
    private ImageButton ibuttonP;
    private ImageButton ibuttonE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_community);

        ibuttonP = findViewById(R.id.Profilo);
        ibuttonE = findViewById(R.id.Esplora);

        ibuttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_profilo();
            }
        });
        ibuttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openActivity_profilo() {
        Intent intent = new Intent(ActivityCommunity.this, ActivityProfilo.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        Intent intent = new Intent(ActivityCommunity.this, MainActivity.class);
        startActivity(intent);
    }
}
