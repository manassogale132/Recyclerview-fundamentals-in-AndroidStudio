package com.example.recyclerviewconcepts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listOfLanguagesObjects = mutableListOf<DataClass>()
        listOfLanguagesObjects.add(DataClass("Java", "Java is one of the languages that is popular in large organizations thanks to it a number of use-cases and has long been the preferred go-to language for coding on Android. It is widely used in Android App Development."))
        listOfLanguagesObjects.add(DataClass("Kotlin", "Kotlin is designed to be completely interoperable with Java. This language offers features that developers ask for since the time Android announced it as its first language. Kotlin effortlessly combines object-oriented and functional programming features within it."))
        listOfLanguagesObjects.add(DataClass("JavaScript", "JavaScript is a core programming language for powering the World Wide Web. Its effectiveness in front- and back-end development, the ability to work well with other languages, versatility, and updated annually make it a widely used language."))
        listOfLanguagesObjects.add(DataClass("SQL", "SQL is a standard language for storing, manipulating and retrieving data in databases. All the Relational Database Management Systems (RDMS) like MySQL, MS Access, Oracle, Sybase, Informix, Postgres and SQL Server use SQL as their standard database language."))
        listOfLanguagesObjects.add(DataClass("CSS", "CSS is often used to style an HTML document. It is designed to enable the separation of presentation and content, including layout, colors, and fonts. This separation can improve content accessibility, provide more flexibility and control in the specification of presentation characteristics."))
        listOfLanguagesObjects.add(DataClass("Swift", "Swift can be friendly to new programmers. It is a safe, fast, and interactive programming language that integrates the best in modern language expertise with wisdom from the wider Apple engineering culture and the diverse contributions from its open-source community."))
        listOfLanguagesObjects.add(DataClass("C", "C is designed specifically for use with the Windows OS and is part of the .Net framework. It is widely used by competitive programmers owing to the fact that it is extremely fast and stable."))
        listOfLanguagesObjects.add(DataClass("C++", "C++ is a general-purpose programming language that can be used to develop operating systems, browsers, games, and more. It supports different ways of programming like procedural, object-oriented, functional, and so on. This makes C++ powerful as well as flexible."))
        listOfLanguagesObjects.add(DataClass("Python", "Python is considered one of the best programming languages that can be used for web and desktop applications, GUI-based desktop applications, machine learning, data science, and network servers."))
        listOfLanguagesObjects.add(DataClass("TypeScript", "TypeScript is an open-source language that builds on JavaScript by adding static type definitions. Types provide a way to define the shape of an object, providing better documentation, and allowing TypeScript to validate that a user’s code is working correctly."))
        listOfLanguagesObjects.add(DataClass("PowerShell", "PowerShell provides full access to COM and WMI, enabling administrators to perform administrative tasks on both local and remote Windows systems as well as WS-Management and CIM enabling management of remote Linux systems and network devices. It has a rich expression parser and a fully developed scripting language."))


        list.adapter = MyAdapter(listOfLanguagesObjects)
        list.layoutManager = LinearLayoutManager(this)


        floatingBtnToGrid.setOnClickListener {
            addClickToGridButton()
        }

    }
    private fun addClickToGridButton(){
        var i : Int =0
        floatingBtnToGrid.setOnClickListener {
            if(i == 0) {
                // recyclerViewList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                list.layoutManager =
                    GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                Toast.makeText(this, "Switched to grid view!", Toast.LENGTH_SHORT).show();
                i++
            }
            else if (i == 1){
                list.layoutManager = LinearLayoutManager(this)
                Toast.makeText(this, "Switched to list view!", Toast.LENGTH_SHORT).show();
                i = 0
            }
        }
    }
}