package app.ali.titan.storage

import androidx.room3.Room
import androidx.room3.RoomDatabase
import java.io.File

actual class DatabaseBuilderFactory {
    actual fun create(): RoomDatabase.Builder<TitanDatabase> {
        val appDataDir = File(System.getProperty("user.home"), ".titan")
        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }
        val dbFile = File(appDataDir, Titan_DATABASE_NAME)
        return Room.databaseBuilder<TitanDatabase>(
            name = dbFile.absolutePath,
        )
    }
}