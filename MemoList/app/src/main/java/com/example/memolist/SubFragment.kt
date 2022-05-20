package com.example.memolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.memolist.ui.main.ListAdapter
import com.example.memolist.ui.main.ListModel

class SubFragment : Fragment() {
    private var adapter: ListAdapter? = null

    companion object{
        fun newInstance() = SubFragment()
    }

    val listViewModel: ListModel by viewModels()
    private
}