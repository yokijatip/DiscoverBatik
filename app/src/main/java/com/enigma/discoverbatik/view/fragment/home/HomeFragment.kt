package com.enigma.discoverbatik.view.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.activity.cart.CartActivity


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel

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

        val btnCart = view.findViewById<ImageView>(R.id.btn_shopping_bag)
        btnCart.setOnClickListener {
            navigateToCartActivity()
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

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}