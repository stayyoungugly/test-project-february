package com.example.febtestproject.data.db.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.febtestproject.data.db.local.dao.ReviewDao
import com.example.febtestproject.data.db.local.model.ReviewEntity

@Database(entities = [ReviewEntity::class], version = 1, exportSchema = false)
abstract class ReviewDatabase : RoomDatabase() {

    abstract fun getReviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: ReviewDatabase? = null

        private const val DB_NAME = "review_database.db"

        fun getDatabase(context: Context): ReviewDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReviewDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
