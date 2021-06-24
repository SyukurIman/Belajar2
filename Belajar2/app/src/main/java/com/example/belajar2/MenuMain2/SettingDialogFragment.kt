package com.example.belajar2.MenuMain2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.example.belajar2.R

/**
 * A simple [Fragment] subclass.
 */
class SettingDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var close: Button
    private lateinit var choose: Button
    private lateinit var Setting: RadioGroup
    private lateinit var JawabanA: RadioButton
    private lateinit var JawabanB: RadioButton
    private lateinit var JawabanC: RadioButton
    private lateinit var JawabanD: RadioButton
    private var optionDialogListener: OnOptionDialogListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choose = view.findViewById(R.id.Choose)
        choose.setOnClickListener(this)
        close = view.findViewById(R.id.Close)
        close.setOnClickListener(this)
        Setting = view.findViewById(R.id.Setting)
        JawabanA = view.findViewById(R.id.JawabanA)
        JawabanB = view.findViewById(R.id.JawabanB)
        JawabanC = view.findViewById(R.id.JawabanC)
        JawabanD = view.findViewById(R.id.JawabanD)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragment = parentFragment

        if (fragment is DetailFragment) {
            val detailFragment = fragment
            this.optionDialogListener = detailFragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.Close -> dialog?.cancel()

            R.id.Choose -> {
                val checkedRadioButtonId = Setting.checkedRadioButtonId
                if (checkedRadioButtonId != -1) {
                    var pilih: String? = null
                    when (checkedRadioButtonId) {
                        R.id.JawabanA -> pilih = JawabanA.text.toString().trim()
                        R.id.JawabanB -> pilih = JawabanB.text.toString().trim()
                        R.id.JawabanC -> pilih = JawabanC.text.toString().trim()
                        R.id.JawabanD -> pilih = JawabanD.text.toString().trim()
                    }

                    if (optionDialogListener != null) {
                        optionDialogListener?.onOptionChosen(pilih)
                    }
                    dialog?.dismiss()
                }
            }
        }

    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }



}
