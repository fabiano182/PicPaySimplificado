package com.picPaySimplificado.exception

class AcceptButDenied(override val message: String, val errorCode: String) : Exception() {
}