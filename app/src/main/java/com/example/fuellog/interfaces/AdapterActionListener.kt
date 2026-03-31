package com.example.fuellog.interfaces

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/18
 */

interface AdapterActionListener {
    fun openItemIdInt(id: Long)
    fun openItemIdString(id: String)
    fun openItemIntBottomSheetDialog(id: Long)
    fun openItemStringBottomSheetDialog(id: String)
}