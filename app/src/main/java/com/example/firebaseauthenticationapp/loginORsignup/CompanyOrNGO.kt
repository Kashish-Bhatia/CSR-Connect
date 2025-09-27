package com.example.firebaseauthenticationapp.loginORsignup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.firebaseauthenticationapp.HomePage
import com.example.firebaseauthenticationapp.R

class CompanyOrNGO : AppCompatActivity() {
    private lateinit var companyHuMain:CardView
    private lateinit var ngoHuMain:CardView
    private lateinit var user:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_or_ngo)
        companyHuMain=findViewById(R.id.companyCard)
        ngoHuMain=findViewById(R.id.ngoCard)


        companyHuMain.setOnClickListener{
            val intent123= Intent(this, HomePage::class.java)
            user="Company"
            intent123.putExtra("User",user)
            startActivity(intent123)
        }

        ngoHuMain.setOnClickListener{
            val intent123= Intent(this, HomePage::class.java)
            user="NGO"
            intent123.putExtra("User",user)
            startActivity(intent123)
        }

    }
}