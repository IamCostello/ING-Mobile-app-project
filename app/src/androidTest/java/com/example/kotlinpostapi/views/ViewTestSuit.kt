package com.example.kotlinpostapi.views

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AlbumListTest::class,
    CommentsListTest::class,
    PostListTest::class,
    UserInfoTest::class
)
class ViewTestSuit