package me.iti.groupchannelservice.exception

import java.text.MessageFormat

open class CustomException(val error: ApiError) : RuntimeException() {
    constructor(error: Errors, vararg parameters: Any?) : this(
        ApiError(
            error.name,
            MessageFormat.format(error.message, *parameters.map { it.toString() }.toTypedArray())
        )
    )
}