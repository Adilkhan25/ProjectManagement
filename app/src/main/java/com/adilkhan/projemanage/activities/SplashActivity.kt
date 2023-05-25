package com.adilkhan.projemanage.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import com.adilkhan.projemanage.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // TODO (Step 6: Add the full screen flags here.)
        // This is used to hide the status bar and make the splash screen as a full screen activity.
        // START
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // END

        // TODO (Step 7: Add the file in the custom font file to the assets folder. And add the below line of code to apply it to the title TextView.)
        // Steps for adding the assets folder are :
        // Right click on the "app" package and GO TO ==> New ==> Folder ==> Assets Folder ==> Finish.
        // START
        // This is used to get the file from the assets folder and set it to the title textView.
        val typeface: Typeface =
            Typeface.createFromAsset(assets, "carbon bl.ttf")
       val tvAppName = findViewById<TextView>(R.id.tv_app_name)
        tvAppName.typeface = typeface
        //END
    }
}//END