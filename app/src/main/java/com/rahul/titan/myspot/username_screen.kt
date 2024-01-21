package com.rahul.titan.myspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rahul.titan.myspot.databinding.ActivityUsernameScreenBinding

class username_screen : AppCompatActivity() {
    private lateinit var binding : ActivityUsernameScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsernameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.usernameNextBtn.setOnClickListener {
            if (binding.usernameEdt.text.isEmpty()){
                Toast.makeText(this,"enter username", Toast.LENGTH_LONG).show()

            }else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", binding.usernameEdt.text.toString())
                startActivity(intent)
            }
        }
    }





}