package com.adilkhan.projemanage.activities


import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.adilkhan.projemanage.R
import com.adilkhan.projemanage.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUp : BaseActivity() {
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
        bindingSignUp?.btnSignUp?.setOnClickListener {
            registerUser()
        }
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

    // TODO (Step 1 : Here we will register a new user using firebase and we will see the entry in Firebase console.)
    // START
    // Before doing this you need to perform some steps in the Firebase Console.
    // 1. Go to your project detail.
    // 2. Click on the "Authentication" tab which is on the left side in the navigation bar under the "Develop" section.
    // 3. In the Authentication Page, you will see the tab named “Sign-in method”. Click on it.
    // 4. In the sign-in providers, enable the “Email/Password”.
    // 5. Finally, Now you will be able to Register a new user using the Firebase.
    /**
     * A function to register a user to our app using the Firebase.
     * For more details visit: https://firebase.google.com/docs/auth/android/custom-auth
     */
    private fun registerUser() {
        // Here we get the text from editText and trim the space
        val name: String = bindingSignUp?.etName?.text.toString().trim { it <= ' ' }
        val email: String = bindingSignUp?.etEmail?.text.toString().trim { it <= ' ' }
        val password: String = bindingSignUp?.etPassword?.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            // Show the progress dialog.
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->

                            // Hide the progress dialog
                            hideProgressDialog()

                            // If the registration is successfully done
                            if (task.isSuccessful) {

                                // Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                // Registered Email
                                val registeredEmail = firebaseUser.email!!

                                Toast.makeText(
                                    this@SignUp,
                                    "$name you have successfully registered with email id $registeredEmail.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                /**
                                 * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                                 * and send him to Intro Screen for Sign-In
                                 */

                                /**
                                 * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                                 * and send him to Intro Screen for Sign-In
                                 */
                                FirebaseAuth.getInstance().signOut()
                                // Finish the Sign-Up Screen
                                finish()
                            } else {
                                Toast.makeText(
                                    this@SignUp,
                                    task.exception!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })

        }
    }
    // END
    // TODO (Step 10: A function to validate the entries of a new user.)
    // START
    /**
     * A function to validate the entries of a new user.
     */
    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }
    }
    // END
}