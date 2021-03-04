package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertElection(election: Election)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table")
    fun getElections(): LiveData<List<Election>>

    //TODO: Add select single election query
    @Query("SELECT * FROM election_table WHERE id = :id")
    fun getElectionById(id: Int): Election

    //TODO: Add delete query
    @Delete
    fun deleteElection(election: Election)

    //TODO: Add clear query

}