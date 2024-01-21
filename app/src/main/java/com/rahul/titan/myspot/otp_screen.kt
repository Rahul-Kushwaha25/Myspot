package com.rahul.titan.myspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.rahul.titan.myspot.databinding.ActivityOtpScreenBinding
import java.util.concurrent.TimeUnit

class otp_screen : AppCompatActivity() {
    private lateinit var binding: ActivityOtpScreenBinding
    lateinit var auth : FirebaseAuth
    private  lateinit var verificationid : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val phonenumber = "+91"+intent.getStringExtra("phone")
        Log.d("shri", "onCreate: $phonenumber")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phonenumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@otp_screen,"verification failed",Toast.LENGTH_LONG).show()

                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    verificationid = p0
                }

            }) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.loginOtpNextBtn.setOnClickListener{
            if (binding.loginOtpEdt.text!!.isEmpty()){
                Toast.makeText(this@otp_screen,"Enter OTP",Toast.LENGTH_LONG).show()
            }else{
                val credential = PhoneAuthProvider.getCredential(verificationid,binding.loginOtpEdt.text.toString())

                auth.signInWithCredential(credential).addOnCompleteListener{
                    if (it.isSuccessful){
                        startActivity(Intent(this,username_screen::class.java))
                    }else{
                        Toast.makeText(this@otp_screen,"Error occured",Toast.LENGTH_LONG).show()

                    }
                }
            }
        }

    }
}