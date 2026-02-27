package com.example.fuellog.interfaces

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/18
 */

interface AdapterActionListenerNew<T> {
    fun openItem(item: T)
    fun openItemBottomSheetDialog(item: T)
}