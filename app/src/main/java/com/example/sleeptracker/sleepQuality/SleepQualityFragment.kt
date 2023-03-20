package com.example.sleeptracker.sleepQuality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sleeptracker.Database.SleepDatabase
import com.example.sleeptracker.R
import com.example.sleeptracker.databinding.FragmentSleepQualityBinding

class SleepQualityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<com.example.sleeptracker.databinding.FragmentSleepQualityBinding>(inflater, R.layout.fragment_sleep_quality,container,false)
        val application = requireNotNull(this.activity).application

        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments()!!)
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey,dataSource)
        val sleepQualityViewModelpie = ViewModelProviders.of(this,viewModelFactory).get(SleepQualityViewModel::class.java)
        binding.sleepQualityViewModel = sleepQualityViewModelpie
        return binding.root
    }

}