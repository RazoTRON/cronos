package com.example.data.auth

import android.content.Context
import android.provider.Settings
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils

class HashPasswordUseCase(val context: Context) {
    fun invoke(password: String): String {
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val hashedPasswordBytes = DigestUtils.sha256(password + androidId)
        val hashedPassword = Hex.encodeHexString(hashedPasswordBytes)
        return hashedPassword
    }
}