package com.example.memolist.ui.main

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.OnListClick
import com.example.memolist.R
import com.example.memolist.db.ListItem

class ListAdapter(private val listLayout: Int, val listener:OnListClick): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var listList: List<ListItem>? = null
    private val mycallback: OnListClick = listener

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listList.let{
            val thisitem: ListItem = it!![position]
            holder.checkbox.isChecked = it!![position].onoff
            holder.editText.setText(it!![position].text)
            holder.checkbox.setOnClickListener {    //  체크 박스 클릭
                val updatelist = ListItem(thisitem.titleid, if(holder.editText.text.isNotEmpty())holder.editText.text.toString() else thisitem.text, holder.checkbox.isChecked)
                updatelist.id = thisitem.id
                mycallback.onClick(updatelist)
            }
            holder.editText.setOnFocusChangeListener { view, b ->
                if(b) {
                    holder.updateBtn.visibility = View.VISIBLE
                    holder.editText.setSelection(holder.editText.length())
                }
            }
            holder.editText.addTextChangedListener{
                if(holder.editText.text.length > 100){
                    holder.editText.text.substring(0, holder.editText.length() -1)
                    holder.editText.setSelection(holder.editText.length())
                }
            }
            holder.updateBtn.setOnClickListener {
                if(holder.editText.text.isNotEmpty()){
                    val updatelist = ListItem(thisitem.titleid, holder.editText.text.toString(), holder.checkbox.isChecked)
                    updatelist.id = thisitem.id
                    mycallback.onClick(updatelist)
                    holder.editText.clearFocus()
                }
                else{
                    mycallback.deleteList(thisitem.id)
                }
                holder.updateBtn.visibility = View.GONE
            }
            holder.deleteBtn.setOnClickListener {   // 삭제 클릭
                mycallback.deleteList(thisitem.id)
                notifyItemRemoved(thisitem.id)
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
        var checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        var editText: EditText = itemView.findViewById(R.id.listTextEdit)
        var updateBtn: Button = itemView.findViewById(R.id.listupdateBtn)
        var deleteBtn: ImageView = itemView.findViewById(R.id.deletelist)
        var listViewGroup: LinearLayout = itemView.findViewById(R.id.listViewGroup)
    }
}