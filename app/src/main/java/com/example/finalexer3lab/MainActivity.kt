package com.example.finalexer3lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalexer3lab.ui.theme.FinalExer3LabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalExer3LabTheme {
                CravingApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CravingApp() {
    val containerColor = Color(0xFFF7F8FA)
    val appRed = Color(0xFFF95A5F)
    val priceGreen = Color(0xFF00B175)
    val textGray = Color(0xFF5A5A5A)
    val textColor = Color(0xFF1B1B1B)
    val cyanColor = Color(0xFF00C4B4)
    val redLight = Color(0xFFFFE5E5)
    val inputBg = Color(0xFFF9F9F9)
    
    var currentMainTab by remember { mutableStateOf("Profile") }
    var currentAppScreen by remember { mutableStateOf("Login") } 

    if (currentAppScreen == "Login" || currentAppScreen == "SignUp") {
        AuthScreen(
            isLogin = currentAppScreen == "Login",
            onToggleMode = { currentAppScreen = if (currentAppScreen == "Login") "SignUp" else "Login" },
            onAuthSuccess = { currentAppScreen = "Main" },
            appRed = appRed,
            containerColor = containerColor,
            textColor = textColor,
            textGray = textGray,
            inputBg = inputBg
        )
    } else {
        MainScreen(
            containerColor = containerColor,
            appRed = appRed,
            priceGreen = priceGreen,
            textGray = textGray,
            textColor = textColor,
            cyanColor = cyanColor,
            redLight = redLight,
            currentTab = currentMainTab,
            onTabSelected = { currentMainTab = it }
        )
    }
}

@Composable
fun AuthScreen(
    isLogin: Boolean,
    onToggleMode: () -> Unit,
    onAuthSuccess: () -> Unit,
    appRed: Color,
    containerColor: Color,
    textColor: Color,
    textGray: Color,
    inputBg: Color
) {
    Scaffold(
        containerColor = containerColor
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isLogin) {
                    // Top Bar for Sign Up (outside the card)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Restaurant,
                                contentDescription = "Logo",
                                tint = appRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "BiteBox",
                                color = appRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        IconButton(onClick = onToggleMode, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = textColor)
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    if (isLogin) {
                        LoginView(onToggleMode, onAuthSuccess, appRed, textColor, textGray, inputBg)
                    } else {
                        SignUpView(onToggleMode, onAuthSuccess, appRed, textColor, textGray, inputBg)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    onToggleMode: () -> Unit,
    onAuthSuccess: () -> Unit,
    appRed: Color,
    textColor: Color,
    textGray: Color,
    inputBg: Color
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = appRed,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Restaurant,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Welcome Back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Sign in to continue to BiteBox",
            fontSize = 14.sp,
            color = textGray
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Email Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Email Address", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("your@email.com", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null, tint = textGray) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    unfocusedBorderColor = Color(0xFFE0E0E0), focusedBorderColor = appRed
                ),
                singleLine = true
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Password Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Password", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
                Text("Forgot Password?", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = appRed, modifier = Modifier.clickable { })
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("••••••••", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = textGray) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    unfocusedBorderColor = Color(0xFFE0E0E0), focusedBorderColor = appRed
                ),
                singleLine = true
            )
        }
        
        // Remember Me Checkbox
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                modifier = Modifier.size(24.dp).padding(end = 8.dp),
                colors = CheckboxDefaults.colors(checkedColor = appRed, uncheckedColor = Color(0xFFC0C0C0))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Remember me", fontSize = 12.sp, color = textGray)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onAuthSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = appRed)
        ) {
            Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row {
            Text(text = "Don't have an account? ", fontSize = 12.sp, color = textGray)
            Text(
                text = "Sign Up",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = appRed,
                modifier = Modifier.clickable { onToggleMode() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView(
    onToggleMode: () -> Unit,
    onAuthSuccess: () -> Unit,
    appRed: Color,
    textColor: Color,
    textGray: Color,
    inputBg: Color
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Join BiteBox to discover and save your\nfavorite meals.",
            fontSize = 12.sp,
            color = textGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Full Name Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Full Name", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("John Doe", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    unfocusedBorderColor = Color(0xFFE0E0E0), focusedBorderColor = appRed
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Email Address", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("you@example.com", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    unfocusedBorderColor = Color(0xFFE0E0E0), focusedBorderColor = appRed
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Password", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("••••••••", color = Color.Gray, fontSize = 14.sp) },
                trailingIcon = { Icon(Icons.Outlined.VisibilityOff, contentDescription = null, tint = textGray, modifier = Modifier.size(20.dp)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    unfocusedBorderColor = Color(0xFFE0E0E0), focusedBorderColor = appRed
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Confirm Password", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("••••••••", color = Color.Gray, fontSize = 14.sp) },
                trailingIcon = { Icon(Icons.Outlined.VisibilityOff, contentDescription = null, tint = textGray, modifier = Modifier.size(20.dp)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = inputBg, unfocusedContainerColor = inputBg,
                    unfocusedBorderColor = Color(0xFFE0E0E0), focusedBorderColor = appRed
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Terms Checkbox
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreed,
                onCheckedChange = { agreed = it },
                modifier = Modifier.size(24.dp).padding(end = 8.dp),
                colors = CheckboxDefaults.colors(checkedColor = appRed, uncheckedColor = Color(0xFFC0C0C0))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "I agree to the ", fontSize = 12.sp, color = textGray)
            Text(text = "Terms & Conditions", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = appRed, modifier = Modifier.clickable{})
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAuthSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = appRed)
        ) {
            Text(text = "Sign Up", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(text = "Already have an account? ", fontSize = 12.sp, color = textGray)
            Text(
                text = "Log In",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = appRed,
                modifier = Modifier.clickable { onToggleMode() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    containerColor: Color,
    appRed: Color,
    priceGreen: Color,
    textGray: Color,
    textColor: Color,
    cyanColor: Color,
    redLight: Color,
    currentTab: String,
    onTabSelected: (String) -> Unit
) {
    Scaffold(
        containerColor = containerColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "BiteBox",
                        color = appRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = "Logo",
                            tint = appRed,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Profile",
                            tint = textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = containerColor
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavItem(icon = Icons.Outlined.Home, label = "Home", isSelected = currentTab == "Home", activeColor = appRed) { onTabSelected("Home") }
                BottomNavItem(icon = Icons.Outlined.Receipt, label = "Orders", isSelected = currentTab == "Orders", activeColor = appRed) { onTabSelected("Orders") }
                BottomNavItem(icon = Icons.Outlined.BookmarkBorder, label = "Saved", isSelected = currentTab == "Saved", activeColor = appRed) { onTabSelected("Saved") }
                BottomNavItem(icon = Icons.Outlined.Person, label = "Profile", isSelected = currentTab == "Profile", activeColor = appRed) { onTabSelected("Profile") }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            when (currentTab) {
                "Saved" -> {
                    SavedScreen(textColor, textGray, priceGreen, cyanColor, appRed)
                }
                "Profile" -> {
                    ProfileScreen(textColor, textGray, appRed)
                }
                "Orders" -> {
                    OrdersScreen(textColor, textGray, priceGreen, appRed, redLight)
                }
                else -> {
                    // Home
                    Text("Select Orders or Profile", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(textColor: Color, textGray: Color, appRed: Color) {
    Spacer(modifier = Modifier.height(24.dp))

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFE0E0E0),
                modifier = Modifier.size(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = "Avatar",
                    modifier = Modifier.padding(20.dp),
                    tint = Color.Gray
                )
                // If you had an actual image, you would use Image here instead
            }
            Surface(
                shape = CircleShape,
                color = appRed,
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = (-4).dp, y = (-4).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color.White,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Alex Doe",
        fontSize = 28.sp,
        fontWeight = FontWeight.Black,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = "alex.doe@example.com",
        fontSize = 14.sp,
        color = textGray,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(32.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileMenuItem(icon = Icons.Outlined.PersonOutline, label = "Personal Info", showDivider = true)
            ProfileMenuItem(icon = Icons.Outlined.CreditCard, label = "Payment Methods", showDivider = true)
            ProfileMenuItem(icon = Icons.Outlined.LocationOn, label = "Delivery Addresses", showDivider = true)
            ProfileMenuItem(icon = Icons.Outlined.Notifications, label = "Notifications", showDivider = true)
            ProfileMenuItem(icon = Icons.Outlined.ExitToApp, label = "Logout", isDestructive = true, showDivider = false)
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun ProfileMenuItem(icon: ImageVector, label: String, isDestructive: Boolean = false, showDivider: Boolean = true) {
    val color = if (isDestructive) Color(0xFFA1132A) else Color(0xFF1B1B1B)
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = color,
                modifier = Modifier.weight(1f)
            )
            if (!isDestructive) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Arrow Right",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        if (showDivider) {
            Divider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(horizontal = 20.dp), thickness = 1.dp)
        }
    }
}

@Composable
fun OrdersScreen(textColor: Color, textGray: Color, priceGreen: Color, appRed: Color, redLight: Color) {
    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "My Orders",
        fontSize = 24.sp,
        fontWeight = FontWeight.Black,
        color = textColor,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Track your active orders and view your order\nhistory.",
        fontSize = 14.sp,
        color = textGray,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = "Active Orders",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = textColor
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.burger),
                contentDescription = "Burger",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Classic Smash Burger\nMeal",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$18.50",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = priceGreen
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "1x Classic Smash Burger, 1x Truffle Fries, 1x Craft\nCola",
                fontSize = 12.sp,
                color = textGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(text = "Order #BBX-892", fontSize = 11.sp, color = textGray)
                    Text(text = "Today, 12:45 PM", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = textColor)
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = redLight
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestaurantMenu,
                            contentDescription = "Preparing",
                            tint = appRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Preparing",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = appRed
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Past Orders",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = textColor
    )

    PastOrderCard(
        imageRes = R.drawable.pasta, // Using pasta since seafood/sushi might not be available exactly, wait actually the screenshot shows sushi, using pasta as substitute if needed, wait we only have the prev specific images
        title = "Deluxe Sushi Combo",
        price = "$32.00",
        items = "1x Spicy Tuna Roll, 1x Salmon Nigiri Set",
        date = "Oct 12, 2023",
        priceGreen = priceGreen,
        textColor = textColor,
        textGray = textGray
    )

    PastOrderCard(
        imageRes = R.drawable.pizza,
        title = "Artisan Margherita Pizza",
        price = "$24.00",
        items = "1x Large Margherita, 1x Garlic Knots",
        date = "Oct 05, 2023",
        priceGreen = priceGreen,
        textColor = textColor,
        textGray = textGray
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun PastOrderCard(
    imageRes: Int, title: String, price: String, items: String, date: String,
    priceGreen: Color, textColor: Color, textGray: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = priceGreen
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = items,
                fontSize = 12.sp,
                color = textGray
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = date, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Delivered",
                        tint = priceGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Delivered",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = priceGreen
                    )
                }
            }
        }
    }
}

@Composable
fun SavedScreen(textColor: Color, textGray: Color, priceGreen: Color, cyanColor: Color, appRed: Color) {
    Spacer(modifier = Modifier.height(24.dp))

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFE0E0E0),
                modifier = Modifier.size(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = "Avatar",
                    modifier = Modifier.padding(20.dp),
                    tint = Color.Gray
                )
                // If you had an actual image, you would use Image here instead
            }
            Surface(
                shape = CircleShape,
                color = appRed,
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = (-4).dp, y = (-4).dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = "Add to Favorites",
                    tint = Color.White,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Your Favorites",
        fontSize = 32.sp,
        fontWeight = FontWeight.Black,
        color = textColor,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Quick access to the meals you love.",
        fontSize = 14.sp,
        color = textGray,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(24.dp))

    FavoriteItemCard(
        imageRes = R.drawable.burger,
        category = "Burgers",
        title = "Classic Double Smash",
        description = "Two seared beef patties, American cheese, house sauce, pickles, toasted potato roll.",
        price = "$14.88",
        priceGreen = priceGreen,
        cyanColor = cyanColor,
        appRed = appRed
    )

    FavoriteItemCard(
        imageRes = R.drawable.pizza,
        category = "Pizza",
        title = "Artisan Pepperoni",
        description = "San Marzano tomato sauce, fresh mozzarella, spicy cup-and-char pepperoni, hot honey drizzle.",
        price = "$22.50",
        priceGreen = priceGreen,
        cyanColor = cyanColor,
        appRed = appRed
    )

    FavoriteItemCard(
        imageRes = R.drawable.pasta,
        category = "Japanese",
        title = "Omakase Signature Roll",
        description = "Spicy tuna, cucumber, topped with torched salmon, spicy mayo, and scallions. 8 pieces.",
        price = "$34.00",
        priceGreen = priceGreen,
        cyanColor = cyanColor,
        appRed = appRed
    )

    FavoriteItemCard(
        imageRes = R.drawable.fries,
        category = "Healthy",
        title = "Green Goddess Harvest",
        description = "Kale, quinoa, roasted sweet potato, avocado, toasted pepitas, signature green goddess...",
        price = "$16.60",
        priceGreen = priceGreen,
        cyanColor = cyanColor,
        appRed = appRed
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun FavoriteItemCard(
    imageRes: Int, 
    category: String, 
    title: String, 
    description: String, 
    price: String, 
    priceGreen: Color,
    cyanColor: Color,
    appRed: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = appRed,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text(
                        text = price,
                        color = priceGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
            
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(cyanColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color(0xFF888888)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF1B1B1B)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = Color(0xFF5A5A5A),
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1B1B1B))
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Add to Order",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add to Order",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, label: String, isSelected: Boolean, activeColor: Color, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .let {
                if (isSelected) {
                    it
                        .clip(CircleShape)
                        .background(activeColor)
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                } else {
                    it.padding(horizontal = 20.dp, vertical = 8.dp)
                }
            }
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(24.dp).padding(0.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) Color.White else Color(0xFF5A5A5A),
                modifier = Modifier.size(24.dp)
            )
        }
        if (isSelected) {
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )
        } else {
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color(0xFF5A5A5A),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
