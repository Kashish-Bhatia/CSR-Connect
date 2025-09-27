package com.example.firebaseauthenticationapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.firebaseauthenticationapp.database_Firebase.ProductTableModel
import com.example.firebaseauthenticationapp.database_RoomDB.DBHandler
import com.example.firebaseauthenticationapp.database_RoomDB.ProductTable
import com.example.firebaseauthenticationapp.databinding.ActivityCompanyInsertProductBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CompanyInsertProduct : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyInsertProductBinding
    private lateinit var calendar: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyInsertProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cmpProductName = binding.cmpProductName
        val categoryCmp = binding.cmpProductCategory
        val companyCmp = binding.cmpProductCompany
        val dateCmp = binding.cmpProductDate
        val quantityCmp = binding.cmpProductQuantity
        val conditionCmp = binding.cmpProductCondition
        val locationCmp = binding.cmpProductLocation

        calendar = Calendar.getInstance()
        val donateBtn = binding.DonateButton
        val db = DBHandler.getDatabase(this)

        dateCmp.setOnClickListener {
            showDatePicker(dateCmp)
        }

        donateBtn.setOnClickListener {
            if (!cmpProductName.text.isNullOrBlank() && !categoryCmp.text.isNullOrBlank() &&
                !companyCmp.text.isNullOrBlank() &&
                !quantityCmp.text.isNullOrBlank() &&
                !conditionCmp.text.isNullOrBlank() &&
                !locationCmp.text.isNullOrBlank()
            ) {
                val cmpProductName2 = cmpProductName.text.toString()
                val categoryCmp2 = categoryCmp.text.toString()
                val companyCmp2 = companyCmp.text.toString()
                val quantityCmp2 = quantityCmp.text.toString()
                val conditionCmp2 = conditionCmp.text.toString()
                val locationCmp2 = locationCmp.text.toString()


                val selectedDate = dateCmp.text.toString()
                val timestamp = System.currentTimeMillis().toString()

                val product = ProductTable(
                    room_productName = cmpProductName2,
                    room_category = categoryCmp2,
                    room_companyOfTheProduct = companyCmp2,
                    room_quantity = quantityCmp2.toInt(),
                    room_condition = conditionCmp2,
                    room_location = locationCmp2,
                    room_date = selectedDate,
                    room_timestamp = timestamp
                )

                lifecycleScope.launch {
                    val insertedId: Long = withContext(Dispatchers.IO) {
                        db.productDAO().insertProduct(product)
                    }

                    if (insertedId != -1L) {  // -1 means ignored due to conflict
                        val insertedProduct: ProductTable? = withContext(Dispatchers.IO) {
                            db.productDAO().getDetailsByTimestamp(timestamp)
                        }

                        if (insertedProduct != null && !insertedProduct.isSynced) {
                            dbRef = FirebaseDatabase.getInstance().getReference("NewProducts")

                            val firebaseProduct = toFirebaseModel(insertedProduct)
                            dbRef.child(firebaseProduct.row_timestamp ?: "")
                                .setValue(firebaseProduct)

                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Update product as synced in Room DB
                                        lifecycleScope.launch {
                                            withContext(Dispatchers.IO) {
                                                val updatedProduct =
                                                    insertedProduct.copy(isSynced = true)
                                                db.productDAO()
                                                    .updateProductDetails(updatedProduct)
                                            }
                                        }
                                    } else {
                                        Log.e("FirebaseSync", "Sync failed: ${task.exception}")
                                    }
                                }

                        }
                    }
                }

                startActivity(Intent(this, HomePage::class.java))
            }
            else{
                if(cmpProductName.text.isNullOrBlank()){
                    Toast.makeText(this, "Please enter Product name", Toast.LENGTH_SHORT).show()
                }
                else if(categoryCmp.text.isNullOrBlank()){
                    Toast.makeText(this, "Please enter Product categoryy", Toast.LENGTH_SHORT).show()
                }
                else if(dateCmp.text.isNullOrBlank()){
                    Toast.makeText(this, "Please enter Date of Expiry", Toast.LENGTH_SHORT).show()
                }
                else if(companyCmp.text.isNullOrBlank()){
                    Toast.makeText(this, "Please enter comapany name", Toast.LENGTH_SHORT).show()
                }
                else if(quantityCmp.text.isNullOrBlank()){
                    Toast.makeText(this, "Please enter Product quantity", Toast.LENGTH_SHORT).show()
                }
                else if(conditionCmp.text.isNullOrBlank()){
                    Toast.makeText(this, "Please enter condition of the Product", Toast.LENGTH_SHORT).show()
                }
                else if(locationCmp.text.isNullOrBlank()){
                    Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show()
                }
            }


        }



    }







    private fun showDatePicker(editText: EditText) {
        datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update the calendar with the selected date
                calendar.set(year, month, dayOfMonth)

                // Format the selected date to "dd/MM/yyyy"
                val selectedDate =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)

                // Set the formatted date to the TextInputEditText
                editText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()

    }



    private fun toFirebaseModel(roomProduct: ProductTable): ProductTableModel {
        return ProductTableModel(
            row_productId = roomProduct.rooom_productId,
            row_productName = roomProduct.room_productName,
            row_category = roomProduct.room_category,
            row_companyOfTheProduct = roomProduct.room_companyOfTheProduct,
            row_quantity = roomProduct.room_quantity,
            row_date = roomProduct.room_date,
            row_condition = roomProduct.room_condition,
            row_location = roomProduct.room_location,
            isSynced = roomProduct.isSynced,
            row_timestamp = roomProduct.room_timestamp
        )
    }

    private fun toRoomClass(firebaseProduct: ProductTableModel): ProductTable {
        return ProductTable(
            rooom_productId = firebaseProduct.row_productId ?: 0,
            room_productName = firebaseProduct.row_productName ?: "",
            room_category = firebaseProduct.row_category ?: "",
            room_companyOfTheProduct = firebaseProduct.row_companyOfTheProduct ?: "",
            room_quantity = firebaseProduct.row_quantity ?: 0,
            room_date = firebaseProduct.row_date ?: "",
            room_condition = firebaseProduct.row_condition ?: "",
            room_location = firebaseProduct.row_location ?: "",
            isSynced = firebaseProduct.isSynced,
            room_timestamp = firebaseProduct.row_timestamp ?: ""
        )
    }

}

//dbRef.child(timestamp).setValue(product)

//else{
//    if(cmpProductName.text.isNullOrBlank()){
//        Toast.makeText(this, "Please enter Product name", Toast.LENGTH_SHORT).show()
//    }
//    if(categoryCmp.text.isNullOrBlank()){
//        Toast.makeText(this, "Please enter Product categoryy", Toast.LENGTH_SHORT).show()
//    }
//    if(companyCmp.text.isNullOrBlank()){
//        Toast.makeText(this, "Please enter comapany name", Toast.LENGTH_SHORT).show()
//    }
//    if(quantityCmp.text.isNullOrBlank()){
//        Toast.makeText(this, "Please enter Product quantity", Toast.LENGTH_SHORT).show()
//    }
//    if(conditionCmp.text.isNullOrBlank()){
//        Toast.makeText(this, "Please enter condition of the Product", Toast.LENGTH_SHORT).show()
//    }
//    if(locationCmp.text.isNullOrBlank()){
//        Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show()
//    }
//}