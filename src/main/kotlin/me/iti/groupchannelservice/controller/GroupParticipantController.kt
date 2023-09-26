package me.iti.groupchannelservice.controller

import me.iti.groupchannelservice.domain.GroupParticipant
import me.iti.groupchannelservice.dto.AddParticipantRequest
import me.iti.groupchannelservice.service.GroupParticipantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/group-participants")
class GroupParticipantController(@Autowired private val groupParticipantService: GroupParticipantService) {
    @PostMapping
    fun addParticipant(@RequestBody request: AddParticipantRequest): ResponseEntity<GroupParticipant> {
        val participant = groupParticipantService.addParticipant(request.groupId, request.userId)
        return ResponseEntity.ok(participant)
    }
}