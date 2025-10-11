package io.github.codenilson.lavava2026.players

import io.github.codenilson.lavava2026.players.dto.PlayerResponseDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.`when`
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
    fun `findAll should return all players as DTOs`() {
        // Given
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
        `when`(playerRepository.findAll()).thenReturn(players)

        // When
        val result = playerService.findAll()

        // Then
        assertEquals(2, result.size)
        assertEquals("Player1", result[0].gameName)
        assertEquals("1234", result[0].tagName)
        assertEquals(true, result[0].active)
        assertEquals("Player2", result[1].gameName)
        assertEquals("5678", result[1].tagName)
        assertEquals(false, result[1].active)

    }

    @Test
    fun `findAll should return empty list when no players exist`() {
        // Given
        `when`(playerRepository.findAll()).thenReturn(emptyList())

        // When
        val result = playerService.findAll()

        // Then
        assertEquals(0, result.size)
    }

    @Test
    fun `findActivePlayers should return only active players as DTOs`() {
        // Given
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
        `when`(playerRepository.findByActiveTrue()).thenReturn(activePlayers)

        // When
        val result = playerService.findActivePlayers()

        // Then
        assertEquals(2, result.size)
        assertEquals("ActivePlayer1", result[0].gameName)
        assertEquals("1111", result[0].tagName)
        assertEquals(true, result[0].active)
        assertEquals("ActivePlayer2", result[1].gameName)
        assertEquals("2222", result[1].tagName)
        assertEquals(true, result[1].active)
    }

    @Test
    fun `findActivePlayers should return empty list when no active players exist`() {
        // Given
        `when`(playerRepository.findByActiveTrue()).thenReturn(emptyList())

        // When
        val result = playerService.findActivePlayers()

        // Then
        assertEquals(0, result.size)
    }

    @Test
    fun `findActivePlayers should handle single active player`() {
        // Given
        val activePlayer = createTestPlayer(
            id = UUID.randomUUID(),
            puuid = "single-active-puuid",
            gameName = "SingleActivePlayer",
            tagName = "9999",
            active = true
        )

        `when`(playerRepository.findByActiveTrue()).thenReturn(listOf(activePlayer))

        // When
        val result = playerService.findActivePlayers()

        // Then
        assertEquals(1, result.size)
        assertEquals("SingleActivePlayer", result[0].gameName)
        assertEquals("9999", result[0].tagName)
        assertEquals(true, result[0].active)
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