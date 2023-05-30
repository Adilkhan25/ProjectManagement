package com.adilkhan.projemanage.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.adilkhan.projemanage.R
import com.adilkhan.projemanage.adapter.BoardItemsAdapter
import com.adilkhan.projemanage.databinding.ActivityMainBinding
import com.adilkhan.projemanage.firebase.FireStoreClass
import com.adilkhan.projemanage.models.Board
import com.adilkhan.projemanage.models.User
import com.adilkhan.projemanage.utils.Constants
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var bindingMainActivity:ActivityMainBinding
    private lateinit var mUserName: String

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(bindingMainActivity.root)

        setupActionBar()

        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        bindingMainActivity.navView.setNavigationItemSelectedListener(this)

        // Get the current logged in user details.
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().loadUserData(this@MainActivity,true)

        bindingMainActivity.toolbarMAin.fabCreateBoard.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateBoardActivity::class.java)
            intent.putExtra(Constants.NAME, mUserName)
            // TODO (Step 2: Here now pass the unique code for StartActivityForResult.)
            // START
            startActivityForResult(intent, CREATE_BOARD_REQUEST_CODE)
            // END
        }
    }

    override fun onBackPressed() {
        if (bindingMainActivity.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            bindingMainActivity.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // A double back press function is added in Base Activity.
            doubleBackToExit()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_my_profile -> {

                startActivityForResult(Intent(this@MainActivity, MyProfileActivity::class.java), MY_PROFILE_REQUEST_CODE)
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
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
            && requestCode == MY_PROFILE_REQUEST_CODE
        ) {
            // Get the user updated details.
            FireStoreClass().loadUserData(this@MainActivity)
        }
        // TODO (Step 4: Here if the result is OK get the updated boards list.)
        // START
        else if (resultCode == Activity.RESULT_OK
            && requestCode == CREATE_BOARD_REQUEST_CODE
        ) {
            // Get the latest boards list.
            FireStoreClass().getBoardsList(this@MainActivity)
        }
        // END
        else {
            Log.e("Cancelled", "Cancelled")
        }
    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        setSupportActionBar(bindingMainActivity.toolbarMAin.toolbarMainActivity)
        bindingMainActivity.toolbarMAin.toolbarMainActivity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        bindingMainActivity.toolbarMAin.toolbarMainActivity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

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

    /**
     * A function to get the current user details from firebase.
     */
    fun updateNavigationUserDetails(user: User,readBoardList:Boolean) {
        hideProgressDialog()
        mUserName = user.name

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
        if(readBoardList) {
            FireStoreClass().getBoardsList(this@MainActivity)
        }
    }
    /**
     * A function to populate the result of BOARDS list in the UI i.e in the recyclerView.
     */
    fun populateBoardsListToUI(boardsList: ArrayList<Board>) {

        hideProgressDialog()

        if (boardsList.size > 0) {

            bindingMainActivity.toolbarMAin.mainContentUi.rvBoardsList.visibility = View.VISIBLE
            bindingMainActivity.toolbarMAin.mainContentUi.tvNoBoardsAvailable.visibility = View.GONE

            bindingMainActivity.toolbarMAin.mainContentUi.rvBoardsList.layoutManager = LinearLayoutManager(this@MainActivity)
            bindingMainActivity.toolbarMAin.mainContentUi.rvBoardsList.setHasFixedSize(true)

            // Create an instance of BoardItemsAdapter and pass the boardList to it.
            val adapter = BoardItemsAdapter(this@MainActivity, boardsList)
            bindingMainActivity.toolbarMAin.mainContentUi.rvBoardsList.adapter = adapter // Attach the adapter to the recyclerView.

        } else {
            bindingMainActivity.toolbarMAin.mainContentUi.rvBoardsList.visibility = View.GONE
            bindingMainActivity.toolbarMAin.mainContentUi.tvNoBoardsAvailable.visibility = View.VISIBLE
        }
    }

    /**
     * A companion object to declare the constants.
     */
    companion object {
        //A unique code for starting the activity for result
        const val MY_PROFILE_REQUEST_CODE: Int = 11
        // TODO (Step 1: Add a unique code for starting the create board activity for result)
        const val CREATE_BOARD_REQUEST_CODE: Int = 12
    }
}