package me.iti.groupchannelservice.service

import jakarta.persistence.EntityNotFoundException
import me.iti.groupchannelservice.domain.Channel
import me.iti.groupchannelservice.domain.ChannelSubscriber
import me.iti.groupchannelservice.exception.EntityAlreadyExistsException
import me.iti.groupchannelservice.repository.ChannelRepository
import me.iti.groupchannelservice.repository.ChannelSubscriberRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class ChannelServiceTest {

    @Mock
    private lateinit var channelRepository: ChannelRepository

    @Mock
    private lateinit var channelSubscriberRepository: ChannelSubscriberRepository

    @InjectMocks
    private lateinit var channelService: ChannelService

    @Test
    fun `should create channel successfully`() {
        val name = "Channel1"
        val ownerId = UUID.randomUUID()
        val url = "http://channel1.com"
        val channel = Channel(name = name, ownerId = ownerId, url = url)

        // Use doReturn for stubbing
        doReturn(channel).`when`(channelRepository).save(anyOrNull())

        val createdChannel = channelService.createChannel(name, ownerId, url)

        verify(channelRepository).save(anyOrNull())
        assertEquals(channel, createdChannel)
    }

    @Test
    fun `should subscribe to channel successfully`() {
        val userId = UUID.randomUUID()
        val channelId = UUID.randomUUID()
        val channel = Channel(name = "Channel1", ownerId = UUID.randomUUID(), url = "http://channel1.com")

        whenever(channelRepository.findById(channelId)).thenReturn(Optional.of(channel))
        whenever(channelSubscriberRepository.findByChannelIdAndUserId(channelId, userId)).thenReturn(null)

        assertDoesNotThrow { channelService.subscribeToChannel(userId, channelId) }

        verify(channelSubscriberRepository).save(any())
    }

    @Test
    fun `should throw EntityNotFoundException when channel not found`() {
        val userId = UUID.randomUUID()
        val channelId = UUID.randomUUID()

        whenever(channelRepository.findById(channelId)).thenReturn(Optional.empty())

        assertThrows<EntityNotFoundException> { channelService.subscribeToChannel(userId, channelId) }
    }

    @Test
    fun `should throw EntityAlreadyExistsException when user already subscribed to channel`() {
        val userId = UUID.randomUUID()
        val channelId = UUID.randomUUID()
        val channel = Channel(name = "Channel1", ownerId = UUID.randomUUID(), url = "http://channel1.com")
        val existingSubscriber = ChannelSubscriber(userId = userId, channel = channel)

        whenever(channelRepository.findById(channelId)).thenReturn(Optional.of(channel))
        whenever(channelSubscriberRepository.findByChannelIdAndUserId(channelId, userId)).thenReturn(existingSubscriber)

        assertThrows<EntityAlreadyExistsException> { channelService.subscribeToChannel(userId, channelId) }
    }


}
