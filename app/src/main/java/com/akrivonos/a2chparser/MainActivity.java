package com.akrivonos.a2chparser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.akrivonos.a2chparser.interfaces.OpenBoardListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.akrivonos.a2chparser.fragments.BoardsFragment.BOARD_ID;

public class MainActivity extends AppCompatActivity implements OpenBoardListener {

    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpScreen();
    }

    private void setUpScreen(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public void openBoard(String boardId) {
        Bundle bundle = new Bundle();
        bundle.putString(BOARD_ID, boardId);
        navController.navigate(R.id.concreteBoardFragment, bundle);
    }
}
