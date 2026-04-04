package edu.nd.pmcburne.hello.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//marks this interface as a DAO for rOOM
//DAOs define how the app interacts with the database
@Dao
interface PlaceDao {
    //SQL query to retrieve all rows from the table
    @Query("SELECT * FROM placeentity")
    fun getAllPlacesFlow(): Flow<List<PlaceEntity>>

    //Inserts a new PlaceEntity into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(place: PlaceEntity)
}
