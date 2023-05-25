package com.adilkhan.projemanage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.adilkhan.projemanage.R
import com.adilkhan.projemanage.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private var bindingSignUp : ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSignUp = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(bindingSignUp?.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
            , WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setUpActionBar()
    }
    private fun setUpActionBar()
    {
        setSupportActionBar(bindingSignUp?.toolbarSignUpActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        bindingSignUp?.toolbarSignUpActivity?.setNavigationOnClickListener { onBackPressed() }
    }
}