package com.example.myapplication
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityLoginScreenBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


@Suppress("DEPRECATION")
class LoginScreenActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient : GoogleSignInClient
    lateinit var binding : ActivityLoginScreenBinding
    val RC_SIGN_IN : Int = 100
    private val EMAIL = "email"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_login_screen)
        setonClickListeners()
    }

    private fun setonClickListeners() {
        binding.signInButton.setOnClickListener({
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val account = GoogleSignIn.getLastSignedInAccount(this)
            signIn();


        })

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val info = GoogleSignIn.getLastSignedInAccount(this)
            if (info != null) {
                val personName = info.displayName
                val personGivenName = info.givenName
                val personFamilyName = info.familyName
                val personEmail = info.email
                val personId = info.id
                var personPhoto : Uri ? = info.photoUrl
                val photoUrl = personPhoto.toString()
                var intent = Intent(applicationContext,LoginDetailsActivity::class.java)
                intent.putExtra("id",personId)
                intent.putExtra("personname",personName)
                intent.putExtra("email",personEmail)
                intent.putExtra("photo",photoUrl)
                startActivity(intent)

            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Failed", "signInResult:failed code=" + e.statusCode)

        }


    }


}