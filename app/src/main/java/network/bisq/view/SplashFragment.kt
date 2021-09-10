package network.bisq.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import network.bisq.R
import network.bisq.base.BaseFragment
import network.bisq.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentSplashBinding.inflate(inflater, container, false).apply {
        binding = this
        root.postDelayed({
            root.findNavController()
                .navigate(R.id.action_splashFragment_to_currencyFragment)
        }, 2000L)
    }.root

}