package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.basicscodelab.sootheapp.theme.MySootheTheme
import com.example.basicscodelab.animation.ui.AnimationCodelabTheme
import com.example.basicscodelab.animation.ui.home.Home
import com.example.basicscodelab.basicstatecodelab.WellnessScreen
import com.example.basicscodelab.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp(
    modifier: Modifier = Modifier
) {
    //rememberSaveable is used to save the state even after any configuration change
    var appOptions by rememberSaveable { mutableStateOf(0) }
    when(appOptions) {
        0 ->  Column(
            modifier = modifier,
            // arranges in the main axis(y-axis)
            verticalArrangement = Arrangement.Center,
            // aligns the components in the other axis(x-axis)
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = { appOptions = 1 }
            ) {
                Text("First App")
            }
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = { appOptions = 2 }
            ) {
                Text("Second App")
            }
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = { appOptions = 3 }
            ) {
                Text("Third App")
            }
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = { appOptions = 4 }
            ) {
                Text("Animation App")
            }
        }

        1 -> BasicsCodelabTheme {
            // First composable
            MyApp(Modifier.fillMaxSize())
        }

        2 -> MySootheTheme {
            Scaffold(bottomBar = { SootheBottomNavigation() }) {
                HomeScreen(Modifier.padding(it))
            }
        }
        3 -> BasicStateCodelabTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                WellnessScreen()
            }
        }
        4 -> AnimationCodelabTheme {
            Home()
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