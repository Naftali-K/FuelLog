package com.example.fuellog.models

/**
 * @Author: naftalikomarovski
 * @Date: 2025/12/02
 */

data class Transport(
    var id: Int,
    var name: String?,
    var company: String?,
    var model: String?,
    var year: Int?,
    var description: String?
) {

}