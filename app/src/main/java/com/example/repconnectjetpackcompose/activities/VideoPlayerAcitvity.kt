package com.example.repconnectjetpackcompose.activities

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.delay

class VideoPlayerAcitvity : ComponentActivity() {
    var videoUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()

        setContent {
            VideoPlayer(videoUri = Uri.parse(videoUrl))
        }
    }

    private fun getIntentData() {
        videoUrl = intent.getStringExtra("url")
    }

    @Composable
    fun VideoPlayer(videoUri: Uri) {
        val context = LocalContext.current

        // Create ExoPlayer instance
        val exoPlayer = remember {
            SimpleExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
            }
        }

        // State to manage play/pause
        var isPlaying by remember { mutableStateOf(false) }

        // State to track video playback position in milliseconds
        var videoPosition by remember { mutableStateOf(0L) }

        // State to track the seek bar position (in percentage)
        var seekBarPosition by remember { mutableStateOf(0f) }

        // Update the seek bar position when video playback changes
        LaunchedEffect(exoPlayer) {
            while (true) {
                val currentPosition = exoPlayer.currentPosition
                val duration = exoPlayer.duration
                if (duration > 0) {
                    videoPosition = currentPosition
                    seekBarPosition = currentPosition.toFloat() / duration
                }
                delay(100) // Update every 100ms
            }
        }

        // Set the player to play/pause based on the isPlaying state
        LaunchedEffect(isPlaying) {
            if (isPlaying) {
                exoPlayer.play()
            } else {
                exoPlayer.pause()
            }
        }

        // Composable elements for the video player and controls
        Column() {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                    }
                },

            )

            Row(
                modifier = Modifier
                    .padding(start = 4.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                // Your custom controls (e.g., play/pause button)
                IconButton(
                    onClick = { isPlaying = !isPlaying }
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.ThumbUp else Icons.Filled.PlayArrow,
                        contentDescription = "Play/Pause"
                    )
                }

                // Seek bar with custom layout for time display
                Slider(
                    value = seekBarPosition,
                    onValueChange = { value ->
                        seekBarPosition = value
                        val duration = exoPlayer.duration
                        if (duration > 0) {
                            val newPosition = (value * duration).toLong()
                            exoPlayer.seekTo(newPosition)
                        }
                    },
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color.Red,
                        thumbColor = Color.Red,
                        inactiveTrackColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        }

        // Hide system UI for immersive video playback
        val windowInsets = LocalContext.current
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsController =
                window.insetsController
            insetsController?.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_DEFAULT
        } else {
        }
    }




}