package com.example.todoapp.DAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [CategoryEntity::class, TaskEntity::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database" // название файла базы
                ) .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL("INSERT INTO categories (name, userToken) VALUES ('No category', 'default')")
                        db.execSQL("INSERT INTO categories (name, userToken) VALUES ('Work', 'default')")
                        db.execSQL("INSERT INTO categories (name, userToken) VALUES ('Personal', 'default')")
                        db.execSQL("INSERT INTO categories (name, userToken) VALUES ('Study', 'default')")
                    }
                }).addMigrations(MIGRATION_7_8)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE categories ADD COLUMN userId TEXT NOT NULL DEFAULT ''")

        // Создаём новую таблицу tasks
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS tasks (
                id INTEGER PRIMARY KEY,
                title TEXT NOT NULL,
                content TEXT NOT NULL,
                category TEXT,
                timeCategory TEXT,
                taskCreatedTime TEXT,
                reminderTime TEXT,
                dateTime TEXT,
                userToken TEXT NOT NULL
            )
        """.trimIndent())

    }
}