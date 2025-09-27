package com.example.firebaseauthenticationapp.database_RoomDB


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product_table")
data class ProductTable(
                        var rooom_productId:Int=0,
                        var room_productName:String,
                        var room_category: String,
                        var room_companyOfTheProduct: String,
                        var room_quantity: Int,
                        var room_date: String,
                        var room_condition: String,
                        var room_location: String,
                        var isSynced: Boolean = false,
                        @PrimaryKey
                        val room_timestamp: String
): Parcelable
