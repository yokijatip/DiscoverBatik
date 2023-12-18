package com.enigma.discoverbatik.view.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.models.adapter.explore.ExploreAdapter


class ExploreFragment : Fragment(), View.OnClickListener {

    //    private lateinit var editText: EditText
//    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var exploreViewModel: ExploreViewModel
    private lateinit var adapter: ExploreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        editText = view.findViewById(R.id.search_bar)
//        editText.setOnKeyListener { _, keyCode, event ->
//            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                handleTextSubmission()
//                true
//            } else {
//                false
//            }
//        }

        val repository = Injection.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)
        exploreViewModel = ViewModelProvider(this, factory)[ExploreViewModel::class.java]

        recyclerView = view.findViewById(R.id.rv_explore)
        adapter = ExploreAdapter()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        observer()
        exploreViewModel.getStudiItem()

    }

//    private fun handleTextSubmission() {
//        val enteredText = editText.text.toString()
//        textView.text = enteredText
//    }

    private fun observer() {
        exploreViewModel.dataItems.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(): ExploreFragment {
            val fragment = ExploreFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}