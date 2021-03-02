package com.openxu.libs.datasource.remote

class ApiException(var code: Int, override var message: String) : RuntimeException()