package com.example.graphqlsample.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.apollographqlsample.CharactersListQuery
import com.example.graphqlsample.networking.RickAndMortyApi
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val webService: RickAndMortyApi
): CharacterRepository {

    override suspend fun queryCharactersList(): Response<CharactersListQuery.Data> {
        return webService.getApolloClient().query(CharactersListQuery()).await()
    }

}