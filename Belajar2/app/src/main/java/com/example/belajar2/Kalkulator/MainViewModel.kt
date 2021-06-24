package com.example.belajar2.Kalkulator

import com.example.belajar2.Kalkulator.CuboidModel

class MainViewModel(private val cuboidModel: CuboidModel) {
    fun getKeliling(): Double = cuboidModel.getKeliling()

    fun getLuasArea(): Double = cuboidModel.getLuasArea()

    fun getVolume(): Double = cuboidModel.getVolume()

    fun save(l: Double, p: Double, t: Double) {
        cuboidModel.save(l, p, t)
    }
}