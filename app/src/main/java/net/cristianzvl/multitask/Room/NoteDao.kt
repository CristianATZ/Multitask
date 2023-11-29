package net.cristianzvl.multitask.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// dao para las notas
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: NotesData)

    @Update
    fun update(item: NotesData)

    @Delete
    fun delete(item: NotesData)

    @Query("SELECT * FROM notestable WHERE id = :id")
    fun getItem(id: Int): NotesData

    @Query("SELECT COUNT(${Constants.NotesTable.NAME}) FROM notestable")
    fun getCount(): Int

    @Query("SELECT * from notestable")
    fun getAllItems(): List<NotesData>
}

/*
@Dao
interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: WorksData)

    @Update
    suspend fun update(item: WorksData)

    @Delete
    suspend fun delete(item: WorksData)

    @Query("SELECT * FROM workstable WHERE id = :id")
    fun getItem(id: Int): WorksData

    @Query("SELECT * from workstable")
    fun getAllItems(): List<WorksData>
}*/
