package me.iti.groupchannelservice.exception

class NotUniqueException(error: Errors, vararg parameters: Any?) : CustomException(error, *parameters)
