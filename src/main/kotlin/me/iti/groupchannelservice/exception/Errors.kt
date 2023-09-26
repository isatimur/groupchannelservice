package me.iti.groupchannelservice.exception

enum class Errors(val message: String) {
    INTERNAL_ERROR("Internal service error"),
    COULD_NOT_PARSE_REQUEST_PARAMETERS("Could not parse request parameters"),
    ENTITY_NOT_FOUND("Entity {0} not found"),
    REQUIRED_FIELD_MISSING("Required field {0} is missing"),
    SHOULD_BE_POSITIVE("{0} value should be positive"),
    WRONG_DECIMAL_FORMAT("{0} wrong decimal number format"),
    COULD_NOT_CHANGE_CURRENCY("{0}"),
    ENTITY_NOT_UNIQUE("{0} already exists"),
    ENTITY_ALREADY_EXISTS("Entity with id {0} is already a part of entity with id {1}"),
    COULD_NOT_SAVE_ENTITY("Could not save entity"),
    INSUFFICIENT_FUNDS("Transaction declined: insufficient funds")
}
