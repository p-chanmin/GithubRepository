package com.dev.githubrepository.core.data

import app.cash.turbine.test
import com.dev.githubrepository.core.data.api.model.RepositoryResponse
import com.dev.githubrepository.core.data.api.model.SearchRepositoryResponse
import com.dev.githubrepository.core.data.api.model.UserResponse
import com.dev.githubrepository.core.data.fake.FakeGitHubApi
import com.dev.githubrepository.core.model.RepositoryInfo
import com.dev.githubrepository.core.model.UserInfo
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class GitHubRepositoryImplTest : StringSpec() {

    private val repository = GitHubRepositoryImpl(
        FakeGitHubApi(
            listOf(searchRepositoryResponsePage1, searchRepositoryResponsePage2),
            repositoryDetails,
            userRepositories,
            userInfo
        )
    )

    init {
        "레포지토리 검색 테스트" {
            val keyword = "android"
            val perPage = 2
            val expected = listOf(
                RepositoryInfo(
                    id = 1,
                    name = "repository1",
                    language = "kotlin",
                    stargazersCount = 3123,
                    watchersCount = 3234,
                    forksCount = 854,
                    description = "test description",
                    topics = listOf("android, kotlin, java"),
                    owner = "user1",
                    avatarUrl = "http://test.user1"
                ),
                RepositoryInfo(
                    id = 2,
                    name = "repository2",
                    language = "kotlin",
                    stargazersCount = 65423,
                    watchersCount = 7345,
                    forksCount = 2346,
                    description = "test description test",
                    topics = listOf("python"),
                    owner = "user2",
                    avatarUrl = "http://test.user2",
                ),
            )

            repository.searchRepositories(keyword = keyword, perPage = perPage).test {

                awaitItem() shouldBe expected

                repository.page shouldBe 1
                repository.endPage shouldBe false
                repository.cacheList.size shouldBe expected.size
                repository.cacheKeyword shouldBe keyword

                cancelAndConsumeRemainingEvents()
            }
        }

        "레포지토리 검색 캐시 테스트" {
            val keyword = "android"
            val perPage = 2
            val expected = listOf(
                RepositoryInfo(
                    id = 1,
                    name = "repository1",
                    language = "kotlin",
                    stargazersCount = 3123,
                    watchersCount = 3234,
                    forksCount = 854,
                    description = "test description",
                    topics = listOf("android, kotlin, java"),
                    owner = "user1",
                    avatarUrl = "http://test.user1"
                ),
                RepositoryInfo(
                    id = 2,
                    name = "repository2",
                    language = "kotlin",
                    stargazersCount = 65423,
                    watchersCount = 7345,
                    forksCount = 2346,
                    description = "test description test",
                    topics = listOf("python"),
                    owner = "user2",
                    avatarUrl = "http://test.user2",
                ),
                RepositoryInfo(
                    id = 3,
                    name = "repository3",
                    language = "java",
                    stargazersCount = 4573,
                    watchersCount = 653,
                    forksCount = 2346,
                    description = "test description test test",
                    topics = listOf("kotlin"),
                    owner = "user3",
                    avatarUrl = "http://test.user3",
                ),
                RepositoryInfo(
                    id = 4,
                    name = "repository4",
                    language = "java",
                    stargazersCount = 1234,
                    watchersCount = 2345,
                    forksCount = 7564,
                    description = "test description test test test",
                    topics = listOf("kotlin"),
                    owner = "user4",
                    avatarUrl = "http://test.user4",
                ),
            )

            repository.searchRepositories(keyword = keyword, perPage = perPage).test {

                awaitItem() shouldBe expected

                repository.page shouldBe 2
                repository.endPage shouldBe true
                repository.cacheList.size shouldBe expected.size
                repository.cacheKeyword shouldBe keyword

                cancelAndConsumeRemainingEvents()
            }
        }

        "캐시 클리어 테스트" {
            repository.cacheClear()
            repository.page shouldBe 1
            repository.endPage shouldBe false
            repository.cacheList.size shouldBe 0
            repository.cacheKeyword shouldBe ""
        }

        "레포지토리 상세정보 테스트" {
            val owner = "user1"
            val repo = "repository1"
            val expected = RepositoryInfo(
                id = 1,
                name = "repository1",
                language = "kotlin",
                stargazersCount = 3123,
                watchersCount = 3234,
                forksCount = 854,
                description = "test description",
                topics = listOf("android, kotlin, java"),
                owner = "user1",
                avatarUrl = "http://test.user1",
            )

            repository.getRepositoryDetails(owner = owner, repo = repo).test {
                awaitItem() shouldBe expected
                cancelAndConsumeRemainingEvents()
            }
        }

        "유저의 레포지토리 정보 테스트" {
            val username = "user1"
            val expected = listOf(
                RepositoryInfo(
                    id = 1,
                    name = "repository1",
                    language = "kotlin",
                    stargazersCount = 3123,
                    watchersCount = 3234,
                    forksCount = 854,
                    description = "test description",
                    topics = listOf("android, kotlin, java"),
                    owner = "user1",
                    avatarUrl = "http://test.user1",
                ),
                RepositoryInfo(
                    id = 1253,
                    name = "repository user1 A",
                    language = "kotlin",
                    stargazersCount = 1234,
                    watchersCount = 5123,
                    forksCount = 8564,
                    description = "A description",
                    topics = listOf("android"),
                    owner = "user1",
                    avatarUrl = "http://test.user1",
                )
            )

            repository.getUserRepositories(username = username).test {
                awaitItem() shouldBe expected
                cancelAndConsumeRemainingEvents()
            }
        }

        "유저 정보 테스트" {
            val username = "user1"
            val expected = UserInfo(
                username = "user1",
                avatarUrl = "http://test.user1",
                following = 842,
                followers = 647,
                publicRepos = 152,
                bio = "user1 bio"
            )

            repository.getUserInfo(username = username).test {
                awaitItem() shouldBe expected
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    companion object {
        private val searchRepositoryResponsePage1 = SearchRepositoryResponse(
            totalCount = 4,
            incompleteResults = false,
            items = listOf(
                RepositoryResponse(
                    id = 1,
                    name = "repository1",
                    language = "kotlin",
                    stargazersCount = 3123,
                    watchersCount = 3234,
                    forksCount = 854,
                    description = "test description",
                    topics = listOf("android, kotlin, java"),
                    owner = UserResponse(
                        login = "user1",
                        avatarUrl = "http://test.user1",
                        following = 842,
                        followers = 647,
                        publicRepos = 152,
                        bio = "user1 bio"
                    ),
                ),
                RepositoryResponse(
                    id = 2,
                    name = "repository2",
                    language = "kotlin",
                    stargazersCount = 65423,
                    watchersCount = 7345,
                    forksCount = 2346,
                    description = "test description test",
                    topics = listOf("python"),
                    owner = UserResponse(
                        login = "user2",
                        avatarUrl = "http://test.user2",
                        following = 1253,
                        followers = 6234,
                        publicRepos = 1235,
                        bio = "user2 bio"
                    ),
                )
            )
        )
        private val searchRepositoryResponsePage2 = SearchRepositoryResponse(
            totalCount = 4,
            incompleteResults = true,
            items = listOf(
                RepositoryResponse(
                    id = 3,
                    name = "repository3",
                    language = "java",
                    stargazersCount = 4573,
                    watchersCount = 653,
                    forksCount = 2346,
                    description = "test description test test",
                    topics = listOf("kotlin"),
                    owner = UserResponse(
                        login = "user3",
                        avatarUrl = "http://test.user3",
                        following = 2364,
                        followers = 1235,
                        publicRepos = 7453,
                        bio = "user3 bio"
                    ),
                ),
                RepositoryResponse(
                    id = 4,
                    name = "repository4",
                    language = "java",
                    stargazersCount = 1234,
                    watchersCount = 2345,
                    forksCount = 7564,
                    description = "test description test test test",
                    topics = listOf("kotlin"),
                    owner = UserResponse(
                        login = "user4",
                        avatarUrl = "http://test.user4",
                        following = 7453,
                        followers = 14623,
                        publicRepos = 42,
                        bio = "user4 bio"
                    ),
                )
            )
        )
        private val repositoryDetails = RepositoryResponse(
            id = 1,
            name = "repository1",
            language = "kotlin",
            stargazersCount = 3123,
            watchersCount = 3234,
            forksCount = 854,
            description = "test description",
            topics = listOf("android, kotlin, java"),
            owner = UserResponse(
                login = "user1",
                avatarUrl = "http://test.user1",
                following = 842,
                followers = 647,
                publicRepos = 152,
                bio = "user1 bio"
            ),
        )

        private val userRepositories = listOf(
            RepositoryResponse(
                id = 1,
                name = "repository1",
                language = "kotlin",
                stargazersCount = 3123,
                watchersCount = 3234,
                forksCount = 854,
                description = "test description",
                topics = listOf("android, kotlin, java"),
                owner = UserResponse(
                    login = "user1",
                    avatarUrl = "http://test.user1",
                    following = 842,
                    followers = 647,
                    publicRepos = 152,
                    bio = "user1 bio"
                ),
            ),
            RepositoryResponse(
                id = 1253,
                name = "repository user1 A",
                language = "kotlin",
                stargazersCount = 1234,
                watchersCount = 5123,
                forksCount = 8564,
                description = "A description",
                topics = listOf("android"),
                owner = UserResponse(
                    login = "user1",
                    avatarUrl = "http://test.user1",
                    following = 842,
                    followers = 647,
                    publicRepos = 152,
                    bio = "user1 bio"
                ),
            ),
        )
        private val userInfo = UserResponse(
            login = "user1",
            avatarUrl = "http://test.user1",
            following = 842,
            followers = 647,
            publicRepos = 152,
            bio = "user1 bio"
        )
    }
}