package moseohcorp.server._common.utils

inline fun <T : Throwable> check(value: Boolean, exceptionSupplier: () -> T) {
    if (!value) {
        throw exceptionSupplier()
    }
}
