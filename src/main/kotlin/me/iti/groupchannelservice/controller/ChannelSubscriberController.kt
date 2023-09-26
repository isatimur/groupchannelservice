package me.iti.groupchannelservice.controller

import me.iti.groupchannelservice.domain.ChannelSubscriber
import me.iti.groupchannelservice.dto.SubscribeUserRequest
import me.iti.groupchannelservice.service.ChannelSubscriberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/channel-subscribers")
class ChannelSubscriberController(@Autowired private val channelSubscriberService: ChannelSubscriberService) {
    @PostMapping
    fun subscribeUser(@RequestBody request: SubscribeUserRequest): ResponseEntity<ChannelSubscriber> {
        val subscriber = channelSubscriberService.subscribeUser(request.channelId, request.userId)
        return ResponseEntity.ok(subscriber)
    }
}