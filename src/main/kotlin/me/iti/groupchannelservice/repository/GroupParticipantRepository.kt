package me.iti.groupchannelservice.repository

import me.iti.groupchannelservice.domain.GroupParticipant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface GroupParticipantRepository : JpaRepository<GroupParticipant, Long> {
    fun findByGroupId(groupId: UUID): List<GroupParticipant>
    fun findByGroupIdAndUserId(groupId: UUID, userId: UUID): GroupParticipant
}

