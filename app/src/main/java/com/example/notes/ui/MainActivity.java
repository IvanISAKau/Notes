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

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.about.AboutFragment;
import com.example.notes.ui.details.NotesDetailsFragment;
import com.example.notes.ui.list.NotesListFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavDrawable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    @Override
    public void initDrawer(MaterialToolbar toolbar) {
        // Находим DrawerLayout
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        // Создаем ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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
                        finish();
                        return true;

                    case R.id.action_notes_list:
                        // todo: доработать. Не совсем верное поведение
                        Fragment notesListFragment = getSupportFragmentManager().findFragmentByTag(NotesListFragment.LIST_TAG);

                        if (notesListFragment == null) {
                            notesListFragment = new NotesListFragment();
                        } else {
                            if (notesListFragment.isVisible()) {
                                drawerLayout.closeDrawer(GravityCompat.START);
                                notesListFragment.onDetach();
                                break;
                            }
                        }

                        getSupportFragmentManager()
                                .beginTransaction()
//                                .addToBackStack("NoteListFragment")
                                .replace(R.id.fragment_container, notesListFragment, NotesListFragment.LIST_TAG)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.action_settings:
                        //todo later
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return false;
            }
        });
    }

}