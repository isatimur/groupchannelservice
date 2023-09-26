package me.iti.groupchannelservice.exception

class ValidationException(error: Errors, parameter: Any? = null) : CustomException(error, parameter)