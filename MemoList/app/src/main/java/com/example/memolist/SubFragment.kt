package com.example.memolist

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.databinding.FragmentSubBinding
import com.example.memolist.db.ListItem
import com.example.memolist.ui.main.ListAdapter
import com.example.memolist.ui.main.ListModel

class SubFragment : Fragment(), OnListClick{
    var adapter: ListAdapter? = null

    private var titleid: Int = 0

    companion object{
        fun newInstance() = SubFragment()
    }

    val listViewModel: ListModel by viewModels()
    private var _binding: FragmentSubBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubBinding.inflate(inflater, container, false)
        titleid = arguments?.getInt("titleId")!!

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observerSetup()
        recyclerSetup()
    }

    private fun observerSetup(){
        listViewModel.getAllLists(titleid)?.observe(viewLifecycleOwner, Observer { lists ->
            lists?.let{
                adapter?.setListList((it))
            }
        })
    }
    fun recyclerSetup(){
        adapter = ListAdapter((R.layout.list_view), this)
        val recyclerView: RecyclerView? = view?.findViewById(R.id.listlistView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }

    override fun onClick(listItem: ListItem) {
        listViewModel.updateList(listItem)
        var imm: InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun deleteList(id: Int) {
        listViewModel.deleteList(id)
        recyclerSetup()
    }
}