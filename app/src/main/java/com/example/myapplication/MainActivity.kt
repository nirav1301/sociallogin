package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        java.printHashKey(this)

       setOnclickListerns()

    }

    private fun setOnclickListerns() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.btnLoginOptions.setOnClickListener({
            var intent = Intent(applicationContext,LoginScreenActivity::class.java)
            startActivity(intent)

        })
        binding.btnGoogleMap.setOnClickListener({
            var intent = Intent(applicationContext,GoogleMapActivity::class.java)
            startActivity(intent)
        }
        )
    }
}