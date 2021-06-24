package com.example.belajar2.MenuMain2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.belajar2.R
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment(), View.OnClickListener {

    lateinit var judul: TextView
    lateinit var deskripsi: TextView
    lateinit var Profile: Button
    lateinit var ShowDialog: Button
    var description: String? = null

    companion object {
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        judul = view.findViewById(R.id.Judul)
        deskripsi = view.findViewById(R.id.Deskripsi)
        Profile = view.findViewById(R.id.Profile)
        Profile.setOnClickListener(this)
        ShowDialog = view.findViewById(R.id.Isi_Dialog)
        ShowDialog.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            val FromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
            description = FromBundle
        }

        if (arguments != null) {
            val jenisJudul = arguments?.getString(EXTRA_NAME)
            Judul.text = jenisJudul
            Deskripsi.text = description
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.Profile -> {
                val mIntent = Intent(activity, ProfileActivity::class.java)
                startActivity(mIntent)

            }
            R.id.Isi_Dialog -> {
                val mOptionDialogFragment = SettingDialogFragment()

                val mFragmentManager = childFragmentManager
                mOptionDialogFragment.show(mFragmentManager, SettingDialogFragment::class.java.simpleName)

            }

        }
    }
    internal var optionDialogListener: SettingDialogFragment.OnOptionDialogListener = object : SettingDialogFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
        }
    }

}
