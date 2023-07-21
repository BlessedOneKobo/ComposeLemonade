package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

enum class Screen { TREE, SQUEEZE, DRINK, RESTART }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    var content by remember { mutableStateOf(Screen.TREE) }
    var remainingTouches: Int by remember { mutableStateOf(0) }

    val imageId = when (content) {
        Screen.TREE -> R.drawable.tree
        Screen.SQUEEZE -> R.drawable.squeeze
        Screen.DRINK -> R.drawable.drink
        Screen.RESTART -> R.drawable.restart
    }
    val descriptionId = when (content) {
        Screen.TREE -> R.string.tree_description
        Screen.SQUEEZE -> R.string.squeeze_description
        Screen.DRINK -> R.string.drink_description
        Screen.RESTART -> R.string.restart_description
    }
    val messageId = when (content) {
        Screen.TREE -> R.string.tree_message
        Screen.SQUEEZE -> R.string.squeeze_message
        Screen.DRINK -> R.string.drink_message
        Screen.RESTART -> R.string.restart_message
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTitle()
        ScreenContent(
            imageId = imageId,
            imageDescription = stringResource(id = descriptionId),
            message = stringResource(id = messageId),
        ) {
            content = when (content) {
                Screen.RESTART -> Screen.TREE
                Screen.DRINK -> Screen.RESTART
                Screen.TREE -> {
                    remainingTouches = (2..4).random()
                    Screen.SQUEEZE
                }

                else -> {
                    if (remainingTouches == 1) {
                        Screen.DRINK
                    } else {
                        remainingTouches--
                        Screen.SQUEEZE
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenContent(
    imageId: Int,
    imageDescription: String,
    message: String,
    handleImageClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(size = 20.dp),
            color = Color(0xFFC3ECD2),
            modifier = Modifier
                .size(200.dp)
                .clickable { handleImageClick() }
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = imageDescription,
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ScreenTitle() {
    Text(
        text = "Lemonade",
        modifier = Modifier
            .background(Color(0xFFF9E44C))
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeApp()
}