package com.tareapi.error.exception


class AlreadyExistException(message: String) : Exception("Not authorized exception (401). $message") {
}