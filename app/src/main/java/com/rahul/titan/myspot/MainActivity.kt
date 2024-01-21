package com.rahul.titan.myspot

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rahul.titan.myspot.databinding.ActivityMainBinding
import java.io.IOException

import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    lateinit var auth: FirebaseAuth
    var currentuser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        auth = FirebaseAuth.getInstance()
        currentuser = auth.currentUser

        // if no user is logged in
        if (currentuser == null) {
            startActivity(Intent(this, phonenumber_screen::class.java))
            finish()
        }


        val usernamemain = intent.getStringExtra("username")
        Log.d("shri", "$usernamemain")
        binding.usernamemain.setText(usernamemain)

        binding.locationbtn.setOnClickListener {
            binding.usernamemain.setText(usernamemain)
            checkLocationPermissions()
        }


    }
    fun checkLocationPermissions(){
        Log.d("shri", "inside checkLocationpermision")

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            Log.d("shri", "inside  if of checklocationpersission")

            checkgps()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)
        }
    }

    private fun checkgps() {
        Log.d("shri", "inside checkgps")

    locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        builder.setAlwaysShow(true)

        val result = LocationServices.getSettingsClient(this.applicationContext)
            .checkLocationSettings(builder.build())

        Log.d("shri", "inside checkgps after result $result")


        result.addOnCompleteListener{task->
            try{
                val response = task.getResult(ApiException::class.java)
                getuserLocation()

            }catch (e : ApiException){
                e.printStackTrace()

                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED->try {
                        val resolveApiException = e as ResolvableApiException
                        resolveApiException.startResolutionForResult(this,200)
                    }catch (sendIntentExecption : IntentSender.SendIntentException){

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->{
                    }
                }
            }

        }
    }

    private fun getuserLocation() {
        Log.d("shri", "inside getuserlocation")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener{ task->
            val location = task.getResult()

            if (location!=null){
                try {

                    val geocoder = Geocoder(this, Locale.getDefault())

                    val address = geocoder.getFromLocation(location.latitude,location.longitude,1)

                    val address_line = address?.get(0)?.getAddressLine(0)

                    binding.locationanswer.setText(address_line)
                    Log.d("shri", "address $address")
                    Log.d("shri", "address $address_line")


                    val address_location = address?.get(0)?.getAddressLine(0)

                    openLocation(address_location.toString())


                }catch (e : IOException){

                }
            }

       }
    }

    private fun openLocation(location: String) {

        binding.locationbtn.setOnClickListener {
            if (!binding.locationanswer.text.isEmpty()){
                val uri = Uri.parse("geo:0,0?q=$location")
                val intent  = Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }


        }


    }
}








