package com.adilkhan.projemanage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.adilkhan.projemanage.R
import com.adilkhan.projemanage.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private var bindingIntro : ActivityIntroBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingIntro = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(bindingIntro?.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        bindingIntro?.btnSignInIntro?.setOnClickListener {
            startActivity(Intent(this@IntroActivity, SignIn::class.java))
        }
        bindingIntro?.btnSignUpIntro?.setOnClickListener {
            startActivity(Intent(this@IntroActivity, SignUp::class.java))

        }
    }
}