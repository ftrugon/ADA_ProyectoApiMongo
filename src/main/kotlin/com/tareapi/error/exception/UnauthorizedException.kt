package com.tareapi.error.exception

class UnauthorizedException(message: String) : Exception("Not authorized exception (401). $message") {
}