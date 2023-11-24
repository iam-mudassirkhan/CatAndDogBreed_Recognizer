package com.mudassir.breedrecognizer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mudassir.breedrecognizer.databinding.FragmentOnboardingScreen03Binding

private var binding: FragmentOnboardingScreen03Binding? = null
class OnboardingScreen03_Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnboardingScreen03Binding.inflate(inflater, container, false)

        return binding?.root
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_onboarding_screen03_, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Save data
        val sharedPrefs = SharedPreferencesManager.getInstance(requireContext())
        //sharedPrefs.putString("username", "JohnDoe")


        binding?.icArrowBtn?.setOnClickListener {
           val intent = Intent(requireContext(), MainActivity::class.java)
            requireContext().startActivity(intent)
            requireActivity().finish()
            sharedPrefs.putBoolean("isLoggedIn", true)

        }

    }

    companion object {
        fun newInstance(): OnboardingScreen03_Fragment {
            return OnboardingScreen03_Fragment()
        }
    }
}