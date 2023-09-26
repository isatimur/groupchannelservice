package me.iti.groupchannelservice.service

import jakarta.persistence.EntityNotFoundException
import me.iti.groupchannelservice.domain.Channel
import me.iti.groupchannelservice.domain.ChannelSubscriber
import me.iti.groupchannelservice.exception.EntityAlreadyExistsException
import me.iti.groupchannelservice.exception.Errors
import me.iti.groupchannelservice.repository.ChannelRepository
import me.iti.groupchannelservice.repository.ChannelSubscriberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChannelService(
    @Autowired private val channelRepository: ChannelRepository,
    @Autowired private val channelSubscriberRepository: ChannelSubscriberRepository
) {
    @Transactional
    fun createChannel(name: String, ownerId: UUID, url: String): Channel {
        val channel = Channel(name = name, ownerId = ownerId, url = url)
        return channelRepository.save(channel)
    }

    @Transactional
    fun subscribeToChannel(userId: UUID, channelId: UUID) {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { EntityNotFoundException("Channel with id $channelId not found") }
        val existingParticipant = channelSubscriberRepository.findByChannelIdAndUserId(channelId, userId)
        if (existingParticipant != null) {
            throw   EntityAlreadyExistsException(Errors.ENTITY_NOT_UNIQUE, userId, channelId)
        }

        channelSubscriberRepository.save(ChannelSubscriber(userId = userId, channel = channel))

    }
}