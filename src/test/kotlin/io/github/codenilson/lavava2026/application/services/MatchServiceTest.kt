package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import io.github.codenilson.lavava2026.application.mapper.MatchMapper
import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.matches.MatchRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.ValorantMatchDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.MatchInfoDTO
import io.github.codenilson.lavava2026.helpers.ValorantMatchFixtures
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MatchServiceTest {
    @Mock
    private lateinit var matchRepository: MatchRepository

    @Mock
    private lateinit var matchMapper: MatchMapper

    @InjectMocks
    private lateinit var matchService: MatchService

    @Test
    fun `saveFromValorantMatch should throw if match already exists`() {
        val matchId = "match123"
        val valorantMatch = ValorantMatchFixtures.createValorantMatchWithId(matchId)

        `when`(matchRepository.existsByMatchRiotId(matchId)).thenReturn(true)

        assertThrows<ResourceAlreadyExists> {
            matchService.saveFromValorantMatch(valorantMatch)
        }
        verify(matchRepository).existsByMatchRiotId(matchId)
        verifyNoMoreInteractions(matchRepository)
        verifyNoInteractions(matchMapper)
    }

    @Test
    fun `saveFromValorantMatch should map and save when match does not exist`() {
        val matchId = "match456"
        val valorantMatch = ValorantMatchFixtures.createValorantMatchWithId(
            matchId = matchId,
            mapId = "Bind",
            region = "eu"
        )
        val mappedMatch = Match(
            matchRiotId = matchId,
            gameLength = 2100000,
            map = "Bind",
            gameStartMillis = 1634567890000L,
            isCompleted = true,
            season = "e5a1"
        )
        `when`(matchRepository.existsByMatchRiotId(matchId)).thenReturn(false)
        `when`(matchMapper.fromValorantMatch(valorantMatch.matchInfo)).thenReturn(mappedMatch)
        `when`(matchRepository.save(mappedMatch)).thenReturn(mappedMatch)

        val result = matchService.saveFromValorantMatch(valorantMatch)
        assertEquals(mappedMatch, result)
        verify(matchRepository).existsByMatchRiotId(matchId)
        verify(matchMapper).fromValorantMatch(valorantMatch.matchInfo)
        verify(matchRepository).save(mappedMatch)
    }

    @Test
    fun `matchAlreadyExists should return true or false`() {
        val matchId = "match789"
        `when`(matchRepository.existsByMatchRiotId(matchId)).thenReturn(true)
        assertTrue(matchService.matchAlreadyExists(matchId))
        `when`(matchRepository.existsByMatchRiotId(matchId)).thenReturn(false)
        assertFalse(matchService.matchAlreadyExists(matchId))
    }
}
