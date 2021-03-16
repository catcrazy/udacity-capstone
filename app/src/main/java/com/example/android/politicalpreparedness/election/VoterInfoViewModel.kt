package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class VoterInfoViewModel( private val repository: VoterInfoRepository, val election: Election) : ViewModel() {

    //TODO: Add live data to hold voter info
    val state = repository.state


    //TODO: Add var and methods to populate voter info
    val savedElection = repository.savedElection
    private var _electionInfoUrl = MutableLiveData<String>()
    val electionInfoUrl: LiveData<String>
        get() = _electionInfoUrl

    private var _ballotInfoUrl = MutableLiveData<String>()
    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl


    //TODO: Add var and methods to support loading URLs
    private var _votingLocationFinderUrl = MutableLiveData<String>()
    val votingLocationFinderUrl: LiveData<String>
        get() = _votingLocationFinderUrl

    init {
        getVoterInfo()
    }
    private fun getVoterInfo() {
        viewModelScope.launch {
            try {
                val dummyAddress = "Modesto"
                repository.refreshVoterInfo(dummyAddress, election.id)
                repository.getElectionById(election.id)
            } catch (e: Exception) {
                // TODO show toast
                Log.e("voter info fragment view model","getVoterInfo.exception: ${e.localizedMessage}")
            }
        }
    }

    fun openBallotInfoUrl() {
        _ballotInfoUrl.value = state.value?.electionAdministrationBody?.ballotInfoUrl
    }

    fun openBallotInfoUrlDone() {
        _ballotInfoUrl.value = null
    }

    fun openVotingLocationFinderUrl() {
        _votingLocationFinderUrl.value = state.value?.electionAdministrationBody?.votingLocationFinderUrl
    }

    fun openVotingLocationFinderUrlDone() {
        _votingLocationFinderUrl.value = null
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */
    fun openElectionInfoUrlDone() {
        _electionInfoUrl.value = null
    }
    fun openElectionInfoUrl() {
        _electionInfoUrl.value = state.value?.electionAdministrationBody?.electionInfoUrl
    }

    fun savingButton() {
        viewModelScope.launch {
            if (savedElection.value == null) {
                repository.saveElection(election)
                savedElection.value = election
            } else {
                repository.deleteElection(election)
                savedElection.value = null
            }
        }
    }
}