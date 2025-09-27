    package com.example.firebaseauthenticationapp.database_Firebase

    import com.google.firebase.database.PropertyName


    data class ProductTableModel(


        @get:PropertyName("rooom_productId")
        @set:PropertyName("rooom_productId")
        var row_productId: Int? = 0,

        @get:PropertyName("room_productName")
        @set:PropertyName("room_productName")
        var row_productName:String?=null,


        @get:PropertyName("room_category")
        @set:PropertyName("room_category")
        var row_category: String?=null,

        @get:PropertyName("room_companyOfTheProduct")
        @set:PropertyName("room_companyOfTheProduct")
        var row_companyOfTheProduct: String?=null,

        @get:PropertyName("room_quantity")
        @set:PropertyName("room_quantity")
        var row_quantity: Int?=0,

        @get:PropertyName("room_date")
        @set:PropertyName("room_date")
        var row_date: String?=null,

        @get:PropertyName("room_condition")
        @set:PropertyName("room_condition")
        var row_condition: String?=null,

        @get:PropertyName("room_location")
        @set:PropertyName("room_location")
        var row_location: String?=null,

        @get:PropertyName("isSynced")
        @set:PropertyName("isSynced")
        var isSynced: Boolean = false,

        @get:PropertyName("room_timestamp")
        @set:PropertyName("room_timestamp")
        var row_timestamp: String? = null
    )
