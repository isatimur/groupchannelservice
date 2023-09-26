package me.iti.groupchannelservice.service

import jakarta.persistence.EntityNotFoundException
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
class GroupParticipantService(
    @Autowired private val groupParticipantRepository: GroupParticipantRepository,
    @Autowired private val groupRepository: GroupRepository
) {
    @Transactional
    fun addParticipant(groupId: UUID, userId: UUID): GroupParticipant {
        val group = groupRepository.findById(groupId)
            .orElseThrow { EntityNotFoundException("Group not found") }
        val existingParticipant = groupParticipantRepository.findByGroupIdAndUserId(groupId, userId)
        if (existingParticipant != null) {
         throw   EntityAlreadyExistsException(Errors.ENTITY_ALREADY_EXISTS, userId, groupId)
        }
        val participant = GroupParticipant(group = group, userId = userId)
        return groupParticipantRepository.save(participant)
    }
}