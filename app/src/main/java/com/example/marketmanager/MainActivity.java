package com.example.marketmanager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.marketmanager.fragments.HistoryFragment;
import com.example.marketmanager.fragments.HomeFragment;
import com.example.marketmanager.fragments.StorageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        map();


        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                Fragment selectedFragment = null;
                switch (menuItem.getItemId())
                {
                    case R.id.storage: {
                        selectedFragment = new StorageFragment();
                        break;
                    }

                    case R.id.home: {
                        selectedFragment = new HomeFragment();
                        break;
                    }

                    case R.id.history: {
                        selectedFragment = new HistoryFragment();
                        break;
                    }
                }

                transaction.replace(R.id.fragmentContainer, selectedFragment);
                transaction.commit();
                return false;
            }
        });


        //set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void map() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentContainer = findViewById(R.id.fragmentContainer);
    }


}