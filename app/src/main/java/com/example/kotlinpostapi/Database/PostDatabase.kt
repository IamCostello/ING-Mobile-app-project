package com.example.kotlinpostapi.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinpostapi.apiObjects.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Post::class], version = 1, exportSchema = false)
public abstract class
PostDatabase() : RoomDatabase() {
    abstract fun PostDao(): PostDao

//    private class PostDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { postDatabase ->
//                scope.launch {
//                    populateDatabase(postDatabase.PostDao())
//                }
//            }
//        }
//
//        suspend fun populateDatabase(postDao: PostDao) {
////            postDao.clearCache()
////            postDao.clearDB()
////            val posts = mutableListOf<Post>()
////            for(i in 1..1) {
////                posts.add(Post(null,null ,null,"Title $i", "Body", null, null))
////            }
////            postDao.insertPost(posts)
//        }
//    }

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
                )
//                    .addCallback(PostDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}