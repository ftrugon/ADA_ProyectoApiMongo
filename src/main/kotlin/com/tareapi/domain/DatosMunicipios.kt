package com.tareapi.domain

import com.tareapi.domain.Municipio

data class DatosMunicipios(
    val update_date:String,
    val size:Int,
    val data:List<Municipio>
) {
}