package com.example.memolist.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.R
import com.example.memolist.SubActivity
import com.example.memolist.db.TitleItem

class TitleAdapter(private val titleLayout: Int): RecyclerView.Adapter<TitleAdapter.ViewHolder>() {

    private var titleList: List<TitleItem>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        titleList.let{
            holder.titleid.text = it!![position].id.toString()
            holder.titleText.text = it!![position].title
            holder.group.setOnClickListener {   // 클릭시 세부 list Activity 이동
                val intent = Intent(holder.itemView?.context, SubActivity::class.java)
                intent.putExtra("titleId", holder.titleid.text.toString().toInt())
                intent.putExtra("title", holder.titleText.text.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            titleLayout, parent, false)
        return ViewHolder(view)
    }

    fun setTitleList(titles: List<TitleItem>){
        titleList = titles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(titleList == null) 0 else titleList!!.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var titleid: TextView = itemView.findViewById(R.id.titleId)
        var titleText: TextView = itemView.findViewById(R.id.titleText)
        var group: LinearLayout = itemView.findViewById(R.id.titleViewGroup)
    }
}