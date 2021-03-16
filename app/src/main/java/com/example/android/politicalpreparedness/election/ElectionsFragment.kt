package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.database.ElectionsRepository
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel
        val database = ElectionDatabase.getInstance(requireActivity().application)
        val repository = ElectionsRepository(database)
        val viewModelFactory = ElectionsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ElectionsViewModel::class.java)


        //TODO: Add binding values
        val binding = FragmentElectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        //TODO: Link elections to voter info
//        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer { election ->
//            election?.let {
//                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
//                viewModel.navigateToVoterInfoSaved()
//            }
//        })

        //TODO: Initiate recycler adapters
        val upcomingElectionListAdapter = ElectionListAdapter(ElectionListener { election ->
            viewModel.navigateToVoterInfoUpcoming(election)
        })

        //TODO: Populate recycler adapters
        binding.upcomingElectionList.adapter = upcomingElectionListAdapter
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer { electionList ->
            electionList?.let {
                upcomingElectionListAdapter.submitList(electionList)
            }
        })

        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer { election ->
            election?.let {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election, election.division))
                viewModel.navigateToVoterInfoSaved()
            }
        })

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}