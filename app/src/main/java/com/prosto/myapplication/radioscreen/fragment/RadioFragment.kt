package com.prosto.myapplication.radioscreen.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.prosto.myapplication.R
import com.prosto.myapplication.core.ui.BaseFragment
import com.prosto.myapplication.core.utils.applyBlurEffect
import com.prosto.myapplication.core.utils.clearBlurEffect
import com.prosto.myapplication.databinding.FragmentRadioBinding
import com.prosto.myapplication.radioscreen.domain.radio.models.RadioState
import com.prosto.myapplication.radioscreen.viewmodel.RadioViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RadioFragment :
    BaseFragment<FragmentRadioBinding, RadioViewModel>(FragmentRadioBinding::inflate) {
    override val viewModel by activityViewModels<RadioViewModel>()

    override fun initViews() {
    }

    override fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.radioState.collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: RadioState) = with(binding) {
        when (state) {
            is RadioState.Content -> {
                tvSongTitle.text = state.songEntity.title
            }

            is RadioState.ConnectionError -> {
                tvSongTitle.text = requireContext().getString(R.string.internet_connection_error)
            }

            is RadioState.Loading -> {
                tvSongTitle.text = requireContext().getString(R.string.loading)
            }

            is RadioState.Empty -> {
                tvSongTitle.text = ""
            }
        }
    }

    private fun applyBackgroundBlur() = with(binding) {
        ivPlaypause.applyBlurEffect()
    }

    private fun clearBackgroundBlur() = with(binding) {
        ivPlaypause.clearBlurEffect()
    }
}