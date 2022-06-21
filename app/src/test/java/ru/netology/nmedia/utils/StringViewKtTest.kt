package ru.netology.nmedia.utils

import junit.framework.TestCase
import org.junit.Test

class StringViewKtTest : TestCase(){

    @Test
    fun test1()
    {
        val res = changeCountString(999, 1)
        assertEquals("1K", res)
    }

    @Test
    fun test2()
    {
        val res = changeCountString(1000, -1)
        assertEquals("999", res)
    }

    @Test
    fun test3()
    {
        val res = changeCountString(1999, 1)
        assertEquals("2K", res)
    }

    @Test
    fun test4()
    {
        val res = changeCountString(1099, 1)
        assertEquals("1.1K", res)
    }

    @Test
    fun test5()
    {
        val res = changeCountString(9999, 1)
        assertEquals("10K", res)
    }

    @Test
    fun test6()
    {
        val res = changeCountString(10000, -1)
        assertEquals("9.9K", res)
    }

    @Test
    fun test7()
    {
        val res = changeCountString(10120, -1)
        assertEquals("10K", res)
    }

    @Test
    fun test8()
    {
        val res = changeCountString(11120, -1)
        assertEquals("11K", res)
    }

    @Test
    fun test9()
    {
        val res = changeCountString(19999, 1)
        assertEquals("20K", res)
    }

    @Test
    fun test10()
    {
        val res = changeCountString(1000000, -1)
        assertEquals("999K", res)
    }

    @Test
    fun test11()
    {
        val res = changeCountString(1100000, 1)
        assertEquals("1.1M", res)
    }

    @Test
    fun test12()
    {
        val res = changeCountString(1000000, 1)
        assertEquals("1M", res)
    }
}