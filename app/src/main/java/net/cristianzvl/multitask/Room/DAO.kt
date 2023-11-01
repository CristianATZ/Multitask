package net.cristianzvl.multitask.Room

import android.content.ClipData.Item
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// dao para las notas
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from notes")
    fun getAllItems(): Flow<List<Item>>
}


// dao para las tareas
/*
@Dao
interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM works WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from works")
    fun getAllItems(): Flow<List<Item>>
}*/
