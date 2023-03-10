package com.example.graphqlsample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.apollographqlsample.CharacterQuery
import com.example.apollographqlsample.CharactersListQuery
import com.example.graphqlsample.repository.CharacterRepository
import com.example.graphqlsample.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

//Add Inject constructor pass the constructor an instance of CharacterRepository
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _charactersList by lazy { MutableLiveData<ViewState<Response<CharactersListQuery.Data>>>() }
    private val _character by lazy { MutableLiveData<ViewState<Response<CharacterQuery.Data>>>() }

    val charactersList: LiveData<ViewState<Response<CharactersListQuery.Data>>>
        get() = _charactersList

    val character: LiveData<ViewState<Response<CharacterQuery.Data>>>
        get() = _character

    fun queryCharactersList() = viewModelScope.launch {
        _charactersList.postValue(ViewState.Loading())
        try {
            val response = repository.queryCharactersList()
            _charactersList.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _charactersList.postValue(ViewState.Error("Error fetching characters"))
        }
    }

    fun queryCharacter(id: String) = viewModelScope.launch {
        _character.postValue(ViewState.Loading())
        try {
            val response = repository.queryCharacter(id)
            _character.postValue(ViewState.Success(response))
        } catch (ae: ApolloException) {
            Log.d("ApolloException", "Failure", ae)
            _character.postValue(ViewState.Error("Error fetching characters"))
        }
    }

}