package com.example.recyclerviewconcepts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val list: List<DataClass>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {    //creates the view-holder and store our views in it
        val inflater = LayoutInflater.from(parent.context)                       //LayoutInflater - takes your layout xml file and converts it into object
        val view = inflater.inflate(R.layout.item_view,parent,false)
        return  MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {        //binding our custom data to our views is RecyclerView
        holder.title.text = list[position].title
        holder.description.text = list[position].description
    }

    override fun getItemCount(): Int {              //gives the item count in our RecyclerView
       return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){     //Nested class      //store the views
        var title = itemView.findViewById<TextView>(R.id.textViewTitle)
        var description = itemView.findViewById<TextView>(R.id.textViewDescription)
    }
}

