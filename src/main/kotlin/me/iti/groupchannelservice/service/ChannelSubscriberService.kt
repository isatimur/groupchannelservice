package me.iti.groupchannelservice.service

import jakarta.persistence.EntityNotFoundException
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
class ChannelSubscriberService(
    @Autowired private val channelSubscriberRepository: ChannelSubscriberRepository,
    @Autowired private val channelRepository: ChannelRepository
) {
    @Transactional
    fun subscribeUser(channelId: UUID, userId: UUID): ChannelSubscriber {
        val channel = channelRepository.findById(channelId)
            .orElseThrow { EntityNotFoundException("Channel not found") }
        val existingParticipant = channelSubscriberRepository.findByChannelIdAndUserId(channelId, userId)
        if (existingParticipant != null) {
            throw  EntityAlreadyExistsException(Errors.ENTITY_NOT_UNIQUE, userId, channelId)
        }
        val subscriber = ChannelSubscriber(channel = channel, userId = userId)
        return channelSubscriberRepository.save(subscriber)
    }
}