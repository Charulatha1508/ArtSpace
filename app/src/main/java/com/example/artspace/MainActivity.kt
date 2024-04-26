package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(){
    var pageNumber by remember {
        mutableStateOf(1)
    }
    val previousClick: () -> Unit = {
        pageNumber--
    }
    val nextClick: () -> Unit = {
        pageNumber++
    }


    when (pageNumber) {
        1 -> {
            ArtSpaceLayout(
                R.drawable.artspace1,
                R.string.first_art_description,
                R.string.first_art_name,
                { },
                nextClick,
                previousEnable = false,
                previousButtonColor = Color.DarkGray,
                modifier = Modifier.fillMaxSize()
            )
        }

        2 -> {
            ArtSpaceLayout(
                R.drawable.artspace3,
                R.string.second_art_description,
                R.string.second_art_name,
                previousClick,
                nextClick,
                modifier = Modifier.fillMaxSize()
            )
        }

        3 -> {
            ArtSpaceLayout(
                R.drawable.artspace4,
                R.string.third_art_description,
                R.string.third_art_name,
                previousClick,
                nextClick,
                modifier = Modifier.fillMaxSize()
            )
        }

        4 -> {
            ArtSpaceLayout(
                R.drawable.artspace5,
                R.string.fourth_art_description,
                R.string.fourth_art_name,
                previousClick,
                { },
                nextEnable = false,
                nextButtonColor = Color.DarkGray,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtSpaceLayout(
    @DrawableRes drawableId: Int,
    @StringRes stringId: Int,
    @StringRes nameId: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    previousEnable: Boolean = true,
    nextEnable: Boolean = true,
    previousButtonColor: Color = Color.LightGray,
    nextButtonColor: Color = Color.LightGray,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
            .background(color = Color.Black)
            .padding(start = 20.dp, end = 20.dp, top = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.artspacetopbar1),
                contentDescription = null,
                Modifier.alpha(0.5f)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.LineThrough
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        if (dragAmount < 0) onNextClick()
                        else onPreviousClick()
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlainTooltipBox(
                tooltip = {
                    Text(
                        stringResource(id = stringId),
                        fontFamily = FontFamily.Default,
                        textAlign = TextAlign.Center,
                        modifier = Modifier. fillMaxWidth()
                    )
                },
                containerColor = Color.Black,
                contentColor = Color.LightGray,
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(0.6f)
                        .tooltipAnchor()
                )
            }

            Text(
                text = stringResource(id = nameId),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            OutlinedButton(
                onClick = onPreviousClick,
                enabled = previousEnable
            )
            {
                Text(
                    text = stringResource(id = R.string.previous_button),
                    color = previousButtonColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = onNextClick,
                enabled = nextEnable
            )
            {
                Text(
                    text = stringResource(id = R.string.next_button),
                    color = nextButtonColor
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}