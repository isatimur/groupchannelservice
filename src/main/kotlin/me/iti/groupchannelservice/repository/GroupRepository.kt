package me.iti.groupchannelservice.repository

import me.iti.groupchannelservice.domain.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface GroupRepository : JpaRepository<Group, UUID> {
    fun findByName(name: String): Optional<Group>
}