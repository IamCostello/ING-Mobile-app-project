package com.example.kotlinpostapi.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserPost::class], version = 1)
public abstract class PostDatabase() : RoomDatabase() {
    abstract fun PostDao(): PostDao

    companion object {
        private var INSTANCE: PostDatabase? = null

        fun getPostDatabase(context: Context): PostDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    "post_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}