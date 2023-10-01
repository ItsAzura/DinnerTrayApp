package com.example.dinnertrayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dinnertrayapp.ui.theme.DinnerTrayAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DinnerTrayAppTheme {
                DinnerApp()
            }
        }
    }
}

