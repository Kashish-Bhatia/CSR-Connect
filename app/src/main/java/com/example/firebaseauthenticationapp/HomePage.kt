package com.example.firebaseauthenticationapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.firebaseauthenticationapp.adapters.CompanyAdapter
import com.example.firebaseauthenticationapp.adapters.ProductAdapter
import com.example.firebaseauthenticationapp.adapters.ViewPagerAdapter
import com.example.firebaseauthenticationapp.classes.Company
import com.example.firebaseauthenticationapp.database_Firebase.ProductTableModel
import com.example.firebaseauthenticationapp.database_RoomDB.DBHandler
import com.example.firebaseauthenticationapp.database_RoomDB.ProductTable
import com.example.firebaseauthenticationapp.databinding.ActivityHomePageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.relex.circleindicator.CircleIndicator3

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var newCompanyArrayList: ArrayList<Company>
    private lateinit var newCharityArrayList: ArrayList<Int>
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productArrayLIst: List<ProductTable>
    private lateinit var firebaseKiProductArrayLIst: ArrayList<ProductTableModel>
    private lateinit var productAdapter: ProductAdapter
    private lateinit var dbRef: DatabaseReference
    private lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        companyRecyclerView= binding.companyRecyclerView
        productArrayLIst= arrayListOf<ProductTable>()
        var db=DBHandler.getDatabase(this)

        //<----Progress Bar------>//////////////////////////////
        progressBar=binding.progressBar


        //<------Company Recycler View-----> //////////////////////////////////////////////////////////////////////////////////////////////////////

        val newComapanyImgArray= arrayOf(
            R.drawable.logo_hindustan,
            R.drawable.logo_parle,
            R.drawable.logo_png,
            R.drawable.logo_nestle,
            R.drawable.logo_cocacola,
            R.drawable.logo_britannia,
            R.drawable.logo_pepsico
        )
        val newCompanyName= arrayOf("Hindustan Unilever Limited", "Parle Products Pvt. Ltd.",
        "Procter & Gamble", "Nestlé India", "Coca-Cola India", "Britannia Industries", "PepsiCo India")


        //recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        companyRecyclerView.layoutManager = layoutManager

        newCompanyArrayList= arrayListOf<Company>()
        for (index in newCompanyName.indices){
            val companyKiDetail= Company(newComapanyImgArray[index],newCompanyName[index] )
            newCompanyArrayList.add(companyKiDetail)
        }
        companyRecyclerView.adapter=CompanyAdapter(newCompanyArrayList)

//        <-----HOME PAGE KA VIEW PAGER 2 KA CODE ------------->//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        newCharityArrayList= arrayListOf( R.drawable.viewpager_11,
            R.drawable.viewpager_1,
            R.drawable.viewpager_6, R.drawable.viewpager_2, R.drawable.viewpager_5,
            R.drawable.viewpager_3, R.drawable.viewpager_4, R.drawable.viewpager_30,R.drawable.viewpager_01111,
            R.drawable.viewpager_12 )

        val viewPagerTwo= binding.viewPager2

        viewPagerTwo.adapter= ViewPagerAdapter(newCharityArrayList)
        viewPagerTwo.orientation= ViewPager2.ORIENTATION_HORIZONTAL

        val indicator=findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(binding.viewPager2)


//        <-----HOME PAGE KA PRODUCT RECYCLER VIEW KA CODE ------------->////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        productRecyclerView=binding.productKaRecyclerView
        productRecyclerView.layoutManager=LinearLayoutManager(this)
//        val product1= Product("Nestle Lemon Ice Tea", "Ice Tea", "Nestle", 200, "28 July 2025", "The iced tea is unopened, well-preserved, and stored under proper conditions. All units are in good condition and ready for distribution.", "Choladi, Tamil Nadu")
//        val product2= Product("Dabur Herbal Paste", "Toothpaste", "Dabur", 150, "16 August 2025", "Unopened, well-sealed, and in excellent condition, stored under recommended conditions and ready for use.", "Baddi, Himachal Pradesh")
//        val product3= Product("Dettol Antiseptic Liquid", "Medicine, Antiseptic Liquid", "Dettol", 235, "18 October 2025", "The antiseptic liquid is unopened, tightly sealed, and in excellent condition, stored as per manufacturer guidelines.", "Mysore, Karnataka")
//        val product4= Product("Head & Shoulders 2‑in‑1", "Shampoo, Conditioner", "Head&Shoulders", 70, "20 September 2025", "The shampoo+conditioner is unopened, well-sealed, stored under recommended conditions, and in excellent condition ready for use.", "Baddi, Himachal Pradesh")
//        val product5= Product("Nestle Lemon Ice Tea", "Ice Tea", "Nestle", 200, "28 July 2025", "The iced tea is unopened, well-preserved, and stored under proper conditions. All units are in good condition and ready for distribution.", "Choladi, Tamil Nadu")
//        val product6= Product("Nestle Lemon Ice Tea", "Ice Tea", "Nestle", 200, "28 July 2025", "The iced tea is unopened, well-preserved, and stored under proper conditions. All units are in good condition and ready for distribution.", "Choladi, Tamil Nadu")
//        val product7= Product("Nestle Lemon Ice Tea", "Ice Tea", "Nestle", 200, "28 July 2025", "The iced tea is unopened, well-preserved, and stored under proper conditions. All units are in good condition and ready for distribution.", "Choladi, Tamil Nadu")
        progressBar.visibility= View.VISIBLE
//        var listOfProduct= showNow()

