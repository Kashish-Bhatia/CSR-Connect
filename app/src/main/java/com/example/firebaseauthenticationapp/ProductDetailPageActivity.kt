package com.example.firebaseauthenticationapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.firebaseauthenticationapp.database_Firebase.ProductTableModel
import com.example.firebaseauthenticationapp.database_RoomDB.DBHandler
import com.example.firebaseauthenticationapp.database_RoomDB.ProductTable
import com.example.firebaseauthenticationapp.databinding.ActivityProductDetailPageBinding
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProductDetailPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailPageBinding
    private lateinit var timestamp: String
    private lateinit var calendar: Calendar
    private lateinit var newproduct: ProductTable
    private lateinit var datePickerDialog: DatePickerDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameDeLaProduct=binding.nameDeLaProduct
        val companyDeProduct=binding.companyDeProduct
        val dateDeProduct=binding.dateDeProduct
        val conditionDeProduct=binding.conditionDeProduct
        val quantityDeProduct=binding.quantityDeProduct
        val locationDeProduct=binding.locationDeProduct
        val categoryDeProduct=binding.categoryDeProduct
        var thisProductId: Int=0



        val details=intent.getParcelableExtra<ProductTable>("productDetails")
        if(details!=null){
            details.let{
                nameDeLaProduct.text=it.room_productName
                companyDeProduct.text=it.room_companyOfTheProduct
                dateDeProduct.text=it.room_date
                conditionDeProduct.text=it.room_condition
                val quantity= it.room_quantity
                quantityDeProduct.text=quantity.toString()
                locationDeProduct.text=it.room_location
                categoryDeProduct.text=it.room_category
                thisProductId=it.rooom_productId
                timestamp=it.room_timestamp


            }
        }
