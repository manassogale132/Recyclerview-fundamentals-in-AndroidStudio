package com.example.recyclerviewconcepts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var list : ArrayList<DataClass>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {

    private var listTemp = ArrayList<DataClass>(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {    //creates the view-holder and store our views in it
        val inflater =
            LayoutInflater.from(parent.context)                       //LayoutInflater - takes your layout xml file and converts it into object
        val view = inflater.inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {        //binding our custom data to our views in RecyclerView
        holder.title.text = list[position].title
        holder.description.text = list[position].description
    }

    override fun getItemCount(): Int {              //gives the item count in our RecyclerView
        return list.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {     //Nested class      //store the views
        val title = itemView.findViewById<TextView>(R.id.textViewTitle)
        val description = itemView.findViewById<TextView>(R.id.textViewDescription)
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {     //automatically be executed in background thread
                val filteredList = ArrayList<DataClass>()                                  //constraint variable to define filter logic
                if (constraint == null || constraint.length == 0) {
                    filteredList.addAll(listTemp)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()    //filterPattern takes the input text from constraint
                    for (list : DataClass in listTemp) {
                        if (list.title.toLowerCase().contains(filterPattern)) {  //checking the title based on the filterpattern
                            filteredList.add(list)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {   //results are automatically publish to the ui theard
                list.clear()
                list.addAll(results?.values as Collection<DataClass>)
                notifyDataSetChanged()
            }
        }
    }
}
