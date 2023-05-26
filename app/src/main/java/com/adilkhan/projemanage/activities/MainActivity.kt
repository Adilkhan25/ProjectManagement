package com.adilkhan.projemanage.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.adilkhan.projemanage.R
import com.adilkhan.projemanage.databinding.ActivityMainBinding
import com.adilkhan.projemanage.databinding.AppBarMainBinding
import com.adilkhan.projemanage.firebase.FireStoreClass
import com.adilkhan.projemanage.models.User
import com.bumptech.glide.Glide
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
        // TODO (Step 3: Call a function to get the current logged in user details.)
        // START
        // Get the current logged in user details.
        FireStoreClass().loadUserData(this@MainActivity)
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

                // TODO (Step 2: Launch the my profile activity for Result.)
                // START
                startActivityForResult(Intent(this@MainActivity, MyProfileActivity::class.java), MY_PROFILE_REQUEST_CODE)
                // END
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
    // TODO (Step 5: Create a function to update the user details in the navigation view.)
    // START
    /**
     * A function to get the current user details from firebase.
     */
    fun updateNavigationUserDetails(user: User) {
        // The instance of the header view of the navigation view.
        val headerView = bindingMainActivity.navView.getHeaderView(0)

        // The instance of the user image of the navigation view.
        val navUserImage = headerView.findViewById<ImageView>(R.id.iv_user_image)

        // Load the user image in the ImageView.
        Glide
            .with(this@MainActivity)
            .load(user.image) // URL of the image
            .centerCrop() // Scale type of the image.
            .placeholder(R.drawable.ic_user_place_holder) // A default place holder
            .into(navUserImage) // the view in which the image will be loaded.

        // The instance of the user name TextView of the navigation view.
        val navUsername = headerView.findViewById<TextView>(R.id.tv_username)
        // Set the user name
        navUsername.text = user.name
    }
    // END


    // TODO (Step 4: Add the onActivityResult function and check the result of the activity for which we expect the result.)
    // START
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
            && requestCode == MY_PROFILE_REQUEST_CODE
        ) {
            // Get the user updated details.
            FireStoreClass().loadUserData(this@MainActivity)
        } else {
            Log.e("Cancelled", "Cancelled")
        }
    }
    // END

    // TODO (Step 1: Create a companion object and a constant variable for My profile Screen result.)
    // START
    /**
     * A companion object to declare the constants.
     */
    companion object {
        //A unique code for starting the activity for result
        const val MY_PROFILE_REQUEST_CODE: Int = 11
    }
    // END
}