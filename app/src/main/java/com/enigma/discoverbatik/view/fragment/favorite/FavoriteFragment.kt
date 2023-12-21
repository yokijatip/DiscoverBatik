package com.enigma.discoverbatik.view.fragment.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.data.remote.response.FavoriteItem
import com.enigma.discoverbatik.models.adapter.favorite.FavoriteAdapter
import com.enigma.discoverbatik.utils.CommonUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class FavoriteFragment : Fragment(), View.OnClickListener{

    private lateinit var favoriteManager: FavoriteManager
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteManager = FavoriteManager.getInstance()

//        Inisiasi recycler view
        recyclerView = view.findViewById(R.id.rv_favorite)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoriteAdapter = FavoriteAdapter(emptyList())
        recyclerView.adapter = favoriteAdapter

        val favoriteItemsDatabaseReference = favoriteManager.getFavoriteItems()
        favoriteItemsDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val favoriteItems = mutableListOf<FavoriteItem>()
                for (snapshot in dataSnapshot.children) {
                    val favoriteItem = snapshot.getValue(FavoriteItem::class.java)
                    favoriteItem?.let { favoriteItems.add(it) }
                }
                favoriteAdapter.updateData(favoriteItems)
            }

            override fun onCancelled(error: DatabaseError) {
                CommonUtils.alertError(requireContext(), "Sorry error😑")
            }

        })
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