package com.enigma.discoverbatik.view.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.models.adapter.popular.PopularAdapter
import com.enigma.discoverbatik.view.activity.camera.CameraActivity
import com.enigma.discoverbatik.view.activity.camera.OpenCameraActivity
import com.enigma.discoverbatik.view.activity.cart.CartActivity


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Injection.provideRepository(requireActivity())
        val viewModelFactory = ViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

//        Setting Recycler View Popular Batik
        popularAdapter = PopularAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_popular_batik)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = popularAdapter

        observerViewModelPopularBatik()
        homeViewModel.fetchPopularItems()

        val btnCart = view.findViewById<ImageView>(R.id.btn_shopping_bag)
        btnCart.setOnClickListener {
            navigateToCartActivity()
        }

        val btnCamera = view.findViewById<Button>(R.id.btn_camera)
        btnCamera.setOnClickListener {
            navigateToCameraActivity()
        }

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    //    Move to Cart Activity
    private fun navigateToCartActivity() {
        val intent = Intent(this@HomeFragment.requireContext(), CartActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToCameraActivity() {
        val intent = Intent(this@HomeFragment.requireContext(), OpenCameraActivity::class.java)
        startActivity(intent)
    }

    private fun observerViewModelPopularBatik() {
        homeViewModel.popularItems.observe(viewLifecycleOwner) { popularItems ->
            popularItems?.listStory?.let {
                val limitedList = it.take(20)
                popularAdapter.setData(limitedList)
            }
        }
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