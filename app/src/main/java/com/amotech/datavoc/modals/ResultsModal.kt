package com.amotech.datavoc.modals

data class ResultsModal(
    val data: List<Result>
)

data class Result(
    val date : String,
    val time : String,
    val sensor : String,
    val detection : String,
    val status : Int,
    val recommendation : String
)