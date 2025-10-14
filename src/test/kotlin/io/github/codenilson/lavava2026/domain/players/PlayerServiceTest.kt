package io.github.codenilson.lavava2026.domain.players

import io.github.codenilson.lavava2026.application.services.PlayerService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when`
import org.springframework.data.domain.Sort
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class PlayerServiceTest {

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @InjectMocks
    private lateinit var playerService: PlayerService

    @Test
    fun `findAll without active filter should return all players as DTOs`() {
        // Given: two players, one active and one inactive
        val player1 = createTestPlayer(
            id = UUID.randomUUID(),
            puuid = "puuid1",
            gameName = "Player1",
            tagName = "1234",
            active = true
        )
        val player2 = createTestPlayer(
            id = UUID.randomUUID(),
            puuid = "puuid2",
            gameName = "Player2",
            tagName = "5678",
            active = false
        )
        val players = listOf(player1, player2)
        val sort = Sort.by(Sort.Direction.DESC, "id")
        `when`(playerRepository.findAll(sort)).thenReturn(players)

        // When: calling findAll with no active filter
        val result = playerService.findAll(active = null, sort = sort)

        // Then: should return both players
        assertEquals(2, result.size)

        // Check first player (active)
        with(result[0]) {
            assertEquals(player1.id, id)
            assertEquals("Player1", gameName)
            assertEquals("1234", tagName)
            assertEquals(true, active)
            assertEquals(player1.competitiveTier, competitiveTier)
            assertEquals(player1.playerCard, playerCard)
            assertEquals(player1.playerTitle, playerTitle)
            assertEquals(player1.accountLevel, accountLevel)
            assertEquals(player1.updatedAt, updatedAt)
            assertEquals(player1.createdAt, createdAt)
        }

        // Check second player (inactive)
        with(result[1]) {
            assertEquals(player2.id, id)
            assertEquals("Player2", gameName)
            assertEquals("5678", tagName)
            assertEquals(false, active)
            assertEquals(player2.competitiveTier, competitiveTier)
            assertEquals(player2.playerCard, playerCard)
            assertEquals(player2.playerTitle, playerTitle)
            assertEquals(player2.accountLevel, accountLevel)
            assertEquals(player2.updatedAt, updatedAt)
            assertEquals(player2.createdAt, createdAt)
        }
    }

    @Test
    fun `findAll without active filter should return empty list when no players exist`() {
        // Given: repository returns empty list
        val sort = Sort.by(Sort.Direction.DESC, "id")
        `when`(playerRepository.findAll(sort)).thenReturn(emptyList())

        // When: calling findAll with no active filter
        val result = playerService.findAll(active = null, sort = sort)

        // Then: should return empty list
        assertEquals(0, result.size)
    }

    @Test
    fun `findAll with active true should return only active players excluding inactive ones`() {
        // Given: two active players
        val activePlayer1 = createTestPlayer(
            id = UUID.randomUUID(),
            puuid = "active-puuid1",
            gameName = "ActivePlayer1",
            tagName = "1111",
            active = true
        )
        val activePlayer2 = createTestPlayer(
            id = UUID.randomUUID(),
            puuid = "active-puuid2",
            gameName = "ActivePlayer2",
            tagName = "2222",
            active = true
        )
        val activePlayers = listOf(activePlayer1, activePlayer2)
        val sort = Sort.by(Sort.Direction.DESC, "id")
        `when`(playerRepository.findByActive(true, sort)).thenReturn(activePlayers)

        // When: calling findAll with active = true
        val result = playerService.findAll(active = true, sort = sort)

        // Then: should return only active players
        assertEquals(2, result.size)

        // Check that all returned players are active
        result.forEach { player ->
            assertEquals(true, player.active, "All returned players should be active")
        }

        // Check specific content of active players
        with(result[0]) {
            assertEquals(activePlayer1.id, id)
            assertEquals("ActivePlayer1", gameName)
            assertEquals("1111", tagName)
        }

        with(result[1]) {
            assertEquals(activePlayer2.id, id)
            assertEquals("ActivePlayer2", gameName)
            assertEquals("2222", tagName)
        }
    }

    @Test
    fun `findAll with active false should return only inactive players excluding active ones`() {
        // Given: two inactive players
        val inactivePlayer1 = createTestPlayer(
            id = UUID.randomUUID(),
            puuid = "inactive-puuid1",
            gameName = "InactivePlayer1",
            tagName = "3333",
            active = false
        )
        val inactivePlayer2 = createTestPlayer(
            id = UUID.randomUUID(),
            puuid = "inactive-puuid2",
            gameName = "InactivePlayer2",
            tagName = "4444",
            active = false
        )
        val inactivePlayers = listOf(inactivePlayer1, inactivePlayer2)
        val sort = Sort.by(Sort.Direction.DESC, "id")
        `when`(playerRepository.findByActive(false, sort)).thenReturn(inactivePlayers)

        // When: calling findAll with active = false
        val result = playerService.findAll(active = false, sort = sort)

        // Then: should return only inactive players
        assertEquals(2, result.size)

        // Check that all returned players are inactive
        result.forEach { player ->
            assertEquals(false, player.active, "All returned players should be inactive")
        }

        // Check specific content of inactive players
        with(result[0]) {
            assertEquals(inactivePlayer1.id, id)
            assertEquals("InactivePlayer1", gameName)
            assertEquals("3333", tagName)
        }

        with(result[1]) {
            assertEquals(inactivePlayer2.id, id)
            assertEquals("InactivePlayer2", gameName)
            assertEquals("4444", tagName)
        }
    }

    @Test
    fun `findAll with active true should return empty list when no active players exist`() {
        // Given: repository returns empty list for active=true
        val sort = Sort.by(Sort.Direction.DESC, "id")
        `when`(playerRepository.findByActive(true, sort)).thenReturn(emptyList())

        // When: calling findAll with active = true
        val result = playerService.findAll(active = true, sort = sort)

        // Then: should return empty list
        assertEquals(0, result.size)
    }

    @Test
    fun `findAll with active false should return empty list when no inactive players exist`() {
        // Given: repository returns empty list for active=false
        val sort = Sort.by(Sort.Direction.DESC, "id")
        `when`(playerRepository.findByActive(false, sort)).thenReturn(emptyList())

        // When: calling findAll with active = false
        val result = playerService.findAll(active = false, sort = sort)

        // Then: should return empty list
        assertEquals(0, result.size)
    }

    private fun createTestPlayer(
        id: UUID,
        puuid: String,
        gameName: String,
        tagName: String,
        active: Boolean,
        competitiveTier: Int = 10,
        playerCard: String = "default-card",
        playerTitle: String = "default-title",
        accountLevel: Int = 50
    ): Player {
        // Create a test player with default values
        val now = LocalDateTime.now()
        return Player(
            id = id,
            puuid = puuid,
            gameName = gameName,
            tagName = tagName,
            competitiveTier = competitiveTier,
            playerCard = playerCard,
            playerTitle = playerTitle,
            accountLevel = accountLevel,
            active = active,
            updatedAt = now,
            createdAt = now
        )
    }
}