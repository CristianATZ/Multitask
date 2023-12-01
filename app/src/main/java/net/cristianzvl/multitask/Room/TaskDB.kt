package net.cristianzvl.multitask.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [NotesData::class, WorksData::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class TaskDB : RoomDatabase() {
    abstract fun NotesDao(): NoteDao
    abstract fun WorksDao(): WorkDao
}