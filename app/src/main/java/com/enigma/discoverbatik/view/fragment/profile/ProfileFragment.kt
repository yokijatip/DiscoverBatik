package com.enigma.discoverbatik.view.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.enigma.discoverbatik.R
import com.enigma.discoverbatik.data.local.preferences.TokenPreferences
import com.enigma.discoverbatik.data.local.preferences.dataStore
import com.enigma.discoverbatik.view.activity.landing.LandingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val curentUser = auth.currentUser

        if (curentUser != null) {
            val email = curentUser.email
            val userId = curentUser.uid

            val userReference: DatabaseReference = database.reference.child("users").child(userId)

            userReference.child("username").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = snapshot.value.toString()

                    view.findViewById<TextView>(R.id.textView7).text = username
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

            view.findViewById<TextView>(R.id.tv_user_email).text = email
        }

        val btnLogout = view.findViewById<Button>(R.id.btn_logout)

        btnLogout.setOnClickListener {
            lifecycleScope.launch {
                clearTokenDataStore()
                requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                requireActivity().finish()
                startActivity(Intent(requireActivity(), LandingActivity::class.java))

            }
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private suspend fun clearTokenDataStore() {
        val dataStore = TokenPreferences.getInstance(requireActivity().dataStore)
        return dataStore.clearToken()
    }

}