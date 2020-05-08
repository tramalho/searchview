package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView

class CatAdapter(private val listCat: ArrayList<Data>) :
    RecyclerView.Adapter<CatAdapter.ViewHolder>(), Filterable {

    private val imutablelistCat: ArrayList<Data> = ArrayList(listCat)
    private val filter: Filter = FilterImp()


    override fun getFilter(): Filter {
        return filter
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val from = LayoutInflater.from(parent.context)

        val layoutId = when (getItemViewType(viewType)) {
            0 -> R.layout.item_row
            else -> R.layout.item_row2
        }

        val view: View = from.inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = HtmlCompat.fromHtml((listCat[position].text), FROM_HTML_MODE_LEGACY)
        holder.rowCountTextView.text = "$position"
    }

    override fun getItemCount(): Int = listCat.size

    override fun getItemViewType(position: Int): Int {
        return when(listCat[position].isSearch) {
            true -> 0
            false -> 1
        }
    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val textView: TextView = itemsView.findViewById(R.id.textView)
        val rowCountTextView: TextView = itemsView.findViewById(R.id.rowCountTextView)
    }

    class ViewHolder2(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val textView: TextView = itemsView.findViewById(R.id.textView)
        val rowCountTextView: TextView = itemsView.findViewById(R.id.rowCountTextView)
    }

    private inner class FilterImp : android.widget.Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {

            val filteredList = arrayListOf<Data>()

            when {
                constraint.isNullOrEmpty() -> filteredList.addAll(imutablelistCat)
                else -> filteredList.addAll(createFilterList(constraint))
            }

            val filterResults = FilterResults()
            filterResults.values = filteredList

            return filterResults
        }

        private fun createFilterList(constraint: CharSequence): ArrayList<Data> {

            val filteredList = arrayListOf<Data>()

            imutablelistCat.forEach {
                if (it.text.contains(constraint.toString(), true)) {
                    val text = it.text.replace(constraint.toString(), "<strong>$constraint</strong>")
                    filteredList.add(Data(text, true))
                }
            }

            return filteredList
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            listCat.clear()
            listCat.addAll(filterResults?.values as Collection<Data>)
            notifyDataSetChanged()
        }
    }
}