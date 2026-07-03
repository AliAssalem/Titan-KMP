package app.ali.titan.storage

import androidx.room3.Room
import androidx.room3.RoomDatabase

actual class DatabaseBuilderFactory {
    actual fun create(): RoomDatabase.Builder<TitanDatabase> {
        return Room.databaseBuilder<TitanDatabase>(
            name = Titan_DATABASE_NAME,
        )
    }
}