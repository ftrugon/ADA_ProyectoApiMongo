package com.tareapi.error.exception


class AlreadyExistException(message: String) : Exception("Conflict exception (409).$message") {
}