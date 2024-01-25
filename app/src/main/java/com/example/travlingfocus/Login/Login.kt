package com.example.travlingfocus.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travlingfocus.AppViewModelProvider
import com.example.travlingfocus.R
import com.example.travlingfocus.ui.theme.GrayLoginBackground
import com.example.travlingfocus.ui.theme.GrayText
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
){


    Surface (
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
        ),
        color = MaterialTheme.colorScheme.primary,

    ){
        val coroutineScope = rememberCoroutineScope()
        var loginOrSignUp:Boolean by remember { mutableStateOf(true) }
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            topBar = {
                LoginTopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(120.dp),
                    subTitle = if(loginOrSignUp) "Signin to your account to continue" else "Signup for a new account",
                    title = if(loginOrSignUp) "Login" else "Register"

                )
            },
            content = {
                if(!loginOrSignUp)
                    SignupContent(
                        modifier = Modifier.padding(it),
                        onSignup = {
                            coroutineScope.launch {
                                authViewModel.saveUser()
                            }
                        },
                        onToLogin = {
                            loginOrSignUp = !loginOrSignUp
                        },
                        updateSignUpUiState = { email, username, password ->
                            authViewModel.updateUiState(email, password, username)
                        }
                    )
                else
                    LoginContent(
                        modifier = Modifier.padding(it),
                        onLogin = {
                            coroutineScope.launch {
                                authViewModel.login()
                            }
                        },
                        onToSignup = {
                            loginOrSignUp = !loginOrSignUp
                        },
                        updateLoginUIState = { email, password ->
                            authViewModel.updateLoginState(email, password)
                        },
                        loginUIState = authViewModel.loginUIState.value
                    )
            }

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupContent(
    modifier: Modifier,
    onSignup: () -> Unit,
    onToLogin: () -> Unit,
    updateSignUpUiState: (email: String, username: String, password: String) -> Unit
)
{
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisibility:Boolean by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisibility:Boolean by rememberSaveable { mutableStateOf(false) }
    var yourName by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .height(IntrinsicSize.Max),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,

            )
        {

            Text(
                text = "YOUR NAME",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = GrayText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, bottom = 8.dp)
            )
            TextField(
                value = yourName,
                onValueChange = { yourName = it
                                updateSignUpUiState(email, yourName, password)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    containerColor = GrayLoginBackground.copy(alpha = 0.2f),
                ),
            )

            Text(
                text = "EMAIL",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = GrayText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, bottom = 8.dp)
            )
            TextField(
                value = email,
                onValueChange = { email = it
                                updateSignUpUiState(email, yourName, password)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    containerColor = GrayLoginBackground.copy(alpha = 0.2f),
                ),
            )
            if(email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                Text(
                    text = "Invalid Email",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 8.dp)
                )

            Text(
                text = "PASSWORD",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = GrayText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, bottom = 8.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it
                                updateSignUpUiState(email, yourName, password)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    containerColor = GrayLoginBackground.copy(alpha = 0.2f),
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = ImageVector.vectorResource(id = if (!passwordVisibility) R.drawable.ic_toggle_pass else R.drawable.ic_toggle_pas_hide),
                            contentDescription = null,
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Text(
                text = "CONFIRM PASSWORD",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = GrayText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, bottom = 8.dp)
            )
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    containerColor = GrayLoginBackground.copy(alpha = 0.2f),
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        confirmPasswordVisibility = !confirmPasswordVisibility
                    }) {
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = ImageVector.vectorResource(id = if (!confirmPasswordVisibility) R.drawable.ic_toggle_pass else R.drawable.ic_toggle_pas_hide),
                            contentDescription = null,
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            if(password != confirmPassword)
                Text(
                    text = "Password not match",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 8.dp)
                )
        }

        Column(
            modifier = Modifier.height(250.dp),
            verticalArrangement = Arrangement.Top,
        )
        {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp),
                onClick = {
                    if(password == confirmPassword)
                        onSignup()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(26.dp)
            )
            {
                Text(
                    text = "Create Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(8.dp)

                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 56.dp, end = 56.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "Already a user?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
                Text(
                    text = " Login now",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .clickable(onClick = onToLogin)
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    loginUIState: LoginUIState,
    onLogin: () -> Unit,
    onToSignup: () -> Unit,
    updateLoginUIState: (email: String, password: String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility:Boolean by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .height(IntrinsicSize.Max),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,

        )
        {
            if(loginUIState.loginError.isNotBlank())
                Text(
                    text = loginUIState.loginError,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, bottom = 8.dp)
                )

            Text(
                text = "EMAIL",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = GrayText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, bottom = 8.dp)
            )
            TextField(
                value = email,
                onValueChange = { email = it
                    updateLoginUIState(email, password)  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 56.dp),
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    containerColor = GrayLoginBackground.copy(alpha = 0.2f),
                ),
            )

            Text(
                text = "PASSWORD",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = GrayText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, bottom = 8.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it
                                updateLoginUIState(email, password)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    containerColor = GrayLoginBackground.copy(alpha = 0.2f),
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_toggle_pass),
                            contentDescription = null,
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Text(
                text = "Forgot Password?",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 24.dp),
                textAlign = TextAlign.End
            )

        }

        Column(
            modifier = Modifier.height(250.dp),
            verticalArrangement = Arrangement.Top,
        )
        {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp),
                onClick = {
                    onLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(26.dp)
            )
            {
                Text(
                    text = "Login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 56.dp, end = 56.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "Don't have an account?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                )
                Text(
                    text = " Sign Up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .clickable(onClick = onToSignup)
                )
            }
        }

    }

}


@Composable
fun LoginTopAppBar(
    modifier: Modifier,
    title: String,
    subTitle: String
)
{
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .height(120.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Image(
            painterResource(id = R.drawable.logo_transparent),
            contentDescription = null,
            modifier = Modifier
                .height(120.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = title,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = subTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}