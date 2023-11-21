package com.enigma.discoverbatik.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigma.discoverbatik.databinding.FragmentHomeBinding
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.models.adapter.popular.PopularAdapter
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Injection.provideRepository(requireActivity())
        val viewModelFactory = ViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        adapter = PopularAdapter()

        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.rvHome.layoutManager = layoutManager
        binding!!.rvHome.adapter = adapter
        lifecycleScope.launch {
            homeViewModel.getStory.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }


    }


    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}