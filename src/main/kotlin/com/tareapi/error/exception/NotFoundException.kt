package com.tareapi.error.exception

class NotFoundException(message: String) : Exception("Not authorized exception (401). $message") {
}