package com.example.magasin

import MainViewModel
import android.os.Bundle
import android.util.Log
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

class wvMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)



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

        // Exemple de modification de l'état admin à partir de l'Activity
        val isAdmin = mainViewModel.isAdmin.value ?: false



        // Supposons que tu changes l'état admin en appuyant sur un bouton

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("marche", "60")


        when (item.itemId) {
            R.id.mn_admin -> {
                val currentIsAdmin = mainViewModel.isAdmin.value ?: false
                mainViewModel.isAdmin.value = !currentIsAdmin

                Log.d("AdminMode", "Admin mode switched to: ${!currentIsAdmin}")
                    return true

            }

            else -> {
                return super.onOptionsItemSelected(item)
            }

        }

    }
}
