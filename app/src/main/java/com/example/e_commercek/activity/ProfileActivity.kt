package com.example.e_commercek.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commercek.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private var back_btnIV_AP: ImageView? = null
    private var nameTIETPro: TextInputEditText? = null
    private var emailTIETPro: TextInputEditText? = null
    private var phoneTIETPro: TextInputEditText? = null
    private var addressTIETPro: TextInputEditText? = null
    var btnLogOut: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        nameTIETPro!!.setText(
            getSharedPreferences("CREDENTIALS", MODE_PRIVATE).getString(
                "NAME",
                "Arijit Singh"
            )
        )
        emailTIETPro!!.setText(
            getSharedPreferences("CREDENTIALS", MODE_PRIVATE).getString(
                "EMAIL",
                "arijitsingh2003@gmail.com"
            )
        )
        phoneTIETPro!!.setText(
            getSharedPreferences("CREDENTIALS", MODE_PRIVATE).getString(
                "PHONE",
                "6589745636"
            )
        )
        addressTIETPro!!.setText(
            getSharedPreferences(
                "CREDENTIALS",
                MODE_PRIVATE
            ).getString("ADDRESS", "Lak Pin: 514789")
        )


        back_btnIV_AP!!.setOnClickListener { onBackPressed() }

        btnLogOut!!.setOnClickListener {
            val sp = getSharedPreferences("CREDENTIALS", MODE_PRIVATE)
            val editor = sp.edit()
            editor.clear()
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@ProfileActivity, LogInSignUpActivity::class.java))
            HomeScreenActivity.activity!!.finish()
            finish()
        }
    }
    private fun init() {
        back_btnIV_AP = findViewById(R.id.back_btnIV_AP)
        back_btnIV_AP = findViewById(R.id.back_btnIV_AP)
        nameTIETPro = findViewById(R.id.nameTIETPro)
        emailTIETPro = findViewById(R.id.emailTIETPro)
        phoneTIETPro = findViewById(R.id.phoneTIETPro)
        addressTIETPro = findViewById(R.id.addressTIETPro)
        btnLogOut = findViewById(R.id.btnLogOut)
    }

}