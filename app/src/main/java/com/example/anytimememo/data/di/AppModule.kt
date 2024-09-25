package com.example.anytimememo.data.di


import android.content.Context
import androidx.room.Room
import com.example.anytimememo.data.local.NoteDatabase
import com.example.anytimememo.data.repositery.NoteRepositoryImplementation
import com.example.anytimememo.domain.repositery.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()


    @Provides
    @Singleton
    fun provideNoteRepository(dataBase: NoteDatabase): NoteRepository =
        NoteRepositoryImplementation(dao = dataBase.dao)
}
