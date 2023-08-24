package com.example.bungae.data

data class ProfileData(
    var uid: String = "",
    var nickname: String = "",
    var age: Int = 0,
    var gender: Boolean = true,
) : java.io.Serializable
