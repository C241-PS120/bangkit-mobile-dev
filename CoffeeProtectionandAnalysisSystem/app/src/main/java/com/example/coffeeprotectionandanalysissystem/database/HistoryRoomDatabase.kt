package com.example.coffeeprotectionandanalysissystem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [History::class], version = 3, exportSchema = false)
@TypeConverters(ConverterType::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1, MIGRATION_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE History_Scan ADD COLUMN symptoms TEXT")
            }
        }

        private val MIGRATION_2 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE History_Scan ADD COLUMN symptoms TEXT DEFAULT NULL")
            }
        }

    }
}
