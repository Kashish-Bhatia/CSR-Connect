package com.example.firebaseauthenticationapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthenticationapp.R
import com.example.firebaseauthenticationapp.classes.Company

class CompanyAdapter(private val companyList: ArrayList<Company>):RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.each_company, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val companyyy= companyList[position]
        holder.companyName.text=companyyy.companyKaName
        holder.companyImg.setImageResource(companyyy.logo)

    }

    class ViewHolder(companyView: View):RecyclerView.ViewHolder(companyView) {
        val companyImg:ImageView= companyView.findViewById(R.id.companyImage)
        val companyName:TextView= companyView.findViewById(R.id.companyName)

    }
}

