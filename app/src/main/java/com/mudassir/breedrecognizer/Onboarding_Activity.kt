package com.mudassir.breedrecognizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mudassir.breedrecognizer.databinding.ActivityOnboardingBinding

class Onboarding_Activity : AppCompatActivity() {
    private  var binding: ActivityOnboardingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()

        val fragments = arrayListOf<Fragment>(
            OnboardingScreen01Fragment.newInstance(),
            OnboardingScreen02_Fragment.newInstance(),
            OnboardingScreen03_Fragment.newInstance()
        )

        val adapter = ViewPagerAdapter(fragments, this.supportFragmentManager, lifecycle)

        binding?.viewPager?.adapter = adapter

    }
}