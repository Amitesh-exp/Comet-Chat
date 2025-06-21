package com.example.cometchattabbedui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private val appID = "27768220d0777574" // Replace with your App ID
    private val region = "in" // Replace with your App Region
    private val authKey = "d88c4db654e22ade932383ed10141fa341d2dc4d" // Replace with your Auth Key or leave blank if you are authenticating using Auth Token

    private val uiKitSettings = UIKitSettings.UIKitSettingsBuilder()
        .setRegion(region)
        .setAppId(appID)
        .setAuthKey(authKey)
        .subscribePresenceForAllUsers()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CometChatUIKit.init(this, uiKitSettings, object : CometChat.CallbackListener<String?>() {
            override fun onSuccess(successString: String?) {

                Log.d(TAG, "Initialization completed successfully")

                loginUser()
            }

            override fun onError(e: CometChatException?) {}
        })
    }

    private fun loginUser() {
        CometChatUIKit.login("cometchat-uid-1", object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {

                // Launch Tab-Based Chat Experience (Chats, Calls, Users, Groups)
                startActivity(Intent(this@MainActivity, TabbedActivity::class.java))
            }

            override fun onError(e: CometChatException) {
                // Handle login failure (e.g. show error message or retry)
                Log.e("Login", "Login failed: ${e.message}")
            }
        })
    }
}