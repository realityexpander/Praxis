package com.praxis.feat.authentication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.praxis.navigator.NavigationKeys
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val navigator: Navigator
) : ViewModel() {

  var password = MutableStateFlow("")
    private set
  var email = MutableStateFlow("")
    private set
  var passwordResetFlow = MutableStateFlow("")
    private set

  init {
    observePasswordReset()
  }

  private fun observePasswordReset() {
    navigator.observeResult<String>(NavigationKeys.ForgotPassword).onStart {
      val message = savedStateHandle.get<String>(NavigationKeys.ForgotPassword)
      message?.let {
        emit(it)
      }
    }
      .onEach { passwordResetFlow.value = it }
      .launchIn(viewModelScope)
  }

  fun navigateDashboard() {
    navigator.navigate(Screen.Jokes.route)
  }

  fun navigateForgotPassword() {
    navigator.navigate(Screen.ForgotPassword.route)
  }
}