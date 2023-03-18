package com.example.sleeptracker.SleepQuality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sleeptracker.R
import com.example.sleeptracker.databinding.FragmentSleepQualityBinding

class SleepQualityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSleepQualityBinding>(inflater, R.layout.fragment_sleep_quality,container,false)
        val application = requireNotNull(this.activity).application
        return binding.root
    }

}