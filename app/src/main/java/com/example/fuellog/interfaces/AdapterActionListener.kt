package com.example.fuellog.interfaces

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/18
 */

interface AdapterActionListener {
    fun openItemIdInt(id: Int)
    fun openItemIdString(id: String)
    fun openItemIntBottomSheetDialog(id: Int)
    fun openItemStringBottomSheetDialog(id: String)
}