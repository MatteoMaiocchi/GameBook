package it.disco.unimib.GameBook.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;

import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;

import it.disco.unimib.GameBook.R;
import it.disco.unimib.GameBook.databinding.ActivityMainBinding;
import it.disco.unimib.GameBook.ui.esplora.EsploraFragment;
import it.disco.unimib.GameBook.utils.Constants;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean prova = prefs.getBoolean(Constants.PROVA, false);
        prefs.edit().putBoolean(Constants.PROVA, false).apply();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.EsploraFragment, R.id.ProfiloFragment, R.id.CommunityFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


    @Override
    protected void onResume() {
        super.onResume();
        NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                this,
                getLifecycle()
        );
        DialogPropertiesPendulum properties = builder.getDialogProperties();
        properties.setCancelable(false);
        properties.setNoInternetConnectionTitle("Connessione Internet assente");
        properties.setNoInternetConnectionMessage("Verifica la connessione e riprova");
        properties.setShowInternetOnButtons(true);
        properties.setPleaseTurnOnText("Attiva");
        properties.setWifiOnButtonText("Wifi");
        properties.setMobileDataOnButtonText("Dati mobili");
        properties.setOnAirplaneModeTitle("Connessione Internet assente");
        properties.setOnAirplaneModeMessage("Modalit?? aereo attiva");
        properties.setPleaseTurnOffText("Disattiva");
        properties.setAirplaneModeOffButtonText("Modalit?? aereo");
        properties.setShowAirplaneModeOffButtons(true);
        builder.build();
    }
}