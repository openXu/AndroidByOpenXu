package com.openxu.core.network

class ApiException(var code: Int, override var message: String) : RuntimeException()