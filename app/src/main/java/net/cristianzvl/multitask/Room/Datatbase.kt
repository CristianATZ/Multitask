package net.cristianzvl.multitask.Room

import android.content.ClipData
import android.content.Context
import android.media.RouteListingPreference
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.ClipData.Item

// aqui es ITEM
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class MultiDatabase : RoomDatabase() {
    abstract fun ItemDao(): NoteDao

    companion object {
        @Volatile
        private var Instance: MultiDatabase? = null

        fun getDatabase(context: Context): MultiDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MultiDatabase::class.java, "item_database")
                    .build().also { Instance = it }
            }
        }
    }
}
