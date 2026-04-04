package edu.nd.pmcburne.hello.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Defines room databse with one entity
@Database(entities = [PlaceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    //abstract funtion that provides access to the DAO
    //Room generates the implementation at runtime.
    abstract fun placeDao(): PlaceDao

    companion object {
        //volatile ensures visibility of INSTANCE across threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if INSTANCE already exists, return it
            // otherwise, creates it in a thread-safe way
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "places_db"
                )
                    // if schema changes and no migration is provided this will wipe and rebuild
                    // the database
                    .fallbackToDestructiveMigration()
                    .build()

                //returns the newly created instance
                INSTANCE = instance
                instance
            }
        }
    }
}
