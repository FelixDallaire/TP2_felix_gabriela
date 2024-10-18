package com.example.magasin

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.magasin.databinding.ActivityMainBinding

class wvMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val bundle = Bundle()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("marche", "27")

        bundle.putBoolean("isAdmin", false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("marche", "34")


        val navView: BottomNavigationView = binding.navView

        Log.d("marche", "38")


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_shop, R.id.navigation_cart
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("marche", "54")

        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("marche", "60")

        var isAdmin = bundle.getBoolean("isAdmin")
        when (item.itemId) {
            R.id.mn_admin -> {
                if (isAdmin)
                    bundle.putBoolean("isAdmin",false)
                else
                    bundle.putBoolean("isAdmin",true)
                return true         }
            else -> {return super.onOptionsItemSelected(item)}

        }

}
}
