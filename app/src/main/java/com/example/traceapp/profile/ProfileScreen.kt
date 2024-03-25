package com.example.traceapp.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.traceapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    val backgroundImagePainter = painterResource(id = R.drawable.profile_background)

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    IconButton(
                        onClick = {
                            navController.navigate("dashboard")
                        }) {
                        Icon(Icons.Filled.Home, "dashboard")
                    }
                    IconButton(
                        onClick = {
                            navController.navigate("login")
                        }) {
                        Icon(Icons.Filled.ExitToApp, "Login")
                    }
                }

            }
        }
    ) {innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = backgroundImagePainter,
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column() {
                    ProfileHeader()
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileDetail("Name", "Amanda Andrade")
                    ProfileDetail("Birthday", "1995 - 07 - 04")
                    ProfileDetail("Email", "amanda.andrade@hotmail.com")
                    ProfileDetail("Password", "********", isPassword = true)

                    Spacer(modifier = Modifier.height(50.dp))

                    OutlinedButton(
                        onClick = {
                            // Handle login logic
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 70.dp, end = 70.dp),
                        border = BorderStroke(2.dp, Color(0xFF292147)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF292147),
                            containerColor = Color(0xFFF8F8F8)
                        )
                    ) {
                        Text("Edit Profile")
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }

    }
}

@Composable
fun ProfileHeader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Amanda Andrade",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                RoundedProfileImage()
            }
        }
    }
}


@Composable
fun RoundedProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.cat_profile),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(80.dp)
            .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
            .clip(CircleShape)
            .background(Color.LightGray, shape = CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ProfileDetail(label: String, value: String, isPassword: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 15.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                modifier = Modifier.weight(1f)
            )
            if (isPassword) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Edit",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
//    ProfileScreen()
}
