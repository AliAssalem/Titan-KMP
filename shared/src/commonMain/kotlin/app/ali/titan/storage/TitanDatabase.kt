package app.ali.titan.storage

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import app.ali.titan.screens.watchlist.data.WatchlistDao
import app.ali.titan.screens.watchlist.data.WatchlistItemEntity

@Database(entities = [WatchlistItemEntity::class], version = 2, exportSchema = true)
@ConstructedBy(TitanDatabaseConstructor::class)
abstract class TitanDatabase : RoomDatabase() {
    internal abstract fun watchlistDao(): WatchlistDao
}

// Room KSP generates the actual implementation for each target.
@Suppress("NO_ACTUAL_FOR_EXPECT", "KotlinNoActualForExpect")
expect object TitanDatabaseConstructor : RoomDatabaseConstructor<TitanDatabase> {
    override fun initialize(): TitanDatabase
}

internal const val Titan_DATABASE_NAME = "titan.db"