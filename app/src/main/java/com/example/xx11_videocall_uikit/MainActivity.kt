package com.example.xx11_videocall_uikit

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.agora.agorauikit_android.*
import io.agora.rtc2.Constants.CLIENT_ROLE_BROADCASTER


class MainActivity : AppCompatActivity() {

    // Object of AgoraVideoVIewer class
    private var agView: AgoraVideoViewer? = null

    // Fill the App ID of your project generated on Agora Console.
    private val appId = "c5c559e17aaf4667bc3a64cbd543c6cd"

    // Fill the channel name.
    private val channelName = "Dhaavik-Kingdom"

    // Fill the temp token generated on Agora Console.
    private val token =
        "007eJxTYFjSLd76PLhSQE1btsygfnrVocYdLL/Z/onNihCWkVd6wKfAkGyabGpqmWponpiYZmJmZp6UbJxoZpKclGJqYpxslpyyWZMnrSGQkUE96wsLIwMEgvj8DC4ZiYllmdm63pl56Sn5uQwMALjPIGU="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initializeAndJoinChannel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun initializeAndJoinChannel() {
        // Create AgoraVideoViewer instance
        try {
            agView = AgoraVideoViewer(
                this,
                AgoraConnectionData(appId, token),
                AgoraVideoViewer.Style.FLOATING,
                AgoraSettings(),
                null
            )
        } catch (e: Exception) {
            Log.e(
                "AgoraVideoViewer",
                "Could not initialize AgoraVideoViewer. Check that your app Id is valid."
            )
            Log.e("Exception", e.toString())
            return
        }

        // Add the AgoraVideoViewer to the Activity layout
        addContentView(
            agView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        // Check permission and join a channel
        // if (DevicePermissionsKt.requestPermissions(AgoraVideoViewer.Companion, this)) {
        joinChannel()
        // } else {
        val joinButton = Button(this)
        joinButton.text = "Allow camera and microphone access, then click here"
        joinButton.setOnClickListener {
            // if (DevicePermissionsKt.requestPermissions(
            //         AgoraVideoViewer.Companion,
            //         applicationContext
            //     )
            // ) {
            (joinButton.parent as ViewGroup).removeView(joinButton)
            joinChannel()
            //}
            addContentView(
                joinButton,
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 200)
            )
        }
    }

    fun joinChannel() {
        agView?.join(channelName, token, CLIENT_ROLE_BROADCASTER, 0)
    }
}