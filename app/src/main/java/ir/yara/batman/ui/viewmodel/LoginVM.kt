package ir.yara.batman.ui.viewmodel

import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import ir.yara.batman.R
import ir.yara.batman.network.api.NetworkApi


class LoginVM @ViewModelInject constructor(
    private val networkApi: NetworkApi,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun goToMovieFrag(view: View) {
        view.findNavController()
            .navigate(R.id.action_loginFragment_to_MovieFragment)
    }

    fun clear() = onCleared()

    override fun onCleared() {

    }

}