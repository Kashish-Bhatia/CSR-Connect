package com.example.firebaseauthenticationapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthenticationapp.R

class ViewPagerAdapter (var charityImgList: ArrayList<Int>): RecyclerView.Adapter<ViewPagerAdapter.CharityViewholder>(){
    inner class CharityViewholder(charityView: View): RecyclerView.ViewHolder(charityView) {
        val charityImage: ImageView=charityView.findViewById(R.id.charityImg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityViewholder {
        return CharityViewholder(LayoutInflater.from(parent.context).inflate(R.layout.viewpager_quote, parent, false))
    }

    override fun getItemCount(): Int {
        return charityImgList.size
    }

    override fun onBindViewHolder(holder: CharityViewholder, position: Int) {
        holder.charityImage.setImageResource(charityImgList[position])
    }
}