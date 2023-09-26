package me.iti.groupchannelservice.repository

import me.iti.groupchannelservice.domain.ChannelSubscriber
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ChannelSubscriberRepository : JpaRepository<ChannelSubscriber, Long> {
    fun findByChannelId(channelId: UUID): List<ChannelSubscriber>
    fun findByChannelIdAndUserId(channelId: UUID, userId: UUID): ChannelSubscriber
}