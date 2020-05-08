package com.example.myapplication

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Color
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.apply {
            val editText =
                this.findViewById(androidx.appcompat.R.id.search_src_text) as EditText

            editText.clearFocus()

            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(QueryTextListener(this))
        }

        populate()
    }

    override fun onResume() {
        super.onResume()
        searchView.setQuery("", false)
        main_content.requestFocus()
    }

    inner class QueryTextListener(private val searchView: SearchView) :
        SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            searchView.clearFocus()
            searchView.setQuery("", false)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            (recyclerView.adapter as CatAdapter).filter.filter(newText)
            return false
        }

    }

    private fun populate() {
        val moviesList = arrayListOf<Data>()
        moviesList.add(Data("Iron Man"))
        moviesList.add(Data("The Incredible Hulk"))
        moviesList.add(Data("Iron Man 2"))
        moviesList.add(Data("Thor"))
        moviesList.add(Data("Captain America: The First Avenger"))
        moviesList.add(Data("The Avengers"))
        moviesList.add(Data("Iron Man 3"))
        moviesList.add(Data("Thor: The Dark World"))
        moviesList.add(Data("Captain America: The Winter Soldier"))
        moviesList.add(Data("Guardians of the Galaxy"))
        moviesList.add(Data("Avengers: Age of Ultron"))
        moviesList.add(Data("Ant-Man"))
        moviesList.add(Data("Captain America: Civil War"))
        moviesList.add(Data("Doctor Strange"))
        moviesList.add(Data("Guardians of the Galaxy Vol. 2"))
        moviesList.add(Data("Spider-Man: Homecoming"))
        moviesList.add(Data("Thor: Ragnarok"))
        moviesList.add(Data("Black Panther"))
        moviesList.add(Data("Avengers: Infinity War"))
        moviesList.add(Data("Ant-Man and the Wasp"))
        moviesList.add(Data("Captain Marvel"))
        moviesList.add(Data("Avengers: Endgame"))
        moviesList.add(Data("Spider-Man: Far From Home"))

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CatAdapter(moviesList)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.post { searchView.onActionViewExpanded() }
    }
}
