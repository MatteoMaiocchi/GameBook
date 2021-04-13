package it.disco.unimib.GameBook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCommunity extends AppCompatActivity {
    private Button buttonP;
    private Button buttonE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_community);

        buttonP = findViewById(R.id.Profilo);
        buttonE = findViewById(R.id.Esplora);

        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_profilo();
            }
        });
        buttonE.setOnClickListener(new View.OnClickListener() {
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
