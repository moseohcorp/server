package moseohcorp.server._common.exception

open class ApiException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
