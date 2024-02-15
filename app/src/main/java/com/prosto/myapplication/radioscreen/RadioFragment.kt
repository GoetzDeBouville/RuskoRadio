package com.prosto.myapplication.radioscreen

import androidx.fragment.app.activityViewModels
import com.prosto.myapplication.core.ui.BaseFragment
import com.prosto.myapplication.databinding.FragmentRadioBinding
import com.prosto.myapplication.main.MainActivity


class RadioFragment :
    BaseFragment<FragmentRadioBinding, RadioViewModel>(FragmentRadioBinding::inflate) {
    override val viewModel by activityViewModels<RadioViewModel>()

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }

    private fun applyBackgroundBlur() {
        (requireActivity() as MainActivity).applyBlurEffect()
    }

    private fun clearBackgroundBlur() {
        (requireActivity() as MainActivity).clearBlurEffect()
    }
}