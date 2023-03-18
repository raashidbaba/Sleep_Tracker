package com.example.sleeptracker.SleepTracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.example.sleeptracker.Database.SleepDatabase
import com.example.sleeptracker.Database.SleepDatabaseDao
import com.example.sleeptracker.R
import com.example.sleeptracker.databinding.FragmentSleepQualityBinding
import com.example.sleeptracker.databinding.FragmnetSleepTrackerBinding

class SleepTrackerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmnetSleepTrackerBinding>(inflater, R.layout.fragmnet_sleep_tracker,container,false)
      val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)
        val sleepTrackerViewModel = ViewModelProviders.of(this,viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.sleepTrackerViewModel  = sleepTrackerViewModel
        binding.setLifecycleOwner(this)

       /* sleepTrackerViewModel.navigationToSleepQuality.observe(this, Observer {
            night ->
            night?.let {
                this.findNavController().navigate(
                  SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(night.nightId)}
            sleepTrackerViewModel
                )
            }
        })*/
        return binding.root
    }
}