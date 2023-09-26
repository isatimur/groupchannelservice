package me.iti.groupchannelservice.exception

class EntityAlreadyExistsException(error: Errors, vararg parameters: Any?) : CustomException(error, *parameters)
