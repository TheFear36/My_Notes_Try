package com.thefear.seconttrymynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.thefear.seconttrymynotes.contracts.ToolbarForActivity;
import com.thefear.seconttrymynotes.fragments.NotesListFragment;
import com.thefear.seconttrymynotes.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements ToolbarForActivity {

    private DrawerLayout dLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NotesListFragment())
                    .commit();
        }

        NavigationView navigationView = findViewById(R.id.navigation_menu);

        dLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.list_notes) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new NotesListFragment())
                            .addToBackStack(null)
                            .commit();

                    dLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (item.getItemId() == R.id.action_settings) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new SettingsFragment())
                            .addToBackStack(null)
                            .commit();

                    dLayout.closeDrawer(GravityCompat.START);
                    return true;
                } else if (item.getItemId() == R.id.action_exit) {
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);

        dLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}