package com.example.sleeptracker.SleepTracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
        return binding.root
    }
}