package com.example.fuellog.interfaces

/**
 * @Author: naftalikomarovski
 * @Date: 2026/01/28
 */

interface AddUpdateListener<T> {
    fun add(item: T)
    fun update(item: T)
}