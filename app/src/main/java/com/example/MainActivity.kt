package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ui.navigation.JarvisNavGraph
import com.example.ui.theme.JarvisAgentTheme
import com.example.ui.theme.JarvisDarkBackground

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      JarvisAgentTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = JarvisDarkBackground
        ) {
          JarvisNavGraph()
        }
      }
    }
  }
}
