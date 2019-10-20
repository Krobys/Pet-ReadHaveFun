package com.akrivonos.a2chparser;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.akrivonos.a2chparser.interfaces.OpenBoardListener;
import com.akrivonos.a2chparser.interfaces.OpenDetailedSavePage;
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener;
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener;
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardConcrete;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.akrivonos.a2chparser.fragments.BoardsFragment.BOARD_INFO;

public class MainActivity extends AppCompatActivity implements OpenBoardListener,
        SetUpToolbarModeListener,
        PageDisplayModeListener,
        OpenDetailedSavePage {

    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    public static final int TOOLBAR_MODE_FULL = 1;
    public static final int TOOLBAR_MODE_BACK_BUTTON = 2;
    public static final int TOOLBAR_MODE_TITLE = 3;

    public static final int PAGE_MODE_FULL = 1;
    public static final int PAGE_MODE_ONLY_TOOLBAR = 2;
    public static final int PAGE_MODE_ONLY_NAVBAR = 3;
    public static final int PAGE_MODE_EMPTY = 4;

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
    public void openBoard(BoardConcrete boardConcrete) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BOARD_INFO, boardConcrete);
        navController.navigate(R.id.concrete_board_fragment, bundle);
    }

    @Override
    public void setMode(int mode, String title) {
        switch (mode){
            case TOOLBAR_MODE_FULL:
                setToolbarMode(true, true);
                setTitleToolbar(title);
                break;
            case TOOLBAR_MODE_BACK_BUTTON:
                setToolbarMode(true, false);
                break;
            case TOOLBAR_MODE_TITLE:
                setToolbarMode(false, true);
                setTitleToolbar(title);
                break;
        }
    }

    private void setToolbarMode(boolean displayBackButton, boolean displayTitle){
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(displayBackButton);
                actionBar.setDisplayShowHomeEnabled(displayBackButton);
                actionBar.setDisplayShowTitleEnabled(displayTitle);
            }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack();
            return true;
        }
        return false;
    }

    private void setTitleToolbar(String titleToolbar){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleToolbar);
        }
    }

    @Override
    public void setPageMode(int mode) {
        switch (mode){
            case PAGE_MODE_FULL:
                setPageDisplay(true, true);
                break;
            case PAGE_MODE_ONLY_NAVBAR:
                setPageDisplay(false, true);
                break;
            case PAGE_MODE_ONLY_TOOLBAR:
                setPageDisplay(true, false);
                break;
            case PAGE_MODE_EMPTY:
                setPageDisplay(false, false);
                break;
        }
    }

    private void setPageDisplay(boolean isToolbar, boolean isNavBar){
        toolbar.setVisibility((isToolbar)
                ? View.VISIBLE
                : View.GONE);
        bottomNavigationView.setVisibility((isNavBar)
                ? View.VISIBLE
                : View.GONE);
    }

    @Override
    public void openSavePage(int typePage) {

    }
}
