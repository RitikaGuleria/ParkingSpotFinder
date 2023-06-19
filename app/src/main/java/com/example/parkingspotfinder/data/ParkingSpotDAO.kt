package com.example.parkingspotfinder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingSpotDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParkingSpot(spot : ParkingSpotEntity)

    @Delete
    suspend fun deleteParkingSpot(spot : ParkingSpotEntity)

    @Query("Select * from parkingspotentity")
    fun getParkingSpots() : Flow<List<ParkingSpotEntity>>
}