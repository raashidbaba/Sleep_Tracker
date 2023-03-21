package com.example.sleeptracker.sleepTracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.sleeptracker.Database.SleepDatabase
import com.example.sleeptracker.R
import com.example.sleeptracker.databinding.FragmnetSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

class SleepTrackerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmnetSleepTrackerBinding>(
            inflater,
            R.layout.fragmnet_sleep_tracker,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.sleepTrackerViewModel = sleepTrackerViewModel
        binding.setLifecycleOwner(this)

        sleepTrackerViewModel.navigationToSleepQuality.observe(
            viewLifecycleOwner,
            Observer { night ->
                night?.let {
                view?.let { it1 ->
                    Navigation.findNavController(it1.rootView).navigate(
                        SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(
                            night.nightId
                        )
                    )
                }
                    sleepTrackerViewModel.doneNavigating()
                }
            })
        sleepTrackerViewModel.showSnackBarEvent.observe(this, Observer {
            if (it==true){
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                getString(R.string.cleared_message),
                Snackbar.LENGTH_SHORT).show()
                sleepTrackerViewModel.doneShowingSnackBar()
            }
        })






        return binding.root
    }
}
