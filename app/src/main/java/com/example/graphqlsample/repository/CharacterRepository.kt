package com.example.graphqlsample.repository

import com.apollographql.apollo.api.Response
import com.example.apollographqlsample.CharacterQuery
import com.example.apollographqlsample.CharactersListQuery

interface CharacterRepository {

    suspend fun queryCharactersList(): Response<CharactersListQuery.Data>

    suspend fun queryCharacter(id: String): Response<CharacterQuery.Data>

}