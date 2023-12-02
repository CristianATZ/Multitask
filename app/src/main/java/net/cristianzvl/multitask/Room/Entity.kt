package net.cristianzvl.multitask.Room

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
    @ColumnInfo(name = Constants.WorksTable.DESC) val descwork: String,
    @ColumnInfo(name = Constants.WorksTable.DATE) val datework: LocalDate,
    @ColumnInfo(name = Constants.WorksTable.HOUR) val hour: LocalTime,
    @ColumnInfo(name = Constants.WorksTable.IMAGE) val images: List<Uri>,
    @ColumnInfo(name = Constants.WorksTable.VIDEO) val videos: List<Uri>,
    @ColumnInfo(name = Constants.WorksTable.AUDIO) val audios: List<Uri>,
    @ColumnInfo(name = Constants.WorksTable.FILE) val files: List<Uri>
)