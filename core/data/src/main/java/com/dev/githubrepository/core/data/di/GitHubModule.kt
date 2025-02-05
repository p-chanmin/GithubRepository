package com.dev.githubrepository.core.data.di

import com.dev.githubrepository.core.data.GitHubRepositoryImpl
import com.dev.githubrepository.core.data_api.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GitHubModule {

    @Binds
    abstract fun bindGitHubRepository(
        gitHubRepository: GitHubRepositoryImpl,
    ): GitHubRepository
}