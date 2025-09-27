package com.example.firebaseauthenticationapp.database_RoomDB

import android.content.Context
import androidx.room.Room

object DBHandler {
    @Volatile
    private var INSTANCE: ProductDatabase?= null
    fun getDatabase(context: Context):ProductDatabase{
        return Room.databaseBuilder(context, ProductDatabase::class.java, "product_database")
            .allowMainThreadQueries().build()
    }
}