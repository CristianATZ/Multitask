package net.cristianzvl.multitask.Room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [NotesData::class], version = 1, exportSchema = true)
abstract class NoteDB : RoomDatabase() {
    abstract fun NotesDao(): NoteDao
}

/*
@Database(entities = [NotesData::class], version = 1, exportSchema = true)
abstract class WorkDB : RoomDatabase() {
    abstract fun WorksDao(): WorkDao
}
*/


/*companion object {
        @Volatile
        private var Instance: MultiDatabase? = null

        fun getDatabase(context: Context): MultiDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MultiDatabase::class.java, "item_database")
                    .build().also { Instance = it }
            }
        }
    }*/