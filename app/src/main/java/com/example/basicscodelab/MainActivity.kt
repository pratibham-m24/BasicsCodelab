package com.example.basicscodelab

import android.app.Activity
import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.basicscodelab.crane.details.launchDetailsActivity
import androidx.compose.samples.crane.home.CraneHome
import androidx.compose.samples.crane.home.LandingScreen
import androidx.compose.samples.crane.home.OnExploreItemClicked
import androidx.compose.samples.crane.ui.CraneTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.example.basicscodelab.sootheapp.theme.MySootheTheme
import com.example.basicscodelab.animation.ui.AnimationCodelabTheme
import com.example.basicscodelab.animation.ui.home.Home
import com.example.basicscodelab.basicstatecodelab.WellnessScreen
import com.example.basicscodelab.basicstatecodelab.ui.theme.BasicStateCodelabTheme
import com.example.basicscodelab.reply.data.local.LocalEmailsDataProvider
import com.example.basicscodelab.reply.ui.ReplyApp
import com.example.basicscodelab.reply.ui.ReplyHomeUIState
import com.example.basicscodelab.reply.ui.ReplyHomeViewModel
import com.example.basicscodelab.reply.ui.theme.ReplyTheme
import com.example.basicscodelab.reply.ui.utils.DevicePosture
import com.example.basicscodelab.reply.ui.utils.isBookPosture
import com.example.basicscodelab.reply.ui.utils.isSeparating
import com.example.basicscodelab.wearos.WearApp
import com.example.compose.rally.Bills
import com.example.compose.rally.Overview
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.jetnews.data.AppContainerImpl
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ReplyHomeViewModel by viewModels()

        val devicePostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    //check if it is booking posture or seperating posture
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(
                            foldingFeature.bounds,
                            foldingFeature.orientation
                        )

                    else -> DevicePosture.NormalPosture
                }
            }
            //convert into stateflow
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )
        setContent {
            MainApp(
                context = this,
                devicePostureFlow = devicePostureFlow,
                uiState = viewModel.uiState.collectAsState().value
            )
        }
    }
}

val appNames = listOf(
    "First App",
    "Second App",
    "Third App",
    "Animation App",
    "Crane App",
    "Rally App",
    "JetNews App",
    "Reply App",
    "Wear App"
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    devicePostureFlow: StateFlow<DevicePosture>,
    uiState: ReplyHomeUIState,
    context: Context
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Launch App",
        modifier = modifier
    ) {
        composable(route ="Launch App") {
            LazyColumn(
                modifier = modifier,
                // arranges in the main axis(y-axis)
                verticalArrangement = Arrangement.Center,
                // aligns the components in the other axis(x-axis)
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(appNames) { index, name ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize(),
                        onClick = { navController.navigateSingleTopTo(appNames[index])
                        },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(name, modifier = Modifier.padding(16.dp))
                    }
                }

            }
        }
        composable(route = "First App") {
            BasicsCodelabTheme {
                // First composable
                MyApp(Modifier.fillMaxSize())
            }
        }


        composable(route = "Second App") {
            MySootheTheme {
                Scaffold(bottomBar = { SootheBottomNavigation() }) {
                    HomeScreen(Modifier.padding(it))
                }
            }
        }

        composable(route = "Third App") {
            BasicStateCodelabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WellnessScreen()
                }
            }
        }

        composable(route = "Animation App") {
            AnimationCodelabTheme {
                Home()
            }
        }

        composable(route = "Crane App") {
            CraneTheme {
                MainScreen(onExploreItemClicked = {
                    launchDetailsActivity(
                        context = context,
                        item = it
                    )
                })
            }
        }

        composable(route = "Rally App") { RallyApp() }
        composable(route = "JetNews App") {
            JetnewsApp(AppContainerImpl(context))
        }

        composable(route = "Reply App") { // get the folding posture using WindowLayoutFlow
            ReplyTheme {
                // get the windowsize
                val windowSize = calculateWindowSizeClass(context as Activity)
                // get the device posture in state
                val devicePosture = devicePostureFlow.collectAsState().value
                ReplyApp(uiState, windowSize.widthSizeClass, devicePosture)
            }
        }

        composable(route = "Wear App") {
            WearApp()
        }
    }

}

/*
This composable stores a state called shouldShowOnboarding. Any change in the
value of this state results in recomposition.
 */
@Composable
fun MyApp(
    modifier: Modifier = Modifier
) {
    //rememberSaveable is used to save the state even after any configuration change
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        /* This decides if onBoarding screen needs to be shown or the Greetings.
        At first onboarding screen is shown and once the button is clicked, Greeting screen is shown
        */
        if (shouldShowOnboarding) {
            // We pass the callback to the composable to achieve state hoisting.
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false }, modifier)
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    /* LazyColumn is used to show very long list. It only creates the listitem which is visible to the user, once
    scrolled, other list items are created.
    */
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        //this iterates through the list
        items(names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        CardItem(name)
    }
}

@Composable
fun CardItem(name: String) {
    // this is used to save if the listitem is expanded or not.
    var expanded by rememberSaveable { mutableStateOf(false) }

    // This is used to show component adjacent to each other
    Row(
        Modifier
            .padding(12.dp)
            // this shows animation and gradually changes the size
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier.weight(1.0F)) {
            Text(
                text = "Hello,"
            )
            Text(
                text = name,
                // changing the text style to headlineMedium and extrabold
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            // show additional text when expanded
            if (expanded) {
                Text(text = stringResource(R.string.composem_ipsum))
            }
        }
        // onClick toggles the expanded value
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) stringResource(
                    R.string.show_less
                ) else stringResource(R.string.show_more)
            )
        }

    }
}

// Preview for the Greetings
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
    // shows components one after the other
    Column(
        modifier = modifier,
        // arranges in the main axis(y-axis)
        verticalArrangement = Arrangement.Center,
        // aligns the components in the other axis(x-axis)
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the BasicsCodelab")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Composable
private fun MainScreen(onExploreItemClicked: OnExploreItemClicked) {
    androidx.compose.material.Surface(color = androidx.compose.material.MaterialTheme.colors.primary) {
        var showLandingScreen by remember { mutableStateOf(true) }
        if (showLandingScreen) {
            LandingScreen(onTimeout = { showLandingScreen = false })
        } else {
            CraneHome(onExploreItemClicked = onExploreItemClicked)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyAppPreview() {
    ReplyTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Compact,
            foldingDevicePosture = DevicePosture.NormalPosture,
        )
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun ReplyAppPreviewTablet() {
    ReplyTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Medium,
            foldingDevicePosture = DevicePosture.NormalPosture,
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ReplyAppPreviewDesktop() {
    ReplyTheme {
        ReplyApp(
            replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            windowSize = WindowWidthSizeClass.Expanded,
            foldingDevicePosture = DevicePosture.NormalPosture,
        )
    }
}