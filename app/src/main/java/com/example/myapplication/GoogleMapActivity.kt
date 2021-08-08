package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityGoogleMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class GoogleMapActivity : AppCompatActivity() {
    var smf: SupportMapFragment? = null
    var client: FusedLocationProviderClient? = null
    lateinit var binding : ActivityGoogleMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_google_map)
        smf = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        client = LocationServices.getFusedLocationProviderClient(this)

        Dexter.withContext(applicationContext)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    getmylocation()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()


    }

    private fun getmylocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(applicationContext, "Map Failed", Toast.LENGTH_SHORT).show()
            return
        }

            val task = client!!.lastLocation
            task.addOnSuccessListener { location ->
                smf!!.getMapAsync { googleMap ->
                    val latLng  = LatLng(location.latitude, location.longitude)
                    val markerOptions = MarkerOptions().position(latLng).title("You are here...!!")
                    googleMap.addMarker(markerOptions)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

            }
        }



    }


}
