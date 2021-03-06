package com.example.recyclerviewconcepts

data class DataClass(val title : String, val description : String,val creationAt : Long){

    constructor() : this("","",System.currentTimeMillis()){

    }

}

