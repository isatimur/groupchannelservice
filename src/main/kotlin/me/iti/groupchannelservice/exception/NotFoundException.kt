package me.iti.groupchannelservice.exception

class NotFoundException(error: Errors, vararg parameters: Any?) : CustomException(error, *parameters)