package app.ali.titan.storage

import androidx.room3.RoomDatabase

expect class DatabaseBuilderFactory {
    fun create(): RoomDatabase.Builder<TitanDatabase>
}
