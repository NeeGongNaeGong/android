package com.ssafy.neegongnaegong.presentation.base

import androidx.lifecycle.ViewModel
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SnackbarViewModel @Inject constructor(
    snackbarManager: SnackbarManager
) : ViewModel() {
    val message = snackbarManager.message
}
