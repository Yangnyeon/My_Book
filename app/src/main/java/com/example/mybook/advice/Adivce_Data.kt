package com.example.mybook.advice

import com.google.gson.annotations.SerializedName

data class Adivce_Data (
    @SerializedName("slip") var slip:Slip?,
)

data class Slip (
    @SerializedName("id") var id:Int?,

    @SerializedName("advice") var advice:String?,
)