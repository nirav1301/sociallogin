package com.example.myapplication
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityLoginScreenBinding
import com.facebook.*
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
    private var callbackManager : CallbackManager ? = null
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
            signIn()



        })
        binding.fbLoginButton.setOnClickListener({
            Toast.makeText(applicationContext, "Button Clicked", Toast.LENGTH_SHORT).show()
//            facebookLogin()
        })


    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
//        callbackManager?.onActivityResult(requestCode,resultCode,data)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val info = GoogleSignIn.getLastSignedInAccount(this)
            if (info != null) {
                val personName = info.displayName
                val personEmail = info.email
                val personId = info.id
                val personPhoto : Uri ? = info.photoUrl
                val photoUrl = personPhoto.toString()
                val intent = Intent(applicationContext,LoginDetailsActivity::class.java)
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
//    private fun facebookLogin(){
//        binding.fbLoginButton.setPermissions(arrayListOf(EMAIL))
//        callbackManager = CallbackManager.Factory.create()
//        binding.fbLoginButton.registerCallback(callbackManager,
//            object : FacebookCallback<LoginResult?> {
//                override fun onSuccess(loginResult: LoginResult?) {
//
//
//                }
//
//                override fun onCancel() {
//                    Toast.makeText(
//                        applicationContext,
//                        "Something Went wrong",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                override fun onError(error: FacebookException?) {
//                    Toast.makeText(
//                        applicationContext,
//                        "Something Went wrong",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//            })
//        callbackManager = CallbackManager.Factory.create()
//        LoginManager.getInstance().registerCallback(callbackManager,
//            object : FacebookCallback<LoginResult?> {
//                override fun onSuccess(loginResult: LoginResult?) {
//                    val accessToken = AccessToken.getCurrentAccessToken()
//                    if (accessToken != null && !accessToken.isExpired) {
//                        var intent =
//                            Intent(applicationContext, LoginDetailsActivity::class.java)
//                        var id: String? = loginResult?.accessToken?.userId
//                        var photoUrl : String ? = "https://graph.facebook.com/" + loginResult?.accessToken?.userId + "/picture?return_ssl_resources=1"
//                        intent.putExtra("id", id)
//                        intent.putExtra("photo",photoUrl)
//                        startActivity(intent)
//                    }
//
//                }
//
//                override fun onCancel() { // App code
//                }
//
//                override fun onError(exception: FacebookException) { // App code
//                }
//
//            })
//
//    }


}