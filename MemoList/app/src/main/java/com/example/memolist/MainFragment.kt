package com.example.memolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.databinding.FragmentMainBinding
import com.example.memolist.ui.main.TitleAdapter
import com.example.memolist.ui.main.TitleModel

class MainFragment : Fragment() {
    private var adapter: TitleAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    val titleViewModel: TitleModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.titlelistView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL)) // 구분선
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observerSetup()
        recyclerSetup()
    }

    private fun observerSetup(){
        titleViewModel.getAllTitles()?.observe(viewLifecycleOwner, Observer { titles ->
            titles?.let{
                adapter?.setTitleList(it)
            }
        })
    }
    fun recyclerSetup(){
        adapter = TitleAdapter(R.layout.title_view)
        val recyclerView: RecyclerView? = view?.findViewById(R.id.titlelistView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }
}