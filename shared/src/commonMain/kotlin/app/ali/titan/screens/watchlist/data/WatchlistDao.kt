package app.ali.titan.screens.watchlist.data

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WatchlistDao {
    @Query("SELECT * FROM watchlist_items ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<WatchlistItemEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist_items WHERE id = :id AND mediaType = :mediaType)")
    fun observeContains(
        id: Int,
        mediaType: String,
    ): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist_items WHERE id = :id AND mediaType = :mediaType)")
    suspend fun contains(
        id: Int,
        mediaType: String,
    ): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: WatchlistItemEntity)

    @Query("DELETE FROM watchlist_items WHERE id = :id AND mediaType = :mediaType")
    suspend fun deleteById(
        id: Int,
        mediaType: String,
    )
}
