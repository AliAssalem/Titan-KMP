package app.ali.titan.storage

import androidx.room3.Room
import androidx.room3.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseBuilderFactory {
    @OptIn(ExperimentalForeignApi::class)
    actual fun create(): RoomDatabase.Builder<TitanDatabase> {
        val documentsDir =
            NSFileManager.defaultManager
                .URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = true,
                    error = null,
                )?.path ?: error("Unable to resolve documents directory")
        val dbPath = "$documentsDir/$Titan_DATABASE_NAME"
        return Room.databaseBuilder<TitanDatabase>(name = dbPath)
    }
}
