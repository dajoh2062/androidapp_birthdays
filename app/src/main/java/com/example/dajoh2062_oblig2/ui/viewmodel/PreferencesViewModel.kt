package com.example.dajoh2062_oblig2.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dajoh2062_oblig2.MyApp
import com.example.dajoh2062_oblig2.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PreferencesViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val KEY_SMS_ENABLED = "sms_enabled"
        private const val KEY_DEFAULT_MESSAGE = "default_message"
    }

    private val prefs: SharedPreferences =
        application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val _smsEnabled = MutableStateFlow(prefs.getBoolean(KEY_SMS_ENABLED, false))
    val smsEnabled: StateFlow<Boolean> get() = _smsEnabled

    private val _defaultMessage = MutableStateFlow(
        prefs.getString(
            KEY_DEFAULT_MESSAGE,
            application.getString(R.string.default_message) // ⬅️ fallback from strings.xml
        ) ?: application.getString(R.string.default_message)
    )
    val defaultMessage: StateFlow<String> get() = _defaultMessage

    fun setSmsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            prefs.edit { putBoolean(KEY_SMS_ENABLED, enabled) }
            _smsEnabled.value = enabled

            val app = getApplication<MyApp>()
            if (enabled) {
                app.scheduleDailyWork(app.applicationContext)
            } else {
                app.cancelDailyWork(app.applicationContext)
            }
        }
    }

    fun setDefaultMessage(message: String) {
        viewModelScope.launch {
            prefs.edit { putString(KEY_DEFAULT_MESSAGE, message) }
            _defaultMessage.value = message
        }
    }

    fun isSmsEnabled(): Boolean = _smsEnabled.value
    fun getDefaultMessage(): String = _defaultMessage.value
}
