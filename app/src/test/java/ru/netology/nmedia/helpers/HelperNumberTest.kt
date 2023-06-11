package ru.netology.nmedia.helpers

import org.junit.Test

import org.junit.Assert.*

class HelperNumberTest {

    @Test
    fun testSampleFormat() {
        val num = NumberHelper.intToShortString(10)
        assertEquals("10", num)
    }

    @Test
    fun testThousandFormat() {
        val num = NumberHelper.intToShortString(1_000)
        assertEquals("1K", num)
    }

    @Test
    fun testTenThousandFormat() {
        val num = NumberHelper.intToShortString(10_000)
        assertEquals("10K", num)
    }

    @Test
    fun testThousandOneHundredFormat() {
        val num = NumberHelper.intToShortString(1_100)
        assertEquals("1.1K", num)
    }

    @Test
    fun testTenThousandOneHundredFormat() {
        val num = NumberHelper.intToShortString(10_100)
        assertEquals("10K", num)
    }

    @Test
    fun testMillionFormat() {
        val num = NumberHelper.intToShortString(1_000_000)
        assertEquals("1M", num)
    }

    @Test
    fun testMillionThousandHundredFormat() {
        val num = NumberHelper.intToShortString(1_100_000)
        assertEquals("1.1M", num)
    }
}