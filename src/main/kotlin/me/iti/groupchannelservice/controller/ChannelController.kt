package me.iti.groupchannelservice.controller

import me.iti.groupchannelservice.domain.Channel
import me.iti.groupchannelservice.service.ChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/channels")
class ChannelController(
    @Autowired private val channelService: ChannelService
) {
    @PostMapping
    fun createChannel(
        @RequestParam channelName: String,
        @RequestParam ownerId: UUID,
        @RequestParam channelUrl: String
    ): ResponseEntity<Channel> {
        val channel = channelService.createChannel(channelName, ownerId, channelUrl)
        return ResponseEntity(channel, HttpStatus.CREATED)
    }

    @PostMapping("/{channelId}/subscribe")
    fun subscribeToChannel(@PathVariable channelId: UUID, @RequestParam userId: UUID): ResponseEntity<Void> {
        channelService.subscribeToChannel(userId, channelId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}