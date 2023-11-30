package net.cristianzvl.multitask.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

// tabla para notas
@Entity(tableName = Constants.NotesTable.TABLE_NAME)
data class NotesData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = Constants.NotesTable.NAME) val titlenote: String,
    @ColumnInfo(name = Constants.NotesTable.DESC) val descnote: String,
    @ColumnInfo(name = Constants.NotesTable.DAY) val daynote: String,
    @ColumnInfo(name = Constants.NotesTable.MONTH) val monthnote: String,
)

@Entity(tableName = Constants.WorksTable.TABLE_NAME)
data class WorksData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = Constants.WorksTable.NAME) val titlework: String,
    @ColumnInfo(name = Constants.WorksTable.DESC) val descwork: String
)
