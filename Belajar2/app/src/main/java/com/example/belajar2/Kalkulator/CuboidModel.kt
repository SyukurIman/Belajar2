package com.example.belajar2.Kalkulator

class CuboidModel {
    private var lebar: Double = 0.0
    private var panjang: Double = 0.0
    private var tinggi: Double = 0.0

    fun getVolume(): Double = lebar * panjang * tinggi

    fun getLuasArea(): Double {
        val lp = lebar * panjang
        val lt = lebar * tinggi
        val pt = panjang * tinggi

        return 2 * (lp + lt + pt)
    }

    fun getKeliling(): Double = 4 * (lebar + panjang + tinggi)

    fun save(lebar: Double, panjang: Double, tinggi: Double) {
        this.lebar = lebar
        this.panjang = panjang
        this.tinggi = tinggi
    }
}