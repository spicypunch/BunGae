package com.example.bungae.data

// 테스트용
data class ItemData(
    var uid: String = "",
    var title: String = "",
    var content: String = "",
    var address: String = "",
    var category: String = "",
    var date: String = "",
    var imageUrl: String = ""
) : java.io.Serializable