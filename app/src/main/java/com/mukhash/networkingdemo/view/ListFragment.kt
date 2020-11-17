package com.mukhash.networkingdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mukhash.networkingdemo.R
import com.mukhash.networkingdemo.viewmodel.ListViewVM
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private val viewModel: ListViewVM by viewModels()
    private val listAdapter = AnimalListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animalList = view.findViewById<RecyclerView>(R.id.animalList)
        val progressBar = view.findViewById<ProgressBar>(R.id.listProgressBar)
        val errorText = view.findViewById<TextView>(R.id.listError)
        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)

        viewModel.animals.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                animalList.visibility = View.VISIBLE
                listAdapter.updateList(it)
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                errorText.visibility = View.GONE
                animalList.visibility = View.GONE
            }
        })
        viewModel.loadError.observe(viewLifecycleOwner, Observer {
            errorText.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                animalList.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        })

        viewModel.refresh()

        animalList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            listError.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

    }

}