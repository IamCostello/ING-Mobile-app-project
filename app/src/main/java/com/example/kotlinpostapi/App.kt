package com.example.kotlinpostapi

import android.app.Application
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.example.kotlinpostapi.Albums.AlbumViewModel
import com.example.kotlinpostapi.Comments.CommentsListViewModel
import com.example.kotlinpostapi.Database.PostDao
import com.example.kotlinpostapi.Database.PostDatabase
import com.example.kotlinpostapi.posts.PostViewModel
import com.example.kotlinpostapi.Users.UserViewModel
import com.example.kotlinpostapi.network.PostApi
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.photos.PhotoListViewModel
import com.example.kotlinpostapi.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    private var listOfModules = module {
        single { PostApi(androidContext()) }
        single { providePostDao(androidContext(), CoroutineScope(Dispatchers.IO)) }
        single { provideApiService(get()) }
        single { PostRepository(postDao = get(), postApiService = get()) }
        single { CommentsRepository(postApiService = get()) }
        single { UserRepository(postApiService = get()) }
        single { AlbumRepository(albumApiService = get()) }
        single {PhotoRepository(photoApiService = get())}
        viewModel { PostViewModel(postRepository = get(), userRepository = get(), commentsRepository = get()) }
        viewModel { CommentsListViewModel(commentsRepository = get(), postRepository = get()) }
        viewModel { UserViewModel(userRepository = get()) }


        viewModel { AlbumViewModel(albumRepository = get(), userRepository = get()) }
        viewModel { PhotoListViewModel(photosRepository = get(), albumRepository = get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOfModules)
        }
    }
    private fun provideApiService(api: PostApi) : PostApiService{
        return api.getPostApiService()
    }

    private fun providePostDao(context: Context, scope: CoroutineScope): PostDao {
        return PostDatabase.getPostDatabase(context, scope).PostDao()
    }
}