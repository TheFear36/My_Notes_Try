package com.thefear.seconttrymynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentResultListener;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.thefear.seconttrymynotes.contracts.ToolbarForActivity;
import com.thefear.seconttrymynotes.dialogs.ExitDialogFragment;
import com.thefear.seconttrymynotes.fragments.NotesListFragment;
import com.thefear.seconttrymynotes.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements ToolbarForActivity {

    private DrawerLayout dLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().setFragmentResultListener(ExitDialogFragment.KEY_RESULT, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int choice = result.getInt(ExitDialogFragment.ARG_CHOICE);

                switch (choice) {
                    case Dialog
                            .BUTTON_POSITIVE:
                        finishAffinity();
                        Toast.makeText(MainActivity.this, "Вы вышли из приложения", Toast.LENGTH_SHORT).show();
                        break;
                    case Dialog
                            .BUTTON_NEGATIVE:
                        Snackbar.make(findViewById(R.id.drawer_layout), "Вернуть меню выхода?", Snackbar.LENGTH_LONG)
                                .setAction("Да", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onCreateExitDialog();
                                    }
                                })
                                .show();
                        break;
                }
            }
        });

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
                    onCreateExitDialog();
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
/* TODO
    @Override
    public void onBackPressed() {
        onCreateExitDialog();
    }*/

    void onCreateExitDialog() {
        ExitDialogFragment.newInstance(R.string.app_name, R.string.exit_message).show(getSupportFragmentManager(), ExitDialogFragment.TAG);
    }
}