package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notes.MyDialogExitFragment;
import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.about.AboutFragment;
import com.example.notes.ui.details.NotesDetailsFragment;
import com.example.notes.ui.list.NotesListFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavDrawable {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            openNotesList();
        }

        drawerLayout = findViewById(R.id.drawer_layout);

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.action_about:
                        Fragment aboutFragment = getSupportFragmentManager().findFragmentByTag(AboutFragment.ABOUT_TAG);
                        if (aboutFragment == null) {
                            aboutFragment = new AboutFragment();
                        } else {
                            if (aboutFragment.isVisible()) {
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            }
                        }

                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("AboutFragment")
                                .replace(R.id.fragment_container, aboutFragment, AboutFragment.ABOUT_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START); // закрываем drawer после выбора пункта меню

                        return true;

                    case R.id.action_exit:
                        MyDialogExitFragment.newInstance().show(getSupportFragmentManager(), "MyDialogExitFragment");
                        return true;

                    case R.id.action_notes_list:

                        Fragment listFragment = getSupportFragmentManager().findFragmentByTag(NotesListFragment.LIST_TAG);
                        if (listFragment == null) {
                            listFragment = new NotesListFragment();
                        } else {
                            if (listFragment.isVisible()) {
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            }
                        }
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, listFragment, NotesListFragment.LIST_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START); // закрываем drawer после выбора пункта меню

                        return true;

                    case R.id.action_settings:
                        //todo later
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_sign_out:
                        signOut();

                        return true;
                }
                return false;
            }
        });

        getSupportFragmentManager().setFragmentResultListener(NotesListFragment.NOTE_SELECTED, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                Note note = result.getParcelable(NotesListFragment.SELECTED_NOTE_BUNDLE);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, NotesDetailsFragment.newInstance(note))
                        .addToBackStack("NoteDetailsFragment")
                        .commit();
            }
        });

        getSupportFragmentManager().setFragmentResultListener(AuthorizationFragment.KEY_AUTHORIZED, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                openNotesList();
            }
        });

    }

    @Override
    public void initDrawer(MaterialToolbar toolbar) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void openNotesList() {

        // проверяем авторизацию:
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AuthorizationFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NotesListFragment())
                    .commit();
        }
    }

    private void signOut() {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, googleSignInOptions);
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        openNotesList();
                    }
                });
    }

}