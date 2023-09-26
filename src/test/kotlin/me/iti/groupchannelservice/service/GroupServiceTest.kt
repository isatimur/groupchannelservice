import jakarta.persistence.EntityNotFoundException
import me.iti.groupchannelservice.domain.Group
import me.iti.groupchannelservice.domain.GroupParticipant
import me.iti.groupchannelservice.exception.EntityAlreadyExistsException
import me.iti.groupchannelservice.repository.GroupParticipantRepository
import me.iti.groupchannelservice.repository.GroupRepository
import me.iti.groupchannelservice.service.GroupService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class GroupServiceTest {

    @Mock
    private lateinit var groupRepository: GroupRepository

    @Mock
    private lateinit var groupParticipantRepository: GroupParticipantRepository

    @InjectMocks
    private lateinit var groupService: GroupService


    @Test
    fun `should create group successfully`() {
        val userId = UUID.randomUUID()
        val group = Group(id= UUID.randomUUID(), name = userId.toString())
        val groupParticipant = GroupParticipant(userId = userId, group = group)

        whenever(groupRepository.save(any())).thenReturn(group)
        whenever(groupParticipantRepository.save(any())).thenReturn(groupParticipant)

        val createdGroup = groupService.createGroup(userId)

        verify(groupRepository).save(any())
        verify(groupParticipantRepository).save(any())
        assertEquals(group, createdGroup)
    }

    @Test
    fun `should join group successfully`() {
        val userId = UUID.randomUUID()
        val groupId = UUID.randomUUID()
        val group = Group(id = groupId, name = "GroupName")

        whenever(groupRepository.findById(groupId)).thenReturn(Optional.of(group))
        whenever(groupParticipantRepository.findByGroupIdAndUserId(groupId, userId)).thenReturn(null)
        whenever(groupParticipantRepository.save(any())).thenReturn(GroupParticipant(userId = userId, group = group))

        groupService.joinGroup(userId, groupId)

        verify(groupRepository).findById(groupId)
        verify(groupParticipantRepository).findByGroupIdAndUserId(groupId, userId)
        verify(groupParticipantRepository).save(any())
    }

    @Test
    fun `should throw EntityNotFoundException when group not found`() {
        val userId = UUID.randomUUID()
        val groupId = UUID.randomUUID()

        whenever(groupRepository.findById(groupId)).thenReturn(Optional.empty())

        assertThrows<EntityNotFoundException> {
            groupService.joinGroup(userId, groupId)
        }

        verify(groupRepository).findById(groupId)
        verify(groupParticipantRepository, never()).findByGroupIdAndUserId(anyOrNull(), anyOrNull())
        verify(groupParticipantRepository, never()).save(anyOrNull())
    }

    @Test
    fun `should throw EntityAlreadyExistsException when user already in group`() {
        val userId = UUID.randomUUID()
        val groupId = UUID.randomUUID()
        val group = Group(id = groupId, name = "GroupName")
        val existingParticipant = GroupParticipant(userId = userId, group = group)

        whenever(groupRepository.findById(groupId)).thenReturn(Optional.of(group))
        whenever(groupParticipantRepository.findByGroupIdAndUserId(groupId, userId)).thenReturn(existingParticipant)

        assertThrows<EntityAlreadyExistsException> {
            groupService.joinGroup(userId, groupId)
        }

        verify(groupRepository).findById(groupId)
        verify(groupParticipantRepository).findByGroupIdAndUserId(groupId, userId)
        verify(groupParticipantRepository, never()).save(any())
    }

}