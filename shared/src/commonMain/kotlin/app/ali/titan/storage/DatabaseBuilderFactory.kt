package app.ali.titan.storage

import androidx.room.RoomDatabase

expect class DatabaseBuilderFactory {
    fun create(): RoomDatabase.Builder<TitanDatabase>
}
