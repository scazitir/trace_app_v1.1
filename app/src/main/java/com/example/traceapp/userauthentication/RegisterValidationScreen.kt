import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.traceapp.R
import kotlinx.coroutines.delay

@Composable
fun RegisterValidationScreen(
    navController: NavController
) {
    val backgroundImage = painterResource(id = R.drawable.register_background)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Displaying success message
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your account has been successfully created!",
                color = Color(0xFF292147),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )
        }
    }

    // Navigating after a delay
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("login") {
            // Remove all previous routes from the backstack
            popUpTo("register") { inclusive = true }
        }
    }
}

