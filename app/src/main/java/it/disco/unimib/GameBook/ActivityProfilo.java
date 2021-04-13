package it.disco.unimib.GameBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityProfilo extends AppCompatActivity {
    private Button buttonE;
    private Button buttonC;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profilo);

        buttonE = findViewById(R.id.Esplora);
        buttonC = findViewById(R.id.Community);

        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_community();
            }
        });
    }
    public void openMainActivity() {
        Intent intent = new Intent(ActivityProfilo.this, MainActivity.class);
        startActivity(intent);
    }
    public void openActivity_community() {
        Intent intent = new Intent(ActivityProfilo.this, ActivityCommunity.class);
        startActivity(intent);
    }
}