//        val timestamp = System.currentTimeMillis().toString()

        val thisProduct:ProductTable=ProductTable(
            rooom_productId = thisProductId,
            room_productName=nameDeLaProduct.text.toString(),
                    room_category=categoryDeProduct.text.toString(),
                    room_companyOfTheProduct=companyDeProduct.text.toString(),
                    room_quantity=quantityDeProduct.text.toString().toInt(),
                    room_date=dateDeProduct.text.toString(),
                    room_condition=conditionDeProduct.text.toString(),
                    room_location=locationDeProduct.text.toString(),
            room_timestamp = timestamp

        )
        //<------------Company or NGO----->/////////////////////////////////////////////////////////////////////////////////////
        val intentFromNgoOrCompany= intent

        val userType= intentFromNgoOrCompany.getStringExtra("User")
        Log.d("userrrrrrr", "${userType}")
        if(userType=="Company"){
            showCompanyDisplay()

        }
        if(userType=="NGO"){
            showNGODisplay()


        }

            ////<----DELETE BUTTON------>///////////////////////////////////////////////


        val deleteBtn= binding.delBtn
        deleteBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Deleting Product!!")
            builder.setMessage("Are you sure, you want to delete this product?")
            builder.setPositiveButton(android.R.string.yes) { dialog1, which ->
                deleteFunction(timestamp)
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton(android.R.string.no) { dialog1, which ->
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            builder.show()

        }

        /////<----UPDATE BUTTON------>///////////////////////////////////
        val updateBtn= binding.updateBtn
        updateBtn.setOnClickListener{
            openUpdateDialog(timestamp, thisProduct)

        }


    }
    private fun deleteFunction(timestamp: String){
        val db = DBHandler.getDatabase(this)
        db.productDAO().deleteUser(timestamp)
        deleteFromFirebase(timestamp)
        finish()
    }

    private fun deleteFromFirebase(timestamp: String) {
       val dbRef= FirebaseDatabase.getInstance().getReference("NewProducts").child(timestamp)
        val myTask= dbRef.removeValue()
        myTask.addOnFailureListener(){
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
        }
        myTask.addOnSuccessListener{
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNGODisplay() {
        val requestBtn= binding.requestBtn
        requestBtn.visibility= View.VISIBLE
    }

    private fun showCompanyDisplay() {
        val updateBtn= binding.updateBtn
        val deleteBtn= binding.delBtn
        val layoutForBtns= binding.layoutForBtns
        updateBtn.visibility= View.VISIBLE
        deleteBtn.visibility= View.VISIBLE
        layoutForBtns.visibility= View.VISIBLE
    }

    private fun openUpdateDialog(timestamp: String, product:ProductTable){
        val updateDialogBox= AlertDialog.Builder(this)
        val inflater= layoutInflater
        val dialogBoxView= inflater.inflate(R.layout.update_menu, null)
        val db = DBHandler.getDatabase(this)



        updateDialogBox.setView(dialogBoxView)
        val updateProductName=dialogBoxView.findViewById<EditText>(R.id.updateProductName)
        val updateProductDate=dialogBoxView.findViewById<EditText>(R.id.updateProductDate)
        val updateProductCompany=dialogBoxView.findViewById<EditText>(R.id.updateProductCompany)
        val updateProductCategory=dialogBoxView.findViewById<EditText>(R.id.updateProductCategory)
        val updateProductCondition=dialogBoxView.findViewById<EditText>(R.id.updateProductCondition)
        val updateProductLocation=dialogBoxView.findViewById<EditText>(R.id.updateProductLocation)
        val updateProductQuantity=dialogBoxView.findViewById<EditText>(R.id.updateProductQuantity)
        val newUpdateBtn=dialogBoxView.findViewById<Button>(R.id.updateButton)


        val alertDialog = updateDialogBox.create()
        alertDialog.show()

        val fetchedProduct= db.productDAO().getDetailsByTimestamp(timestamp)

        val updateProductId= fetchedProduct?.rooom_productId?: 0
        updateProductName.setText(fetchedProduct?.room_productName ?: "")
        updateProductDate.setText(fetchedProduct?.room_date ?: "")
        updateProductCompany.setText(fetchedProduct?.room_companyOfTheProduct ?: "")
        updateProductCategory.setText(fetchedProduct?.room_category ?: "")
        updateProductCondition.setText(fetchedProduct?.room_condition ?: "")
        updateProductLocation.setText(fetchedProduct?.room_location ?: "")
        updateProductQuantity.setText((fetchedProduct?.room_quantity ?: 0).toString())

        calendar = Calendar.getInstance()

        updateProductDate.setOnClickListener {
            showDatePicker(updateProductDate)
        }

        newUpdateBtn.setOnClickListener{
            if(!updateProductQuantity.text.isNullOrBlank()) {
                newproduct = ProductTable(
                    updateProductId,
                    updateProductName.text.toString(),
                    updateProductCategory.text.toString(),
                    updateProductCompany.text.toString(),
                    updateProductQuantity.text.toString().toInt(),
                    updateProductDate.text.toString(),
                    updateProductCondition.text.toString(),
                    updateProductLocation.text.toString(),
                    false,
                    timestamp
                )
            }
            db.productDAO().updateProductDetails(newproduct)


            val dbRef= FirebaseDatabase.getInstance().getReference("NewProducts")

            lifecycleScope.launch {
                val firebaseProduct = toFirebaseModel(newproduct)
                val firebaseKey = firebaseProduct.row_timestamp
                if (firebaseKey.isNullOrBlank()) {
                    Log.e("FirebaseUpdate", "Missing timestamp. Cannot update product in Firebase.")
                    Toast.makeText(applicationContext, "Update failed: Missing timestamp.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                dbRef.child(firebaseKey).setValue(firebaseProduct)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Update product as synced in Room DB
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    newproduct.isSynced = true
                                    db.productDAO().updateProductDetails(newproduct)
                                }
                            }
                        } else {
                            Log.e("FirebaseSync", "Sync failed: ${task.exception}")
                            Toast.makeText(applicationContext, "Update failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                        }
                    }


                Toast.makeText(applicationContext, "Product Data Updated", Toast.LENGTH_SHORT)
                    .show()
            }

            alertDialog.dismiss()

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

}