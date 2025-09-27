package com.example.firebaseauthenticationapp.loginORsignup

//import android.credentials.GetCredentialRequest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthenticationapp.databinding.ActivitySignUpBinding

class SignUPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // Instantiate a Google sign-in request
//        val googleIdOption = GetGoogleIdOption.Builder()
//            // Your server's client ID, not your Android client ID.
//            .setServerClientId(getString(R.string.client_id))
//            // Only show accounts previously used to sign in.
//            .setFilterByAuthorizedAccounts(true)
//            .build()
//
//// Create the Credential Manager request
//        val request = GetCredentialRequest.Builder()
//            .addCredentialOption(googleIdOption)
//            .build()

        binding.signupTv.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signUpButton.setOnClickListener{
            val email= binding.emailSignup.text.toString()
            val password= binding.passSignup.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                MainActivity.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this, CompanyOrNGO::class.java))
                    }
                }
                    .addOnFailureListener{
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
            }
        }



    }
}