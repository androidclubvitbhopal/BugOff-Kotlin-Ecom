package com.example.e_commercek.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_commercek.R
import com.example.e_commercek.data.USER_MODEL
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class SignUp : Fragment() {

    private var backBtnIVSignUp: ImageView? = null
    private var createAccountBtn: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var userNameTIET: TextInputEditText? = null
    private var phoneTIET: TextInputEditText? = null
    private var emailTIETSU: TextInputEditText? = null
    private var passwordTIETSU: TextInputEditText? = null
    private var addressTIETSU: TextInputEditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_sign_up, container, false)
        initialize(view)

        backBtnIVSignUp!!.setOnClickListener {
            fragmentTransaction(
                LogIn(),
                R.anim.neg_100_to_pos_0,
                R.anim.pos_0_to_pos_100
            )
        }
        createAccountBtn!!.setOnClickListener { newUserSignUp() }
        return view
    }


    private fun initialize(view: View) {
        backBtnIVSignUp = view.findViewById(R.id.backBtnIVSignUp)
        createAccountBtn = view.findViewById(R.id.createAccountBtn)
        mAuth = FirebaseAuth.getInstance()

        userNameTIET = view.findViewById(R.id.userNameTIET)
        phoneTIET = view.findViewById(R.id.phoneTIET)
        emailTIETSU = view.findViewById(R.id.emailTIETSU)
        passwordTIETSU = view.findViewById(R.id.passwordTIETSU)
        addressTIETSU = view.findViewById(R.id.addressTIETSU)
    }


    private fun fragmentTransaction(fragment: Fragment, entry: Int, exit: Int) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(entry, exit)
        fragmentTransaction.replace(R.id.frameLayoutLogSignActivity, fragment)
        fragmentTransaction.commit()
    }

    private fun newUserSignUp() {
        val email = Objects.requireNonNull(emailTIETSU!!.text).toString().trim()
        val password = Objects.requireNonNull(passwordTIETSU!!.text).toString().trim()
        val phone = Objects.requireNonNull(phoneTIET!!.text).toString().trim()
        val name = Objects.requireNonNull(userNameTIET!!.text).toString().trim()
        val address = Objects.requireNonNull(addressTIETSU!!.text).toString().trim()
        if (email != "" && password != "" && phone != "" && name != "" && address != ""&&
            password != "#" && password != "@" && password != "%" && password != "^" && password != "&"
        )
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val myUserModel = USER_MODEL(name, email, phone, address)
                val db = FirebaseDatabase.getInstance()
                val Users = db.getReference("Users")
                Users.child(mAuth!!.currentUser!!.uid)
                    .setValue(myUserModel)
                userNameTIET!!.setText("")
                phoneTIET!!.setText("")
                emailTIETSU!!.setText("")
                passwordTIETSU!!.setText("")
                addressTIETSU!!.setText("")
                Toast.makeText(context, "You have successfully Signed Up", Toast.LENGTH_SHORT)
                    .show()
                fragmentTransaction(LogIn(), R.anim.neg_100_to_pos_0, R.anim.pos_0_to_pos_100)
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(context, "Failed to Signed Up", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_SHORT).show()
    }

}