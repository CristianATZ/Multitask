package net.cristianzvl.multitask.Room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [NotesData::class, WorksData::class], version = 1, exportSchema = true)
abstract class TaskDB : RoomDatabase() {
    abstract fun NotesDao(): NoteDao
    abstract fun WorksDao(): WorkDao
}