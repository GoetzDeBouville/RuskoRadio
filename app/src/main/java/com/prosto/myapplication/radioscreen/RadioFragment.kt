package com.prosto.myapplication.radioscreen

import androidx.fragment.app.activityViewModels
import com.prosto.myapplication.core.ui.BaseFragment
import com.prosto.myapplication.core.utils.applyBlurEffect
import com.prosto.myapplication.core.utils.clearBlurEffect
import com.prosto.myapplication.databinding.FragmentRadioBinding
import com.prosto.myapplication.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RadioFragment :
    BaseFragment<FragmentRadioBinding, RadioViewModel>(FragmentRadioBinding::inflate) {
    override val viewModel by activityViewModels<RadioViewModel>()

    override fun initViews() {
        applyBackgroundBlur()
    }

    override fun subscribe() {
    }

    private fun applyBackgroundBlur() = with(binding) {
        ivLogo.applyBlurEffect()
        ivPlaypause.applyBlurEffect()
        llPlayer.applyBlurEffect()
        llFooter.applyBlurEffect()
        (requireActivity() as MainActivity).applyBlurEffect()
    }

    private fun clearBackgroundBlur() = with(binding) {
        ivLogo.clearBlurEffect()
        llPlayer.clearBlurEffect()
        ivPlaypause.clearBlurEffect()
        llFooter.clearBlurEffect()
        (requireActivity() as MainActivity).clearBlurEffect()
    }
}