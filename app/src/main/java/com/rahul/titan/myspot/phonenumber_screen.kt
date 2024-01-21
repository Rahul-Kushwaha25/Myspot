package com.rahul.titan.myspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rahul.titan.myspot.databinding.ActivityPhonenumberScreenBinding

class phonenumber_screen : AppCompatActivity() {

    private lateinit var binding: ActivityPhonenumberScreenBinding
    lateinit var auth : FirebaseAuth
    var currentuser : FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhonenumberScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        currentuser = auth.currentUser


        if (currentuser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.loginOtpSentBtn.setOnClickListener{
            if (binding.loginPhoneNumber.text!!.isEmpty()){
                Toast.makeText(this,"enter mobile number",Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this,otp_screen::class.java)
                intent.putExtra("phone",binding.loginPhoneNumber.text.toString())
                startActivity(intent)
            }
        }


    }


}