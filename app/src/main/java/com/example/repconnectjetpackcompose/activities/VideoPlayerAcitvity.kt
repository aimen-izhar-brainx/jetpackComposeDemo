package com.example.repconnectjetpackcompose.activities

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import com.example.repconnectjetpackcompose.viewModel.RepertoireViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

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
        val exoPlayer = remember {
            SimpleExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
            }
        }
        val isPlaying = remember { mutableStateOf(false) }

        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = {
                if (isPlaying.value) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
                isPlaying.value = !isPlaying.value
            },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (isPlaying.value) Icons.Filled.Phone else Icons.Filled.PlayArrow,
                contentDescription = if (isPlaying.value) "Pause" else "Play",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}