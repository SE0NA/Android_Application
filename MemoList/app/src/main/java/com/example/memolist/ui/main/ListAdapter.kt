package com.example.memolist.ui.main

import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.OnListClick
import com.example.memolist.R
import com.example.memolist.db.ListItem

class ListAdapter(private val listLayout: Int, val listener:OnListClick): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var listList: List<ListItem>? = null
    private val mycallback: OnListClick = listener
    var holderlist: ArrayList<ViewHolder>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listList.let{
            val thisitem: ListItem = it!![position]
            holderlist?.add(holder)
            holder.checkbox.isChecked = it!![position].onoff
            if(it!![position].onoff) {
                holder.listViewGroup.setBackgroundResource(R.drawable.round_list_checked)
                holder.editText.setTextColor(R.color.gray_600.toInt())
            }
            else{
                holder.listViewGroup.setBackgroundResource(R.drawable.round_list_nochecked)
                holder.editText.setTextColor(R.color.sky3.toInt())
            }
            holder.editText.setText(it!![position].text)

            holder.checkbox.setOnClickListener {    //  체크 박스 클릭
                val updatelist = ListItem(thisitem.titleid, if(holder.editText.text.isNotEmpty())holder.editText.text.toString() else thisitem.text, holder.checkbox.isChecked)
                updatelist.id = thisitem.id
                mycallback.onClick(updatelist)
                if(updatelist.onoff) {
                    holder.listViewGroup.setBackgroundResource(R.drawable.round_list_checked)
                    holder.editText.setTextColor(R.color.gray_600.toInt())
                }
                else
                    holder.listViewGroup.setBackgroundResource(R.drawable.round_list_nochecked)
                holder.editText.setTextColor(R.color.sky3.toInt())
            }

            holder.editText.setOnFocusChangeListener { view, b ->
                if(b) {
                    holder.editText.setSelection(holder.editText.length())
                    holder.updateBtn.visibility = View.VISIBLE
                    mycallback.setSwipeAll()
                    Log.i("MYTAG", "editText focus")
                }
                else{
                    holder.updateBtn.visibility = View.GONE
                }
            }
            holder.editText.addTextChangedListener{
                if(holder.editText.text.length > 100){
                    holder.editText.text.substring(0, holder.editText.length() -1)
                    holder.editText.setSelection(holder.editText.length())
                    mycallback.setSwipeAll()
                }
            }

            holder.updateBtn.setOnClickListener {
                if(holder.editText.text.isNotEmpty()){
                    val updatelist = ListItem(thisitem.titleid, holder.editText.text.toString(), holder.checkbox.isChecked)
                    updatelist.id = thisitem.id
                    mycallback.onClick(updatelist)
                    holder.editText.clearFocus()
                    holder.itemView.requestFocus()
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