package com.example.magasin

import MainViewModel
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.magasin.databinding.ActivityMainBinding

/**
 * Activité principale de l'application qui configure la navigation et gère les interactions de la barre de navigation inférieure.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Configure le contenu de l'interface utilisateur et initialise la navigation.
     * @param savedInstanceState Bundle contenant l'état précédemment enregistré de l'activité.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_shop, R.id.navigation_cart
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    /**
     * Crée le menu des options pour l'activité, permettant la gestion du mode administrateur.
     * @param menu Le menu dans lequel les items sont placés.
     * @return true pour afficher le menu.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        return true
    }

    /**
     * Gère les actions sur les items du menu.
     * @param item L'item du menu qui a été sélectionné.
     * @return true si l'événement a été géré, false sinon.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mn_admin -> {
                val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
                mainViewModel.toggleAdminMode()
                Toast.makeText(
                    this,
                    if (mainViewModel.isAdminMode.value == true)
                        getString(R.string.admin_mode_activated)
                    else
                        getString(R.string.admin_mode_deactivated),
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
