package de.brunokrams.gaunertrio.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun insertScore(score : ScoreEntity)

    @Query("""
        SELECT * FROM highscores
        WHERE score = (SELECT MIN(score) FROM highscores)
        ORDER BY timestamp DESC
        LIMIT 1
    """)
    suspend fun getNewestMinimalScore(): ScoreEntity?

    @Delete
    suspend fun delete(score : ScoreEntity)

    @Query("SELECT COUNT(*) FROM highscores")
    suspend fun getCount() : Int

    @Query("SELECT * FROM highscores ORDER BY score DESC, timestamp ASC LIMIT 10")
    fun getTopTen(): Flow<List<ScoreEntity>>

}
