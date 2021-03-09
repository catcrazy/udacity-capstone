package com.example.android.politicalpreparedness.election

import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VoterInfoRepository(private val database: ElectionDatabase) {
    val state = MutableLiveData<State>()
    val savedElection = MutableLiveData<Election>()

    suspend fun refreshVoterInfo(address: String, election_id: Int) {
        // TODO update by commenting out
//        withContext(Dispatchers.IO) {
//            val voterResponse = CivicsApi.retrofitService.getVoterInfo(address, electionId)
//            state.postValue(voterResponse.state?.get(0))
//        }
    }

    suspend fun getElectionById(election_id: Int) {
        withContext(Dispatchers.IO) {
            savedElection.postValue(database.electionDao.getElectionById(election_id))
        }
    }

    suspend fun saveElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.insertElection(election)
        }
    }

    suspend fun deleteElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.deleteElection(election)
        }
    }

}