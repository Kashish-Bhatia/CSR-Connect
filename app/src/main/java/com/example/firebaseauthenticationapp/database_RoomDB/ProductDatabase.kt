package com.example.firebaseauthenticationapp.database_RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductTable::class], version = 1)
abstract class ProductDatabase : RoomDatabase(){
    abstract fun productDAO(): ProductDao
}