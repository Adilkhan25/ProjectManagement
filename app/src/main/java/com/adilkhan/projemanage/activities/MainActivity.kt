package com.adilkhan.projemanage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.adilkhan.projemanage.R
import com.adilkhan.projemanage.databinding.ActivityMainBinding
import com.adilkhan.projemanage.databinding.AppBarMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var bindingMainActivity:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)
        // TODO (Step 4: Call the setup action bar function here.)
        // START
        setupActionBar()
        // END

        // TODO (Step 8: Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.)
        // START
        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        bindingMainActivity.navView.setNavigationItemSelectedListener(this)
        // END
    }
    // TODO (Step 5: Add a onBackPressed function and check if the navigation drawer is open or closed.)
    // START
    override fun onBackPressed() {
        if (bindingMainActivity.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            bindingMainActivity.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // A double back press function is added in Base Activity.
            doubleBackToExit()
        }
    }
    // END
    // TODO (Step 7: Implement members of NavigationView.OnNavigationItemSelectedListener.)
    // START
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // TODO (Step 9: Add the click events of navigation menu items.)
        // START
        when (menuItem.itemId) {
            R.id.nav_my_profile -> {

                Toast.makeText(this@MainActivity, "My Profile", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_sign_out -> {
                // Here sign outs the user from firebase in this device.
                FirebaseAuth.getInstance().signOut()

                // Send the user to the intro screen of the application.
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        bindingMainActivity.drawerLayout.closeDrawer(GravityCompat.START)
        // END
        return true
    }
    // END

    // TODO (Step 1: Create a function to setup action bar.)
    // START
    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {
        // val progressDialogBinding = DialogProgressBinding.inflate(layoutInflater)
       // val bindingAppBarMain = AppBarMainBinding.inflate(layoutInflater)
       // setSupportActionBar(bindingAppBarMain.toolbarMainActivity)
        bindingMainActivity.toolbarMAin.toolbarMainActivity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        // TODO (Step 3: Add click event for navigation in the action bar and call the toggleDrawer function.)
        // START
        bindingMainActivity.toolbarMAin.toolbarMainActivity.setNavigationOnClickListener {
            toggleDrawer()
        }
        // END
    }
    // END

    // TODO (Step 2: Create a function for opening and closing the Navigation Drawer.)
    // START
    /**
     * A function for opening and closing the Navigation Drawer.
     */
    private fun toggleDrawer() {

        if (bindingMainActivity.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            bindingMainActivity.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            bindingMainActivity.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    // END
}