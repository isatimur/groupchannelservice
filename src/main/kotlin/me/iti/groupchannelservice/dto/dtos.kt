package me.iti.groupchannelservice.dto

import java.util.UUID

data class CreateChannelRequest(val name: String, val ownerId: UUID, val url: String)
data class CreateGroupRequest(val name: String)

data class AddParticipantRequest(val groupId: UUID, val userId: UUID)
data class SubscribeUserRequest(val channelId: UUID, val userId: UUID)
