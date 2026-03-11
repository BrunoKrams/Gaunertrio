package de.brunokrams.gaunertrio

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import de.brunokrams.gaunertrio.model.Score
import de.brunokrams.gaunertrio.model.ScoreRepository
import de.brunokrams.gaunertrio.persistence.AppDatabase
import de.brunokrams.gaunertrio.persistence.ScoreDao
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime

class ScoreRepositoryTest {

    private lateinit var db: AppDatabase

    private lateinit var scoreDao: ScoreDao
    private lateinit var scoreRepository: ScoreRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        scoreDao = db.scoreDao()
        scoreRepository = ScoreRepository(scoreDao)
    }

    @Test
    fun addScoreInsertsScore() {
        // given
        val now = LocalDateTime.now();
        val score = Score(now, 900000)

        // when
        runBlocking { scoreRepository.addScore(score) }

        // then
        val storedScores = runBlocking { scoreRepository.getTopTen().first()}
        assertEquals(1, storedScores.size)
        assertEquals(score, storedScores.get(0))
    }

    @Test
    fun addScoreInsertsScoreAndRemovesElementWithLeastScoreWhenThereAreMoreThan10() {
        // given
        val now = LocalDateTime.now();
        val scores = List(11) { index ->
            Score(now.minusDays(index.toLong()), (800_000..999_999).random())
        }
        runBlocking { scores.forEach { scoreRepository.addScore(it) } }

        val minScore =
            scores.minWithOrNull(compareBy<Score> { it.score }.thenByDescending { it.timestamp })

        // then
        val storedScores = runBlocking { scoreRepository.getTopTen().first() }
        val nrOfEntries = runBlocking { scoreDao.getCount() }
        assertEquals(10, nrOfEntries)
        assertFalse(storedScores.contains(minScore))
    }

    @Test
    fun getTopTenReturnsEmptyListWhenNoScoresHaveBeenStored() {
        // when / then
        val storedScores = runBlocking { scoreRepository.getTopTen().first() }
        assertEquals(0, storedScores.size)
    }

    @Test
    fun getTopTenReturnsTheFullListOfScoresWhenThereAreLessThan10Scores() {
        // given
        runBlocking {
            repeat(7) { index ->

                scoreRepository.addScore(
                    Score(
                        LocalDateTime.of(2025, 7, index + 1, 14, 30),
                        index * 100000
                    )
                )
            }
        }

        // when
        val storesScores = runBlocking { scoreRepository.getTopTen().first()}

        // then
        assertEquals(7, storesScores.size)
    }

    @Test
    fun getTopTenReturnsTheTopTenScoresInTheCorrectOrder() {
        // given
        runBlocking {
            repeat(10) { index ->
                scoreRepository.addScore(Score(LocalDateTime.now(), (800_000..999_999).random()))
            }
        }

        // when
        val storesScores = runBlocking { scoreRepository.getTopTen().first()}

        // then
        assert(storesScores == storesScores.sortedByDescending { it.score })
    }

    @Test
    fun getTopTenReturnsTheTopTenScoresFromOldestToNewstWhenThereAreTiesInScores() {
        // given
        val now = LocalDateTime.now()
        val score = 999_001
        val score1 = Score(score = score, timestamp = now)
        val score2 = Score(score = score, timestamp = now.minusDays(1))
        runBlocking {
            scoreRepository.addScore(score1)
            scoreRepository.addScore(score2)
        }

        // when
        val storesScores = runBlocking { scoreRepository.getTopTen().first()}

        // then
        assertEquals(score1, storesScores.get(1))
        assertEquals(score2, storesScores.get(0))
    }
}