//        productArrayLIst= arrayListOf<Product>(
//            product4, product1, product2, product3, product5, product6, product7
//        )
//        productAdapter=ProductAdapter(productArrayLIst)
//        productRecyclerView.adapter=productAdapter


        //<-----------Button--------------------->/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val addBtn=binding.addButton
        addBtn.setOnClickListener{
            val intent= Intent(this, CompanyInsertProduct::class.java)
            startActivity(intent)
        }


        //<------------------SearchBar-------------->////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val searchView=binding.searchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true

            }

        })


        //<------------Company or NGO----->/////////////////////////////////////////////////////////////////////////////////////
        val intentFromNgoOrCompany= intent
        val userType= intentFromNgoOrCompany.getStringExtra("User")
        syncingNow(showNow(userType.toString()))
        showNow(userType.toString())

        if(userType=="Company"){
            showCompanyDisplay()
            showNow(userType)
            progressBar.visibility= View.GONE


        }
        if(userType=="NGO"){
            showNGODisplay()
            showNow(userType)
            progressBar.visibility= View.GONE


        }

        //<------Sync now code---->/////////////////////////////////////////////////////////////////////////////////////////////////////
        val syncTV= binding.syncTV

        syncTV.setOnClickListener{
            progressBar.visibility= View.VISIBLE
            syncingNow(showNow(userType.toString()))
            showNow(userType.toString())
            progressBar.visibility= View.GONE

        }







    }


    private fun syncingNow(localDbKiCurrentProductList: List<ProductTable>){

        ////<--ROOM SE FIREBASE--->/////////////////////////////////////////////////////////////////////////////////
        val db = DBHandler.getDatabase(this)
        val timestamp = System.currentTimeMillis().toString()
        val allProductList:List<ProductTable> = db.productDAO().getAllDetails()
        for(index in allProductList.indices){
            val one_product= allProductList[index]
            if (!one_product.isSynced){
                //ab firebase ko lao
                dbRef = FirebaseDatabase.getInstance().getReference("NewProducts")
             //   val key = dbRef.push().key
//                key?.let {
                dbRef.child(timestamp).setValue(one_product).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Update product as synced in Room DB
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                   // val updatedProduct = one_product.copy(isSynced = true)
                                    one_product.isSynced = true
                                    db.productDAO().updateProductDetails(one_product)
                                }
                            }
                        }
                        else {
                            Log.e("FirebaseSync", "Sync failed at Home: ${task.exception}")
                        }
                    }
                //}

            }
        }
        //<--FIREBASE SE ROOM-->////////////////////////////////////////////////////////////////////////////////////

        dbRef= FirebaseDatabase.getInstance().getReference("NewProducts")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                firebaseKiProductArrayLIst= arrayListOf<ProductTableModel>()
                firebaseKiProductArrayLIst.clear()
                if(snapshot.exists()) {
                    for (productSnap in snapshot.children) {
                        val productData = productSnap.getValue(ProductTableModel::class.java)
                        if (productData != null) {
                            firebaseKiProductArrayLIst.add(productData)
                        }
                    }
                }
                val localProductTimestamps = localDbKiCurrentProductList.map { it.room_timestamp }.toSet()

                for (firebaseProduct in firebaseKiProductArrayLIst) {
                    if (firebaseProduct.row_timestamp !in localProductTimestamps) {
                        val productToInsert = toRoomClass(firebaseProduct)
                        db.productDAO().insertProduct(productToInsert)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun showNow(userType: String): List<ProductTable> {
        var db = DBHandler.getDatabase(this)
        productArrayLIst = db.productDAO().getAllDetails()
        for (index in productArrayLIst.indices) {
            val id = productArrayLIst[index].rooom_productId
            val name = productArrayLIst[index].room_productName
            val cat = productArrayLIst[index].room_category
            val cmp = productArrayLIst[index].room_companyOfTheProduct
            val qty = productArrayLIst[index].room_quantity
            val date = productArrayLIst[index].room_date
            val condition = productArrayLIst[index].room_condition
            val location = productArrayLIst[index].room_location
            val timestamp = productArrayLIst[index].room_timestamp
            val product = ProductTable(id, name, cat, cmp, qty, date, condition, location, room_timestamp = timestamp)

        }
        //productRecyclerView.adapter = ProductAdapter(productArrayLIst)
        productAdapter=ProductAdapter(productArrayLIst)
        productRecyclerView.adapter=productAdapter
        productAdapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val currentItem = productArrayLIst[position]
                val intent= Intent(applicationContext, ProductDetailPageActivity::class.java)
                intent.putExtra("productDetails", currentItem)
                intent.putExtra("User",userType)

                startActivity(intent)
            }

        })

        return productArrayLIst



    }

    //<----------Different Displays---------------->//////////////////////////////////////////////////////////////////////////////
      private fun showCompanyDisplay(){
        binding.addButton.visibility=View.VISIBLE

    }

    private fun showNGODisplay(){
        binding.addButton.visibility=View.GONE
    }

    private fun filterList(query: String?) {
        if (query!=null){
            val filteredList= ArrayList<ProductTable>()

            for (index in productArrayLIst.indices) {
                val product= productArrayLIst[index]
                val name = productArrayLIst[index].room_productName
                val category = productArrayLIst[index].room_category

                if (name!!.contains(query, ignoreCase = true) || category!!.contains(query, ignoreCase = true)) {
                    filteredList.add(product) // or category, or both
                }
            }

            if (filteredList.isEmpty()){
                Toast.makeText(this, "No Product Found", Toast.LENGTH_SHORT).show()
            }
            else{
                productAdapter.setFilteredList(filteredList)
            }

        }

    }

////<----COnverting dataClass to ModelClass----->///////////////////////////////////////////////////////////////////////////////////////////////
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
