package com.example.jikan

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jikan.ui.components.AnimeDetailScreen
import com.example.jikan.ui.components.AnimeListCard
import com.example.jikan.ui.theme.JikanTheme
import com.example.jikan.ui.viewmodel.AnimeViewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JikanTheme {
                val model = AnimeViewModel()
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Anime") },
                        Modifier.background(Color.Black)
                    )
                }) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        App(model)
                    }
                }
            }
        }
    }
}


sealed class Routes(val route: String) {
    object home : Routes("Home")
    object detail : Routes("Detail")
}

@Composable
fun App(model: AnimeViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.home.route
    ) {
        composable(route = Routes.home.route) {
            HomeScreen(navController, model)
        }

        composable(route = Routes.detail.route + "/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt()
            Detail(model, id!!)
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun HomeScreen(navController: NavHostController, model: AnimeViewModel) {

    val animeList by model.animeList.collectAsState()
    val isLoading by model.isLoading.collectAsState()
    val errorMessage by model.errorMessage.collectAsState()
    val context = LocalContext.current

    if (isLoading) {
        LoadingScreen()
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceEvenly,

            ) {
            items(animeList.size) { index ->
                val anime = animeList[index]
                Box(modifier = Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
                    AnimeListCard(
                        anime.title,
                        anime.episodes,
                        anime.rating,
                        anime.images.jpg.large_image_url,
                        {
                            navController.navigate(Routes.detail.route + "/${anime.id}")
                        }
                    )

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        model.fetchTopAnime()
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            model.clearErrorMessage()
        }
    }


}

@Composable
fun Detail(model: AnimeViewModel, id: Int) {
    val detail by model.animeDetail.collectAsState()
    val isLoading by model.isLoading.collectAsState()
    val errorMessage by model.errorMessage.collectAsState()
    val context = LocalContext.current

    if (isLoading) {
        LoadingScreen()
    } else {
        detail?.let { anime ->
            val genres = anime.genres.map { it -> it.name }
            AnimeDetailScreen(
                anime.trailer.youtube_id,
                anime.images.jpg.large_image_url,
                anime.title,
                anime.synopsis,
                genres,
                anime.episodes,
                anime.rating
            )
        } ?: run {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No Internet/ No data Found")
            }
        }
    }

    LaunchedEffect(Unit) {
        model.fetchAnimeDetails(id)
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            model.clearErrorMessage()
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JikanTheme {
        HomeScreen(rememberNavController(), AnimeViewModel())
    }
}