package de.brunokrams.gaunertrio.model

import de.brunokrams.gaunertrio.persistence.ScoreDao
import de.brunokrams.gaunertrio.persistence.ScoreEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScoreRepository @Inject constructor(private val scoreDao: ScoreDao) {

    suspend fun addScore(score: Score) {
        scoreDao.insertScore(ScoreEntity(timestamp = score.timestamp, score = score.score))
        if (scoreDao.getCount() > 10) {
            val itemToDelete = scoreDao.getNewestMinimalScore()
            itemToDelete?.let {
                scoreDao.delete(it)
            }
        }
    }
    fun getTopTen(): Flow<List<Score>> {
        return scoreDao.getTopTen().map { entities ->
            entities.map { scoreEntity ->
                Score(timestamp = scoreEntity.timestamp, score = scoreEntity.score)
            }
        }
    }

}