package me.iti.groupchannelservice.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "groups")
data class Group(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val name: String,

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
    val participants: Set<GroupParticipant> = HashSet()
)
