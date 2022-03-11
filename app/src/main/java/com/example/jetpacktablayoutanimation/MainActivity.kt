package com.example.jetpacktablayoutanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpacktablayoutanimation.ui.theme.JetPackTablayoutAnimationTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackTablayoutAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background) {
                    Column{
                        TopAppBar(
                            title = {
                                Text(
                                    text = " MacTronics Tab Layout Animation",
                                color = Color.White,
                                    modifier = Modifier.padding(start = 2.dp, top = 2.dp, bottom = 2.dp, end = 2.dp),
                                    textAlign = TextAlign.Center
                                )
                            },
                            backgroundColor = Color.Black
                        )

                        TabLayoutAnimation()
                        }
                    }
                }
            }
        }
    }

enum class TapPage{
 Accept, InProgress, Complete
}
val list = listOf("Accept", "InProgress","Complete")

@ExperimentalPagerApi
@Composable
fun TabLayoutAnimation(){

    val pagerState = rememberPagerState(pageCount =3)

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {

        Tabs(pagerState=pagerState)
        TabsContent(pagerState=pagerState)
    }
    
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState:PagerState){

    val scope = rememberCoroutineScope()

    val backgroundColor by animateColorAsState(
        when(pagerState.currentPage){
            0 -> Color.Green
            1 -> Color.Red
            else -> Color.Black
        }
    )
     TabRow(
         selectedTabIndex = pagerState.currentPage,
     backgroundColor = backgroundColor,
     contentColor = Color.White,
     indicator = { tabPositions ->
     TablayoutIndicator(tapPosition = tabPositions,
         tapPage =
             when (pagerState.currentPage){
                 0 -> TapPage.Accept
                 1 -> TapPage.InProgress
                 else -> TapPage.Complete
             }
          )

     }
     ) {
         list.forEachIndexed { index, s -> 
             Tab(selected =pagerState.currentPage==index ,
                 onClick = {
                     scope.launch {
                         pagerState.animateScrollToPage(index)
                     }

                 },
                 text = {
                     Text(
                         text =list[index],
                     color = if(pagerState.currentPage== index) Color.White else Color.White.copy(alpha = 0.5f),
                     fontWeight = if (pagerState.currentPage== index) FontWeight.Bold else FontWeight.Normal
                         )
                 }
             )

         }

     }
}
@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState){
HorizontalPager(state = pagerState) {
page -> 
when(page){
    0 -> TabScreen(
        data = "Accept TabLayout" ,
        image = R.drawable.dogs,
        color = Color.Green
    )
    1 -> TabScreen(
        data = "InProgress TabLayout" ,
        image = R.drawable.dogs,
        color = Color.Red
    )
    2 -> TabScreen(
        data = "Complete TabLayout" ,
        image = R.drawable.dogs,
        color = Color.Black
    )
}    
}
}

