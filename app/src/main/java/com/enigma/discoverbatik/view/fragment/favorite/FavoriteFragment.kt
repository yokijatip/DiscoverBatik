package com.enigma.discoverbatik.view.fragment.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.view.fragment.home.HomeFragment


class FavoriteFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(): FavoriteFragment {
            val fragment = FavoriteFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}