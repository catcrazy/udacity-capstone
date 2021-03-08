package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionsRepository
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(val repository: ElectionsRepository): ViewModel() {

    //TODO: Create live data val for upcoming elections
    val upcomingElections = repository.upcomingElections

    //TODO: Create live data val for saved elections
    val savedElections = repository.savedElections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    private val _navigateToVoterInfo = MutableLiveData<Election>()
    val navigateToVoterInfo: LiveData<Election>
        get() = _navigateToVoterInfo

    init {
        refreshElections()
    }
    private fun refreshElections() {
        viewModelScope.launch {
            try {
                repository.refreshElections()
            } catch (e: Exception) {
                Log.e("election view model","refreshElections: ${e.localizedMessage}")
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun navigateToVoterInfoUpcoming(election: Election) {
        _navigateToVoterInfo.value = election
    }
    fun navigateToVoterInfoSaved() {
        _navigateToVoterInfo.value = null
    }
}