package io.github.codenilson.lavava2026.players

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@DataJpaTest
@ActiveProfiles("dev")
class PlayerRepositoryTest
    @Autowired
    constructor(private val repository: PlayerRepository)
{

    @Test
    fun `findActivePlayers should return only active players`() {
        // Given
        val activePlayer = Player(
            puuid = "active-puuid",
            gameName = "ActivePlayer",
            tagName = "1234",
            competitiveTier = 10,
            playerCard = "card1",
            playerTitle = "title1",
            accountLevel = 50,
            active = true,
        )

        val inactivePlayer = Player(
            puuid = "inactive-puuid",
            gameName = "InactivePlayer",
            tagName = "5678",
            competitiveTier = 5,
            playerCard = "card2",
            playerTitle = "title2",
            accountLevel = 20,
            active = false,
        )

        repository.saveAll(listOf(activePlayer, inactivePlayer))

        // When
        val activePlayers = repository.findByActiveTrue()

        // Then
        assertEquals(1, activePlayers.size)
        assertEquals("active-puuid", activePlayers[0].puuid)
    }
}