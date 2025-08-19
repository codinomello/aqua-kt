package com.codinomello.aqua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codinomello.aqua.ui.theme.AquaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AquaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Guarulhos Water Bodies") }
                        )
                    }
                ) { innerPadding ->
                    WaterBodiesScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WaterBodiesScreen(modifier: Modifier = Modifier, viewModel: WaterViewModel = hiltViewModel()) {
    val weatherState = viewModel.weatherState.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Water Bodies in Guarulhos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Weather Information
        when (weatherState) {
            is WeatherState.Loading -> {
                CircularProgressIndicator()
            }
            is WeatherState.Success -> {
                val weather = weatherState.weather
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Weather in Guarulhos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text("Temperature: ${weather.main.temp}°C")
                        Text("Humidity: ${weather.main.humidity}%")
                        Text("Precipitation: ${weather.rain?.oneHour?.let { "$it mm" } ?: "None"}")
                        Text("Description: ${weather.weather[0].description}")
                    }
                }
            }
            is WeatherState.Error -> {
                Text(
                    text = "Error: ${weatherState.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // Water Bodies List
        LazyColumn {
            items(getWaterBodies()) { waterBody ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = waterBody.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(text = waterBody.description)
                    }
                }
            }
        }
    }
}

// Hardcoded list of water bodies in Guarulhos
data class WaterBody(val name: String, val description: String)

fun getWaterBodies(): List<WaterBody> {
    return listOf(
        WaterBody(
            name = "Rio Baquirivu-Guaçu",
            description = "A major river in Guarulhos, contributing to the city's water supply and ecosystem."
        ),
        WaterBody(
            name = "Lago dos Patos",
            description = "A lake in the Vila Galvão neighborhood, used for recreation and local biodiversity."
        ),
        WaterBody(
            name = "Córrego Cocho Velho",
            description = "A stream that flows through urban areas, often affected by local rainfall."
        ),
        WaterBody(
            name = "Reservatório Paiva Castro",
            description = "A key reservoir supplying water to Guarulhos and surrounding areas."
        )
    )
}