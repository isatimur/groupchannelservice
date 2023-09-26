package me.iti.groupchannelservice.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "channels")
data class Channel(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val ownerId: UUID,
    val name: String,
    val url: String,

    @OneToMany(mappedBy = "channel", cascade = [CascadeType.ALL], orphanRemoval = true)
    val subscribers: Set<ChannelSubscriber> = HashSet()
)
