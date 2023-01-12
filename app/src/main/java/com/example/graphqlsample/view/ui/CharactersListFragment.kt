package com.example.graphqlsample.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.graphqlsample.databinding.FragmentCharactersListBinding
import com.example.graphqlsample.view.adapter.CharacterAdapter
import com.example.graphqlsample.view.state.ViewState
import com.example.graphqlsample.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersListFragment: Fragment() {

    //We need three properties one of FragmentCharactersListBinding,
    // one of CharacterAdapter and one of CharacterViewModel
    private lateinit var binding: FragmentCharactersListBinding
    private val characterAdapter by lazy { CharacterAdapter() }
    private val viewModel by viewModels<CharacterViewModel>()

    //Override the method onCreateView() and instantiate there
    // the binding field and return the root field of binding.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentCharactersListBinding.inflate(inflater)
        return binding.root
    }

    //Override the method onViewCreated()
    //in the body assign characterAdapter to the RecyclverView Adapter.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.charactersRv.adapter = characterAdapter
        viewModel.queryCharactersList()
        observeLiveData()
    }


    private fun observeLiveData() {
        viewModel.charactersList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    binding.charactersRv.visibility = View.GONE
                    binding.charactersFetchProgress.visibility = View.VISIBLE
                }
                is ViewState.Success -> {
                    if (response.value?.data?.characters?.results?.size == 0) {
                        characterAdapter.submitList(emptyList())
                        binding.charactersFetchProgress.visibility = View.GONE
                        binding.charactersRv.visibility = View.GONE
                        binding.charactersEmptyText.visibility = View.VISIBLE
                    } else {
                        binding.charactersRv.visibility = View.VISIBLE
                        binding.charactersEmptyText.visibility = View.GONE
                    }
                    val results = response.value?.data?.characters?.results
                    characterAdapter.submitList(results)
                    binding.charactersFetchProgress.visibility = View.GONE
                }
                is ViewState.Error -> {
                    characterAdapter.submitList(emptyList())
                    binding.charactersFetchProgress.visibility = View.GONE
                    binding.charactersRv.visibility = View.GONE
                    binding.charactersEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }


}