package com.ru.mirea.malinovskiy.mireaproject;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ru.mirea.malinovskiy.mireaproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfig;
    private ActivityMainBinding viewBinding;
    private NavController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация View Binding
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        // Настройка Toolbar
        setSupportActionBar(viewBinding.appBarMain.toolbar);

        // Настройка Floating Action Button
        setupFloatingActionButton();

        // Настройка навигации
        setupNavigation();
    }

    private void setupFloatingActionButton() {
        FloatingActionButton actionButton = viewBinding.appBarMain.fab;
        actionButton.setOnClickListener(view ->
                Snackbar.make(view, "Замените это действие своим", Snackbar.LENGTH_LONG)
                        .setAction("Действие", null)
                        .setAnchorView(R.id.fab)
                        .show()
        );
    }

    private void setupNavigation() {
        DrawerLayout drawerLayout = viewBinding.drawerLayout;
        NavigationView navView = viewBinding.navView;

        // Конфигурация элементов навигации
        appBarConfig = new AppBarConfiguration.Builder(
                R.id.nav_home,         // Главная
                R.id.nav_gallery,      // Галерея
                R.id.nav_slideshow,    // Слайд-шоу
                R.id.nav_webview,      // Веб-вью
                R.id.nav_data          // Данные
        )
                .setOpenableLayout(drawerLayout)
                .build();

        // Инициализация контроллера навигации
        navigationController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Настройка ActionBar с навигацией
        NavigationUI.setupActionBarWithNavController(this, navigationController, appBarConfig);

        // Настройка NavigationView с контроллером
        NavigationUI.setupWithNavController(navView, navigationController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Инфлейт меню
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        navigationController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navigationController, appBarConfig)
                || super.onSupportNavigateUp();
    }
}