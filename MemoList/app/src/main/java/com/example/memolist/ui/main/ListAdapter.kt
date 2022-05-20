package com.example.memolist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.R
import com.example.memolist.db.ListItem

class ListAdapter(private val listLayout: Int): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var listList: List<ListItem>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listList.let{
            holder.radio.text = it!![position].text
            holder.radio.isChecked = it!![position].onoff
            if(holder.radio.isChecked== true){
                holder.radio.setTextColor(R.color.gray_600.toInt())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            listLayout, parent, false)
        return ViewHolder(view)
    }

    fun setListList(lists: List<ListItem>){
        listList = lists
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(listList == null) 0 else listList!!.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var radio: RadioButton = itemView.findViewById(R.id.listRadio)
    }
}