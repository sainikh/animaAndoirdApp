package com.example.jikan.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.jikan.R
import com.example.jikan.ui.theme.JikanTheme
import com.example.jikan.ui.theme.MediumFont

@Composable
fun AnimeListCard(
    title: String,
    episodeCount: Int,
    rating: String,
    image: String,
    onclick: () -> Unit
) {

    Card(
        modifier = Modifier
            .background(Color.Black)
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(5.dp)
            )
            .clickable { onclick() },
        colors = CardColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White,

            )
    ) {

        Row(modifier = Modifier.padding(10.dp)) {

            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(contentAlignment = Alignment.BottomStart) {

                    AsyncImage(
                        model = image,
                        contentDescription = "poster",
                        modifier = Modifier.clip(RoundedCornerShape(5.dp))
                    )

                    Text(
                        text = "| $rating",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.Black.copy(alpha = 0.2f),

                            )
                            .padding(5.dp),
                        color = Color.White,
                        fontSize = 10.sp,
                    )

                }

                Column {

                    Text(
                        text = title,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.episodes),
                            contentDescription = "Image",
                            modifier = Modifier.size(15.dp),
                            colorFilter = ColorFilter.tint(Color.White, blendMode = BlendMode.SrcIn)
                        )

                        MediumFont(episodeCount.toString())
                        MediumFont("episodes")
                    }


                }

            }


        }

    }

}

@Composable
fun AnimePreview() {
    JikanTheme {
        AnimeListCard(
            "Sousou no Frieren",
            28,
            "PG-13",
            "https://cdn.myanimelist.net/images/anime/1015/138006.jpg",
            {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AnimeListPagePreview() {
    JikanTheme {
        LazyVerticalGrid(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceEvenly,

            ) {
            items(100) {

                Box(modifier = Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
                    AnimeListCard(
                        "Fullmetal Alchemist Brotherhood",
                        28,
                        "PG-13",
                        "https://cdn.myanimelist.net/images/anime/1015/138006.jpg",
                        {}
                    )
                }
            }
        }
    }
}



