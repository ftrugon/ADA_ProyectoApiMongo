package com.tareapi.domain

import com.tareapi.domain.Provincia

data class DatosProvincias(
    val update_date:String,
    val size:Int,
    val data: List<Provincia>
) {
}