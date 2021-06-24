package com.example.belajar2

import com.example.belajar2.Kalkulator.CuboidModel
import com.example.belajar2.Kalkulator.MainViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.*

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidModel: CuboidModel

    private val dummyLebar = 12.0
    private val dummyPanjang = 7.0
    private val dummyTinggi = 6.0

    private val dummyVolume = 504.0
    private val dummyKeliling = 100.0
    private val dummyLuasArea = 396.0

    @Before
    fun before() {
        cuboidModel = mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(cuboidModel)
    }

    @Test
    fun testVolume() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyLebar, dummyPanjang, dummyTinggi)
        val volume = mainViewModel.getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testKeliling() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyLebar, dummyPanjang, dummyTinggi)
        val volume = mainViewModel.getKeliling()
        assertEquals(dummyKeliling, volume, 0.0001)
    }

    @Test
    fun testLuasArea() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyLebar, dummyPanjang, dummyTinggi)
        val volume = mainViewModel.getLuasArea()
        assertEquals(dummyLuasArea, volume, 0.0001)
    }

    @Test
    fun testMockVolume() {
        `when`(mainViewModel.getVolume()).thenReturn(dummyVolume)
        val volume = mainViewModel.getVolume()
        verify(cuboidModel).getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testMockKeliling() {
        `when`(mainViewModel.getKeliling()).thenReturn(dummyKeliling)
        val volume = mainViewModel.getKeliling()
        verify(cuboidModel).getKeliling()
        assertEquals(dummyKeliling, volume, 0.0001)
    }

    @Test
    fun testMockLuasArea() {
        `when`(mainViewModel.getLuasArea()).thenReturn(dummyLuasArea)
        val volume = mainViewModel.getLuasArea()
        verify(cuboidModel).getLuasArea()
        assertEquals(dummyLuasArea, volume, 0.0001)
    }

    @Test
    fun getKeliling() {
    }

    @Test
    fun getLuasArea() {
    }

    @Test
    fun getVolume() {
    }

    @Test
    fun save() {
    }
}