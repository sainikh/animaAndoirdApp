package com.example.jikan.ui.components

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.example.jikan.R
import com.example.jikan.ui.theme.JikanTheme
import com.example.jikan.ui.theme.MediumFont
import com.example.jikan.ui.theme.SmallFont
import com.example.jikan.ui.theme.TittleFont
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun AnimeDetailScreen(
    videoUrl: String?,
    imageUrl: String,
    tittle: String,
    plot: String,
    generaList: List<String>,
    episodes: Int,
    rating: String
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .verticalScroll(state = scrollState)
    ) {
        if (videoUrl != null) {
            VideoPlayer(videoUrl, lifecycleOwner = LocalLifecycleOwner.current)
        } else {
            PosterView(imageUrl)
        }

        Tittle(tittle)
        Plot(plot)
        Genera(generaList)
        Episodes(episodes)
        Rating(rating)
    }
}

@Composable
fun PosterView(imageUrl: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "poster",
            modifier = Modifier.clip(RoundedCornerShape(5.dp)),
        )
    }

}

@Composable
fun Tittle(tittle: String) {
    Row(Modifier.padding(10.dp)) {
        TittleFont(tittle)
    }
}

@Composable
fun Plot(plot: String) {
    Row(Modifier.padding(10.dp)) {
        MediumFont(plot)
    }
}

@Composable
fun VideoPlayer(url: String, lifecycleOwner: LifecycleOwner) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            factory = { context ->
                YouTubePlayerView(context = context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(url, 0f)
                        }
                    })

                }
            },
        )

    }


    Spacer(modifier = Modifier.height(10.dp))
}


@Composable
fun Episodes(episodes: Int) {
    Row(Modifier.padding(10.dp)) {
        MediumFont("Episodes : ")
        SmallFont(episodes.toString())
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun Rating(rating: String) {
    Row(Modifier.padding(10.dp)) {
        MediumFont("Rating : ")
        SmallFont(rating)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Genera(list: List<String>) {
    Row(Modifier.padding(10.dp)) { MediumFont("Genera") }
    FlowRow(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        list.forEach { label ->
            SuggestionChip({}, { SmallFont(label) })
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}


@Preview
@Composable
private fun AnimeDetailScreenPreview() {
    JikanTheme {
        AnimeDetailScreen(
            "",
            "",
            "Initial D Fourth Stage",
            Resources.getSystem().getString(R.string.demo_synopsis),
            arrayListOf("Adventure", "Drama", "Fantasy"),
            30,
            "PG-13 - Teens 13 or older"
        )
    }
}