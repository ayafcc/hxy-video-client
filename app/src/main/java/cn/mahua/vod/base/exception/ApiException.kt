package com.github.StormWyrm.wanandroid.base.exception

data class ApiException(val errorCode: Int, val errorMessage: String) : RuntimeException(errorMessage)