package app.ali.titan.storage

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseBuilderFactory(
    private val context: Context,
) {
    actual fun create(): RoomDatabase.Builder<TitanDatabase> {
        val dbFile = context.getDatabasePath(Titan_DATABASE_NAME)
        return Room.databaseBuilder<TitanDatabase>(
            context = context.applicationContext,
            name = dbFile.absolutePath,
        )
    }
}
