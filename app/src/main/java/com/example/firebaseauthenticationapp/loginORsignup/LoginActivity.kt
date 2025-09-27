package com.example.firebaseauthenticationapp.loginORsignup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthenticationapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title= "Register"

        binding.loginTv.setOnClickListener{
            startActivity(Intent(this, SignUPActivity::class.java))
        }

        binding.loginButton.setOnClickListener{
            val email= binding.emailLogin.text.toString()
            val password= binding.passLogin.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                MainActivity.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this, CompanyOrNGO::class.java))
                    }
                }
                    .addOnFailureListener{
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            }
        }




    }
}