package com.example.firebaseauthenticationapp.database_RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao

interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(product: ProductTable): Long


    @Query("SELECT * FROM product_table")
    fun getAllDetails():List<ProductTable>

    @Query("SELECT * FROM product_table WHERE room_timestamp = :timestamp LIMIT 1")
    fun getDetailsByTimestamp(timestamp: String): ProductTable?

    @Update
    fun updateProductDetails(product: ProductTable)

    @Query("DELETE from product_table where room_timestamp = :productKaTime")
    fun deleteUser(productKaTime: String)





}