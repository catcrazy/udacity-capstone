package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel
        val args = navArgs<VoterInfoFragmentArgs>()
        val election_id = args.value.argElectionId

        val database = ElectionDatabase.getInstance(requireActivity().application)
        val repository = VoterInfoRepository(database)

        val viewModelFactory = VoterInfoViewModelFactory(repository, election_id)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(VoterInfoViewModel::class.java)

        //TODO: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        viewModel.electionInfoUrl.observe(viewLifecycleOwner, Observer { urlStr ->
            urlStr?.let {
                startActivityWithUrlIntentUsing(urlStr)
                viewModel.openElectionInfoUrlDone()
            }
        })

        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    //TODO: Create method to load URL intents
    private fun startActivityWithUrlIntentUsing(urlStr: String) {
        val uri: Uri = Uri.parse(urlStr)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}