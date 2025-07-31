package com.example.test.dashboard

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.test.R
import com.example.test.multiple_lang.presentation.viewmodel.SignUpUiState
import com.example.test.multiple_lang.presentation.viewmodel.SignUpViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    var showForm by remember { mutableStateOf(false) }
    var isSignIn by remember { mutableStateOf(true) }

    var offsetY by remember { mutableStateOf(0f) }
    val dragThreshold = 200f

    val wavyFont = FontFamily(Font(R.font.wavy_font))
    var isLoading by remember { mutableStateOf(false) }

    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(signUpViewModel.uiState) {
        when (val state = signUpViewModel.uiState) {
            is SignUpUiState.Loading -> isLoading = true
            is SignUpUiState.Success -> {
                isLoading = false
                Toast.makeText(context, "Signup successfully! Please log in.", Toast.LENGTH_SHORT).show()
                isSignIn = true
            }
            is SignUpUiState.Error -> {
                isLoading = false
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dashboard_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        Text(
            "Travel One",
            fontFamily = wavyFont,
            fontSize = 44.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier
                .padding(top = 68.dp)
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Don't wait",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
            )

            Text(
                "Get best experience now!",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
            )
        }

        // Nút lựa chọn ban đầu (khi chưa mở form)
        if (!showForm) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        isSignIn = true
                        showForm = true
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF46DFB1)
                    )
                ) {
                    Text(
                        "Log in",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
                Spacer(Modifier.height(16.dp))
                OutlinedButton(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        isSignIn = false
                        showForm = true
                    },
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Text(
                        "Don't have an Account? Sign Up",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Reset offsetY mỗi khi mở form
        LaunchedEffect(showForm) {
            if (showForm) offsetY = 0f
        }

        // Form đăng nhập/đăng ký hiện lên
        AnimatedVisibility(
            visible = showForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(500)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(500)
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, offsetY.roundToInt()) }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dragAmount ->
                                if (dragAmount > 0) { // Chỉ xử lý khi vuốt xuống
                                    offsetY += dragAmount
                                }
                            },
                            onDragEnd = {
                                if (offsetY > dragThreshold) {
                                    showForm = false
                                } else {
                                    offsetY = 0f
                                }
                            }
                        )
                    }
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(
                        color = Color(0xFFBDD1C5)
                    )
                    .animateContentSize()
                    .padding(24.dp)
            ) {
                AnimatedContent(
                    targetState = isSignIn,
                    transitionSpec = {
                        if (targetState) {
                            // Từ SignUp → SignIn (trượt từ phải)
                            slideInHorizontally(
                                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                                initialOffsetX = { fullWidth -> fullWidth }
                            ) + fadeIn(animationSpec = tween(250)) with

                                    slideOutHorizontally(
                                        animationSpec = tween(250, easing = FastOutSlowInEasing),
                                        targetOffsetX = { fullWidth -> -fullWidth / 4 }
                                    ) + fadeOut(animationSpec = tween(250))
                        } else {
                            // Từ SignIn → SignUp (trượt từ trái)
                            slideInHorizontally(
                                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                                initialOffsetX = { fullWidth -> -fullWidth }
                            ) + fadeIn(animationSpec = tween(250)) with

                                    slideOutHorizontally(
                                        animationSpec = tween(250, easing = FastOutSlowInEasing),
                                        targetOffsetX = { fullWidth -> fullWidth / 4 }
                                    ) + fadeOut(animationSpec = tween(250))
                        }
                    }
                ) { target ->
                    if (target) {
                        SignInContent(onSwitch = { isSignIn = false }, navController = navController, onLoading = {isLoading = it})
                    } else {
                        SignUpContent(
                            signUpViewModel = signUpViewModel,
                            onSwitch = {
                                isLoading = false
                                isSignIn = true
                            },
                            onLoading = { isLoading = true }
                        )
                    }
                }
            }
        }

        if(isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun SignInContent(onSwitch: () -> Unit, navController: NavHostController, onLoading: (Boolean) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isSignedIn = remember { mutableStateOf(false) }

    if (isSignedIn.value) {
        LaunchedEffect(Unit) {
            navController.navigate("home") {
                popUpTo("dashboard") { inclusive = true }
            }
        }
    }

    Column {
        Text(
            "Sign in to continue",
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            color = Color.White
        )
        Spacer(Modifier.height(24.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emailFocusRequester = remember { FocusRequester() }
            val passwordFocusRequester = remember { FocusRequester() }

            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text("Email") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            var isShowPassword by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text("Password") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { isShowPassword = !isShowPassword }
                    ) {
                        Icon(
                            imageVector = if (isShowPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                },
                visualTransformation = if(!isShowPassword) PasswordVisualTransformation() else VisualTransformation.None
            )

            Spacer(modifier = Modifier.height(28.dp))

            val context = LocalContext.current

            Button(
                onClick = {
                    onLoading(true)
                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            onLoading(false)
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Login successfully!", Toast.LENGTH_SHORT).show()
                                isSignedIn.value = true
                            } else {
                                Toast.makeText(context,
                                    task.exception?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2F6650)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    "Sign in",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { onSwitch() }
            ) {
                Text(
                    "Don't have an account? Sign up",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SignUpContent(
    signUpViewModel: SignUpViewModel,
    onSwitch: () -> Unit,
    onLoading: () -> Unit
) {
    val username = signUpViewModel.username
    val email = signUpViewModel.email
    val password = signUpViewModel.password

    var isShowPassword by remember { mutableStateOf(false) }
    val isLoading = signUpViewModel.uiState is SignUpUiState.Loading

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Create your account",
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            textAlign = TextAlign.Start,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { signUpViewModel.username = it },
            label = { Text("Username") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { signUpViewModel.email = it },
            label = { Text("Email") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { signUpViewModel.password = it },
            label = { Text("Password") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            ),
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            trailingIcon = {
                IconButton(onClick = { isShowPassword = !isShowPassword }) {
                    Icon(
                        imageVector = if (isShowPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            visualTransformation = if (!isShowPassword) PasswordVisualTransformation() else VisualTransformation.None
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                onLoading()
                signUpViewModel.onSignUpClick()
            },
            enabled = !isLoading,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2F6650)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                if (isLoading) "Loading..." else "Sign up",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { onSwitch() }
        ) {
            Text("Already have an account? Sign in", color = Color.White)
        }
    }
}