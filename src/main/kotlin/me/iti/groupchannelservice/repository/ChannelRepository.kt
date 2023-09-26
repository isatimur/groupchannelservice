package me.iti.groupchannelservice.repository

import me.iti.groupchannelservice.domain.Channel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface ChannelRepository : JpaRepository<Channel, UUID> {
    fun findByName(name: String): Optional<Channel>
}