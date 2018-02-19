package mbds.ht.nytimesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mbds.ht.nytimesearch.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }
}
