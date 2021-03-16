package com.example.android.politicalpreparedness.representative

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel: ViewModel() {

    //TODO: Establish live data for representatives and address
    private val _representativeList = MutableLiveData<MutableList<Representative>>()
    val representativeList: LiveData<MutableList<Representative>>
        get() = _representativeList


    //TODO: Create function to fetch representatives from API from a provided address
    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    init {
        _address.value = Address("California","","","","")
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location
    fun onUseMyLocationClicked(retrievedAddress: Address) {
        _address.value = retrievedAddress
        getRepresentatives(retrievedAddress.toFormattedString())
    }

    //TODO: Create function to get address from individual fields
    fun onSearchMyRepresentativesClicked() {
        _address.value?.let {
            getRepresentatives(_address.value!!.toFormattedString())
        }
    }

    private fun getRepresentatives(addressStr: String) {
        Log.i("representative view model", "get representatives" + addressStr)
        viewModelScope.launch {
            val representativeResponse = CivicsApi.retrofitService.getRepresentatives(addressStr)
            val offices = representativeResponse.offices
            val officials = representativeResponse.officials

            Log.i("representative view model", representativeResponse.toString())

            Log.i("representativeviewmodel", "getRepresentatives.election: $offices}")
            Log.i("representativeviewmodel", "getRepresentatives.election: $officials}")

            val representatives = mutableListOf<Representative>()
            offices.forEach { office ->
                representatives.addAll(office.getRepresentatives(officials))
                _representativeList.value = representatives
            }
        }
    }
}
