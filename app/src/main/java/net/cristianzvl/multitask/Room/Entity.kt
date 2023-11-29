package net.cristianzvl.multitask.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// tabla para notas
@Entity(tableName = Constants.NotesTable.TABLE_NAME)
data class NotesData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = Constants.NotesTable.NAME) val titlenote: String,
    @ColumnInfo(name = Constants.NotesTable.DESC) val descnote: String,
//    @ColumnInfo(name = Contract.NotesTable.DATE) val datenote: LocalDate,
)

@Entity(tableName = Constants.WorksTable.TABLE_NAME)
data class WorksData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = Constants.WorksTable.NAME) val titlework: String,
    @ColumnInfo(name = Constants.WorksTable.DESC) val descwork: String
)
