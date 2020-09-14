package com.jet2tt.assignment.di

import com.jet2tt.assignment.ui.article.ArticlesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeArticleActivity(): ArticlesActivity
}