package com.example.recyclerviewconcepts

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter: MyAdapter
    lateinit var manager : LinearLayoutManager
    var isScrolling : Boolean = false
    lateinit var listOfLanguagesObjects : MutableList<DataClass>
    private val limit = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)

        listOfLanguagesObjects = mutableListOf<DataClass>()

        fetchDataFromFireStore(0)

        myAdapter = MyAdapter(listOfLanguagesObjects as ArrayList<DataClass>)
        listRecycler.adapter = myAdapter
        listRecycler.layoutManager = manager

        floatingBtnToGrid.setOnClickListener {
            addClickToGridButton()
        }

        searchFeature()

        //-------------------------------------------------------------------------------------------------------------------------------
        listRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) { //this method is called when we have done scrolling
                super.onScrolled(recyclerView, dx, dy)
                var currentVisibleItems : Int = manager.childCount              //total items visible on screen from recyclerview
                // val limit = 10
                var totalItems : Int = manager.itemCount                //total items from recyclerview
                 var firstVisibleIndex : Int = manager.findFirstVisibleItemPosition()  //items that have already been scrolled out

                val scrolledOutItems = currentVisibleItems + firstVisibleIndex
                if(isScrolling && ( scrolledOutItems == totalItems))
                {
                    isScrolling = false
                    fetchData()
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {   //this method is called when we scroll
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }
        })
        //-------------------------------------------------------------------------------------------------------------------------------
    }
    fun getDataClasses(startIndex : Long, limit : Int, callback : (List<DataClass>) -> Unit) {
        val emptyList = mutableListOf<DataClass>()
        Log.e("test","inside getDataClasses $startIndex $limit")

        FirebaseFirestore.getInstance().collection("DataClasses").orderBy("creationAt")
            .startAfter(startIndex).limit(limit.toLong()).get().addOnSuccessListener { snapshots ->
                Log.e("test","inside addOnSuccessListener")

                snapshots.forEach { snapshot ->
                    Log.e("test","inside forEach")
                    val dataClass = snapshot.toObject(DataClass::class.java)
                    emptyList.add(dataClass)
                }
                callback(emptyList)
            }.addOnFailureListener{
                Log.e("test","inside addOnFailureListener")
                it.printStackTrace()
            }

    }
    //-------------------------------------------------------------------------------------------------------------------------------
    private fun fetchData() {
        progressBar.visibility = View.VISIBLE
        Toast.makeText(this@MainActivity,"Next Notes Loaded!",Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {

                fetchDataFromFireStore(listOfLanguagesObjects.last().creationAt)

                myAdapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }
        }, 2500)
    }

    private fun fetchDataFromFireStore(creationAt: Long) {
        getDataClasses(creationAt,8) {
            myAdapter.updateData(it)
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------
    private fun addClickToGridButton(){
        var i : Int =0
        floatingBtnToGrid.setOnClickListener {
            if(i == 0) {
                // recyclerViewList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                listRecycler.layoutManager =
                    GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                Toast.makeText(this, "Switched to grid view!", Toast.LENGTH_SHORT).show();
                i++
            }
            else if (i == 1){
                listRecycler.layoutManager = LinearLayoutManager(this)
                Toast.makeText(this, "Switched to list view!", Toast.LENGTH_SHORT).show();
                i = 0
            }
        }
    }

    private fun searchFeature(){
        searchViewId.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchText: String): Boolean {
                return false
            }
            override fun onQueryTextChange(searchText: String): Boolean {
                myAdapter.filter.filter(searchText)
                return false
            }
        })
    }
}