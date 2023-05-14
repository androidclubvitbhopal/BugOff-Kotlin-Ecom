package com.example.e_commercek.activity

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.e_commercek.R
import com.example.e_commercek.fragments.LogIn
import com.example.e_commercek.fragments.SignUp
import com.google.firebase.auth.FirebaseAuth

class LogInSignUpActivity : AppCompatActivity() {
    private var frameLayoutLogSignActivity: FrameLayout? = null
    private var mAuth: FirebaseAuth? = null

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, HomeScreenActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_signup_activity)
        initialize()
        fragmentTransaction(SignUp())
    }

    private fun initialize() {
        frameLayoutLogSignActivity = findViewById(R.id.frameLayoutLogSignActivity)
        mAuth=FirebaseAuth.getInstance()
    }

    private fun fragmentTransaction(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutLogSignActivity, fragment)
        fragmentTransaction.commit()
    }
}