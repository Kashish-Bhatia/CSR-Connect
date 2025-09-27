package com.example.firebaseauthenticationapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauthenticationapp.R
import com.example.firebaseauthenticationapp.database_RoomDB.ProductTable

class ProductAdapter (private var itemList: List<ProductTable>):RecyclerView.Adapter<ProductAdapter.Viewholder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)

    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener=clickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.each_product, parent, false)
        return Viewholder(view, mListener)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item=itemList[position]
        holder.productName.text=item.room_productName
        holder.productCategory.text=item.room_category
        holder.productCompany.text=item.room_companyOfTheProduct
        holder.productExpiry.text=item.room_date
        holder.productLocation.text=item.room_location

    }

    class Viewholder (val itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val productName:TextView=itemView.findViewById(R.id.productKaNaam)
        val productCategory:TextView=itemView.findViewById(R.id.productKiCategory)
        val productCompany:TextView=itemView.findViewById(R.id.productKiCompany)
        val productExpiry:TextView=itemView.findViewById(R.id.productKiExpiry)
        val productLocation:TextView=itemView.findViewById(R.id.productKiLocation)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
    fun setFilteredList(itemList: ArrayList<ProductTable>){
        this.itemList = itemList
        notifyDataSetChanged()


    }

}