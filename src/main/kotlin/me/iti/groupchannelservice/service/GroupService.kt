package me.iti.groupchannelservice.service

import jakarta.persistence.EntityNotFoundException
import me.iti.groupchannelservice.domain.Group
import me.iti.groupchannelservice.domain.GroupParticipant
import me.iti.groupchannelservice.exception.EntityAlreadyExistsException
import me.iti.groupchannelservice.exception.Errors
import me.iti.groupchannelservice.repository.GroupParticipantRepository
import me.iti.groupchannelservice.repository.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GroupService(
    @Autowired private val groupRepository: GroupRepository,
    @Autowired private val groupParticipantRepository: GroupParticipantRepository
) {
    @Transactional
    fun createGroup(userId: UUID): Group {
        val group = Group(name = userId.toString())
        val savedGroup = groupRepository.save(group)

        groupParticipantRepository.save(GroupParticipant(userId = userId, group = savedGroup))
        return savedGroup
    }

    @Transactional
    fun joinGroup(userId: UUID, groupId: UUID) {
        val group = groupRepository.findById(groupId)
            .orElseThrow { EntityNotFoundException("Group with id $groupId not found") }
        val existingParticipant = groupParticipantRepository.findByGroupIdAndUserId(groupId, userId)
        if (existingParticipant != null) {
           throw EntityAlreadyExistsException(Errors.ENTITY_ALREADY_EXISTS, userId, groupId)
        }
        groupParticipantRepository.save(GroupParticipant(userId = userId, group = group))

    }
}