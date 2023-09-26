package me.iti.groupchannelservice.controller

import me.iti.groupchannelservice.domain.Group
import me.iti.groupchannelservice.service.GroupService
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
@RequestMapping("/api/groups")
class GroupController(
    @Autowired private val groupService: GroupService
) {
    @PostMapping
    fun createGroup(@RequestParam userId: UUID): ResponseEntity<Group> {
        val group = groupService.createGroup(userId)
        return ResponseEntity(group, HttpStatus.CREATED)
    }

    @PostMapping("/{groupId}/join")
    fun joinGroup(@PathVariable groupId: UUID, @RequestParam userId: UUID): ResponseEntity<Void> {
        groupService.joinGroup(userId, groupId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}