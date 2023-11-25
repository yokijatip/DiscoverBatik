package com.enigma.discoverbatik.view.fragment.explore

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.enigma.discoverbatik.R


class ExploreFragment : Fragment(), View.OnClickListener {

    private lateinit var editText: EditText
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.search_bar)
        textView = view.findViewById(R.id.hello_world)

        editText.setOnKeyListener { _, keyCode, event ->

            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                handleTextSubmission()
                true
            } else {
                false
            }

        }

    }

    private fun handleTextSubmission() {
        val enteredText = editText.text.toString()
        textView.text = enteredText
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