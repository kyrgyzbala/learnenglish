package com.english.onlineenglishteacher.ui.test.model

data class ModelQ(
    val question: String?,
    val varA: String?,
    val varB: String?,
    val varC: String?,
    val varD: String?,
    val answer: Int,
    var isAnswered: Int = -1
)