package com.example.belajar2.MenuMain2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.belajar2.MenuMain3.Main3Activity
import com.example.belajar2.R
import kotlinx.android.synthetic.main.fragment_kategori.*


class KategoriFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_kategori, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Button2:Button = view.findViewById(R.id.Button2)
        Button2.setOnClickListener(this)

        val Button3:Button = view.findViewById(R.id.Button3)
        Button3.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.Button2) {
            val mButton2 = DetailFragment()
            val mBundle = Bundle()
            mBundle.putString(DetailFragment.EXTRA_NAME, "Percobaan 1")
            val descrition = "Kategori Ini Berisi Percobaan - percobaan"

            mButton2.arguments = mBundle
            mButton2.description = descrition

            val mFragmentManager = fragmentManager
            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mButton2, Button2::class.java.simpleName)
                addToBackStack(null)
                commit()
            }

        }
        when (v.id) {
            R.id.Button3 -> {
                val mIntent = Intent(activity, Main3Activity::class.java)
                startActivity(mIntent)
            }

        }
    }

}
