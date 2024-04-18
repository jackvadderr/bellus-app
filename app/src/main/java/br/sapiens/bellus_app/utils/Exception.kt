package br.sapiens.bellus_app.utils

class CommonException(message: String = "Something went wrong") : Exception(message)

class UserNotFoundException(message: String) : Exception(message)

class ChatNotFoundException(message: String) : Exception(message)