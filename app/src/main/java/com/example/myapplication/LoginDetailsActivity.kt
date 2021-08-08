package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityLoginDetailsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class LoginDetailsActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient : GoogleSignInClient
    lateinit var binding : ActivityLoginDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_details)
        var id = intent.getStringExtra("id")
        var name = intent.getStringExtra("personname")
        var email = intent.getStringExtra("email")
        var photo = intent.getStringExtra("photo")
        Toast.makeText(this, photo, Toast.LENGTH_SHORT).show()
        Glide.with(this)
            .load(photo)
            .placeholder(R.drawable.common_google_signin_btn_icon_dark)
            .into(binding.ivProfilePic)
        binding.tvPersonId.text = id
        binding.tvPersonEmail.text = email
        binding.tvPersonName.text = name
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.btnSignOut.setOnClickListener({
            signOut()
        })

    }

    private fun signOut() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("LogOut")
        //set message for alert dialog
        builder.setMessage("Are You Sure?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    revokeAccess()
                }



        }
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
    private fun revokeAccess() {
         mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                Toast.makeText(this, "User Logged out", Toast.LENGTH_SHORT).show()
            }
    }

}