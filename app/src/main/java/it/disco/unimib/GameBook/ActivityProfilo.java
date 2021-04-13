package it.disco.unimib.GameBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityProfilo extends AppCompatActivity {
    private Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profilo);

        button = findViewById(R.id.Esplora);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }
    public void openMainActivity() {
        Intent intent = new Intent(ActivityProfilo.this, MainActivity.class);
        startActivity(intent);
    }
}
