package de.brunokrams.gaunertrio

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Config {

    @Provides
    @Singleton
    fun provideDateTimeFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
    }

}