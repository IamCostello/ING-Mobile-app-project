package com.example.kotlinpostapi

import android.app.Application
import com.example.kotlinpostapi.Albums.AlbumViewModel
import com.example.kotlinpostapi.Comments.CommentsListViewModel
import com.example.kotlinpostapi.Posts.PostViewModel
import com.example.kotlinpostapi.Users.UserViewModel
import com.example.kotlinpostapi.network.PostApi
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.repository.AlbumRepository
import com.example.kotlinpostapi.repository.CommentsRepository
import com.example.kotlinpostapi.repository.PostRepository
import com.example.kotlinpostapi.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    private var listOfModules = module {
        single { PostApi(androidContext()) }
        single { provideApiService(get()) }
        single { PostRepository(postApiService = get()) }
        single { CommentsRepository(postApiService = get()) }
        single { UserRepository(postApiService = get()) }
        single { AlbumRepository(albumApiService = get()) }
        viewModel { PostViewModel(postRepository = get()) }
        viewModel { CommentsListViewModel(commentsRepository = get(), postRepository = get()) }
        viewModel { UserViewModel(userRepository = get()) }


        viewModel { AlbumViewModel(albumRepository = get(), userRepository = get()) }
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
}