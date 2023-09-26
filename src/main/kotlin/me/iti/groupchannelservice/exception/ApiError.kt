package me.iti.groupchannelservice.exception

import com.fasterxml.jackson.annotation.JsonProperty
import java.text.MessageFormat

class ApiError(
    @JsonProperty("errorCode")
    var code: String,
    @JsonProperty("error")
    var message: String?,
) {

    constructor(error: Errors, parameter: String) : this(
        error.name,
        MessageFormat.format(error.message, parameter)
    )
}