package com.example.belajar2.MenuMain3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.example.belajar2.R
import kotlinx.android.synthetic.main.fragment_home2.*


class Home2Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        return inflater.inflate(R.layout.fragment_home2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_category.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_home2Fragment_to_categoryFragment2)
        )

        btn_provile.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_home2Fragment_to_profileActivity2)
        }

}
}
