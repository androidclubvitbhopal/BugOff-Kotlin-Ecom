package com.example.e_commercek.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_commercek.R
import com.example.e_commercek.activity.HomeScreenActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class LogIn : Fragment() {
    private var don_t_have_acc: TextView? = null
    private var btnLogInFragment: Button? = null
    private var mAuth: FirebaseAuth? = null

    private var emailTIETLI: TextInputEditText? = null
    private var passwordTIETLI: TextInputEditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_log_in, container, false)
        initialize(view)

        don_t_have_acc!!.setOnClickListener { fragmentTransaction(SignUp()) }
        btnLogInFragment!!.setOnClickListener { existingUserLogIn() }
        return view;
    }

    private fun initialize(view: View) {
        mAuth = FirebaseAuth.getInstance()
        don_t_have_acc = view.findViewById(R.id.don_t_have_acc)
        btnLogInFragment = view.findViewById(R.id.btnLogInFragment)
        emailTIETLI = view.findViewById(R.id.emailTIETLI)
        passwordTIETLI = view.findViewById(R.id.passwordTIETLI)
    }


    private fun fragmentTransaction(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.pos_100_to_pos_0, R.anim.pos_0_to_neg_100)
        fragmentTransaction.replace(R.id.frameLayoutLogSignActivity, fragment)
        fragmentTransaction.commit()
    }

    private fun existingUserLogIn() {
        val dialog = ProgressDialog(requireContext())
        dialog.setTitle("Verification")
        dialog.setMessage("Checking credentials in the database")
        dialog.show()
        val email:String = Objects.requireNonNull(emailTIETLI!!.text).toString().trim()
        val password:String = Objects.requireNonNull(passwordTIETLI!!.text).toString().trim()
        if (email != "" && password != "")
            mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(requireContext(), HomeScreenActivity::class.java)
                    val db = FirebaseDatabase.getInstance()
                    val node = db.getReference("Users").child(mAuth!!.currentUser!!.uid)
                    node.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (task.result.exists()) {
                                val dataSnapshot = task.result
                                val name = dataSnapshot.child("name").value as String?
                                val email = dataSnapshot.child("email").value as String?
                                val phone = dataSnapshot.child("phone").value as String?
                                val address = dataSnapshot.child("address").value as String?
                                dialog.dismiss()
                                val sp = requireActivity().getSharedPreferences(
                                    "CREDENTIALS",
                                    Context.MODE_PRIVATE
                                )
                                val editor = sp.edit()
                                editor.putString("NAME", name)
                                editor.putString("EMAIL", email)
                                editor.putString("PHONE", phone)
                                editor.putString("ADDRESS", address)
                                editor.apply()
                                startActivity(intent)
                                //Log.d("AddressOf",address);
                                requireActivity().finish()
                            } else {
                                dialog.dismiss()
                                Log.d("USERLOG", "User does not exist")
                                requireActivity().finish()
                            }
                        } else {
                            dialog.dismiss()
                            Toast.makeText(requireContext(), "Problem", Toast.LENGTH_SHORT).show()
                            requireActivity().finish()
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Failed to login", Toast.LENGTH_SHORT).show()
                }
            } else {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}