package de.brunokrams.gaunertrio.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime


@Entity(tableName = "highscores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: LocalDateTime,
    val score: Int
)