package com.example.belajar2.MenuMain2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.belajar2.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button:Button = view.findViewById(R.id.Button1)
        button.setOnClickListener(this)

        setMyButtonEnable()

        my_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        my_button.setOnClickListener { Toast.makeText(context, my_edit_text.text, Toast.LENGTH_SHORT).show() }
    }

    private fun setMyButtonEnable() {
        val result = my_edit_text.text
        my_button.isEnabled = result != null && result.toString().isNotEmpty()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.Button1) {
            val mKategoriFragment = KategoriFragment()
            val mFragmentManager = fragmentManager
            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mKategoriFragment, KategoriFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

    }
}
