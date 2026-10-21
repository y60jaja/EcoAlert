package com.example.pruebasubicacion.presentation.view

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Dangerous
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Masks
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.Wc
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.pruebasubicacion.R
import com.example.pruebasubicacion.data.UserPreferences
import com.example.pruebasubicacion.data.dataStore
import com.example.pruebasubicacion.data.model.ClimaEstado
import com.example.pruebasubicacion.util.creaInforme
import com.example.pruebasubicacion.util.sumarHorasUtc

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// --- Modelo de Datos de Perfil de Usuario ---
data class UserProfileData(
    val isLoggedIn: Boolean = false,
    val nombre: String = "Juan",
    val apellido: String = "Pérez",
    val correo: String = "juan.perez@ecoalert.com",
    val peso: String = "75 kg",
    val sexo: String = "Masculino"
)

// --- Paleta de Colores Adaptativa (Modo Claro & Modo Oscuro) ---
data class ThemeColors(
    val bgPrimary: Color,
    val bgCard: Color,
    val bgSubtle: Color,
    val borderCard: Color,
    val textPrimary: Color,
    val textMuted: Color,
    val accentBrand: Color,
    val headerGradient: Brush
)

@Composable
fun getAdaptiveThemeColors(isDark: Boolean): ThemeColors {
    return if (isDark) {
        ThemeColors(
            bgPrimary = Color(0xFF090D16),     // Midnight Dark
            bgCard = Color(0xFF131C2E),        // Slate Card Dark
            bgSubtle = Color(0xFF1E293B),      // Surface Subtle Dark
            borderCard = Color(0xFF1E2D4A),    // Slate Border Dark
            textPrimary = Color(0xFFF8FAFC),   // Bright White
            textMuted = Color(0xFF94A3B8),     // Muted Slate
            accentBrand = Color(0xFF10B981),   // Radiant Emerald
            headerGradient = Brush.verticalGradient(listOf(Color(0xFF064E3B), Color(0xFF0F766E)))
        )
    } else {
        ThemeColors(
            bgPrimary = Color(0xFFF1F5F9),     // Slate 100 Light
            bgCard = Color(0xFFFFFFFF),        // Pure White
            bgSubtle = Color(0xFFF8FAFC),      // Soft Light Surface
            borderCard = Color(0xFFE2E8F0),    // Light Border
            textPrimary = Color(0xFF0F172A),   // Deep Dark Slate
            textMuted = Color(0xFF64748B),     // Cool Grey Muted
            accentBrand = Color(0xFF059669),   // Deep Emerald
            headerGradient = Brush.verticalGradient(listOf(Color(0xFF0D9488), Color(0xFF0284C7)))
        )
    }
}

data class RiskInfo(
    val category: String,
    val color: Color,
    val message: String
)

@Composable
fun getRiskInfoAdaptive(pm25: Float, isDark: Boolean): RiskInfo {
    return when {
        pm25 <= 12.0f -> RiskInfo(
            "Bueno",
            if (isDark) Color(0xFF10B981) else Color(0xFF059669),
            "La calidad del aire es satisfactoria y el riesgo es mínimo."
        )

        pm25 <= 35.4f -> RiskInfo(
            "Mala",
            if (isDark) Color(0xFFF59E0B) else Color(0xFFD97706),
            "Calidad aceptable, representa riesgo leve para personas sensibles."
        )

        pm25 <= 55.4f -> RiskInfo(
            "Riesgosa",
            if (isDark) Color(0xFFF97316) else Color(0xFFEA580C),
            "Grupos sensibles pueden experimentar problemas de salud."
        )

        pm25 <= 150.4f -> RiskInfo(
            "Insalubre",
            if (isDark) Color(0xFFEF4444) else Color(0xFFDC2626),
            "Riesgo aumentado de efectos respiratorios y cardíacos."
        )

        pm25 <= 250.4f -> RiskInfo(
            "Muy dañino",
            if (isDark) Color(0xFFA855F7) else Color(0xFF9333EA),
            "Alerta de salud; es probable que todos se vean afectados."
        )

        else -> RiskInfo(
            "Peligroso",
            if (isDark) Color(0xFFB91C1C) else Color(0xFF991B1B),
            "Situación de emergencia. Riesgos severos para todos."
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoAlertScreen(
    estado: ClimaEstado = ClimaEstado()
) {
    val isDark = isSystemInDarkTheme()
    val theme = getAdaptiveThemeColors(isDark)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Deportista, 1 = Sensible/Asma
    var showNotifications by remember { mutableStateOf(false) }
    var showProfile by remember { mutableStateOf(false) }

    // Estado de Usuario (Perfil y Login)
    var userProfile by remember { mutableStateOf(UserProfileData()) }

    CompositionLocalProvider(LocalContentColor provides theme.textPrimary) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = theme.bgPrimary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Dashboard Adaptativo con Botón de Perfil
                TopDashboardBar(
                    theme = theme,
                    isLoggedIn = userProfile.isLoggedIn,
                    showNotifications = showNotifications,
                    showProfile = showProfile,
                    onNotificationClick = {
                        showNotifications = !showNotifications
                        showProfile = false
                    },
                    onProfileClick = {
                        showProfile = !showProfile
                        showNotifications = false
                    }
                )

                if (showNotifications) {
                    NotificationsView(theme = theme, onClose = { showNotifications = false })
                } else if (showProfile) {
                    ProfileAccountView(
                        theme = theme,
                        userProfile = userProfile,
                        onLoginSuccess = { updatedProfile ->
                            userProfile = updatedProfile.copy(isLoggedIn = true)
                        },
                        onLogout = {
                            userProfile = userProfile.copy(isLoggedIn = false)
                        },
                        onClose = { showProfile = false }
                    )
                } else {
                    if (isLandscape) {
                        // MODO HORIZONTAL: Distribución Optimizada en 2 Paneles
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            // Panel Izquierdo: Ubicación + Hero AQI Arc + Perfil & Recomendación
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                LocationCardAdaptive(estado = estado, theme = theme)
                                HeroAQIArcMeter(estado = estado, isDark = isDark)
                                ProfileSwitchSegmented(
                                    selectedTab = selectedTab,
                                    onTabSelected = { selectedTab = it },
                                    theme = theme
                                )
                                ProfileRecommendationBox(
                                    estado = estado,
                                    selectedTab = selectedTab,
                                    theme = theme
                                )
                            }

                            // Panel Derecho: Métricas Ambientales + Pronóstico 24h + Vista Semanal
                            Column(
                                modifier = Modifier.weight(1.1f),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                EnvironmentalMetricsGrid(estado = estado, theme = theme)
                                if (selectedTab == 0) {
                                    HourlyForecast24h(estado = estado, theme = theme, isDark = isDark)
                                    WeeklyForecast(estado = estado, theme = theme, isDark = isDark)
                                } else {
                                    MainPollutantsCardExtended(
                                        estado = estado,
                                        theme = theme,
                                        isDark = isDark
                                    )
                                }
                            }
                        }
                    } else {
                        // MODO RETRATO: Layout Vertical Fluido
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            LocationCardAdaptive(estado = estado, theme = theme)
                            HeroAQIArcMeter(estado = estado, isDark = isDark)

                            ProfileSwitchSegmented(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it },
                                theme = theme
                            )

                            ProfileRecommendationBox(
                                estado = estado,
                                selectedTab = selectedTab,
                                theme = theme
                            )

                            EnvironmentalMetricsGrid(estado = estado, theme = theme)

                            if (selectedTab == 0) {
                                HourlyForecast24h(estado = estado, theme = theme, isDark = isDark)
                                WeeklyForecast(estado = estado, theme = theme, isDark = isDark)
                            } else {
                                MainPollutantsCardExtended(
                                    estado = estado,
                                    theme = theme,
                                    isDark = isDark
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopDashboardBar(
    theme: ThemeColors,
    isLoggedIn: Boolean,
    showNotifications: Boolean,
    showProfile: Boolean,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = theme.accentBrand,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text(
                        "ECO",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
            Text(
                "Alert",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = theme.textPrimary,
                letterSpacing = (-0.5).sp
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (showNotifications) theme.accentBrand.copy(0.2f) else theme.bgCard,
                        CircleShape
                    )
            ) {
                Icon(
                    Icons.Outlined.NotificationsActive,
                    contentDescription = "Notificaciones",
                    tint = if (showNotifications) theme.accentBrand else theme.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(40.dp)
                    .background(theme.bgCard, CircleShape)
            ) {
                Icon(
                    Icons.Outlined.Analytics,
                    contentDescription = "Estadísticas",
                    tint = theme.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Botón de Cuenta / Perfil de Usuario
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (showProfile || isLoggedIn) theme.accentBrand.copy(0.2f) else theme.bgCard,
                        CircleShape
                    )
            ) {
                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = "Perfil de usuario",
                    tint = if (showProfile || isLoggedIn) theme.accentBrand else theme.textPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileAccountView(
    theme: ThemeColors,
    userProfile: UserProfileData,
    onLoginSuccess: (UserProfileData) -> Unit,
    onLogout: () -> Unit,
    onClose: () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard)
    ) {
        Column(
            Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = theme.accentBrand,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (userProfile.isLoggedIn) "Perfil de Usuario" else "Iniciar Sesión",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = theme.textPrimary
                    )
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = theme.textPrimary)
                }
            }

            if (userProfile.isLoggedIn) {
                // --- VISTA PERFIL LOGUEADO ---
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = CircleShape,
                        color = theme.accentBrand.copy(0.15f),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${userProfile.nombre.take(1)}${userProfile.apellido.take(1)}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = theme.accentBrand
                            )
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "${userProfile.nombre} ${userProfile.apellido}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary
                    )
                    Text(
                        text = userProfile.correo,
                        fontSize = 13.sp,
                        color = theme.textMuted
                    )

                    Spacer(Modifier.height(16.dp))

                    // Detalles del Usuario
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ProfileDetailRow("Nombre", userProfile.nombre, Icons.Outlined.Badge, theme)
                        ProfileDetailRow("Apellido", userProfile.apellido, Icons.Outlined.Badge, theme)
                        ProfileDetailRow("Correo", userProfile.correo, Icons.Outlined.Email, theme)
                        ProfileDetailRow("Peso", userProfile.peso, Icons.Outlined.FitnessCenter, theme)
                        ProfileDetailRow("Sexo", userProfile.sexo, Icons.Outlined.Wc, theme)
                    }

                    Spacer(Modifier.height(18.dp))

                    OutlinedButton(
                        onClick = onLogout,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(0.5f)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                // --- VISTA INICIAR SESIÓN (NO LOGUEADO) ---
                var inputCorreo by remember { mutableStateOf("") }
                var inputPassword by remember { mutableStateOf("") }

                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Ingresa tus credenciales para sincronizar tus preferencias y monitoreo de salud.",
                        fontSize = 13.sp,
                        color = theme.textMuted,
                        lineHeight = 18.sp
                    )

                    OutlinedTextField(
                        value = inputCorreo,
                        onValueChange = { inputCorreo = it },
                        label = { Text("Correo Electrónico") },
                        leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null, tint = theme.accentBrand) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = theme.accentBrand,
                            unfocusedBorderColor = theme.borderCard,
                            focusedLabelColor = theme.accentBrand
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    OutlinedTextField(
                        value = inputPassword,
                        onValueChange = { inputPassword = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = theme.accentBrand) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = theme.accentBrand,
                            unfocusedBorderColor = theme.borderCard,
                            focusedLabelColor = theme.accentBrand
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(Modifier.height(4.dp))

                    Button(
                        onClick = {
                            val emailToUse = if (inputCorreo.isNotBlank()) inputCorreo else "juan.perez@ecoalert.com"
                            onLoginSuccess(
                                UserProfileData(
                                    isLoggedIn = true,
                                    nombre = "Juan",
                                    apellido = "Pérez",
                                    correo = emailToUse,
                                    peso = "75 kg",
                                    sexo = "Masculino"
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = theme.accentBrand),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Iniciar Sesión", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                    }

                    TextButton(
                        onClick = {
                            onLoginSuccess(
                                UserProfileData(
                                    isLoggedIn = true,
                                    nombre = "María",
                                    apellido = "González",
                                    correo = "maria.gonzalez@ecoalert.com",
                                    peso = "62 kg",
                                    sexo = "Femenino"
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("¿No tienes cuenta? Probar perfil demo", color = theme.accentBrand, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileDetailRow(
    label: String,
    value: String,
    icon: ImageVector,
    theme: ThemeColors
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = theme.bgSubtle,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = theme.accentBrand, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(10.dp))
                Text(label, fontSize = 13.sp, color = theme.textMuted, fontWeight = FontWeight.Medium)
            }
            Text(value, fontSize = 14.sp, color = theme.textPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LocationCardAdaptive(estado: ClimaEstado, theme: ThemeColors) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = theme.accentBrand.copy(0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Outlined.Place,
                            contentDescription = null,
                            tint = theme.accentBrand,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        "ESTACIÓN ACTIVA",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textMuted,
                        letterSpacing = 1.sp
                    )
                    Text(
                        estado.nombreUbicacion ?: "Obteniendo ubicación...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary
                    )
                }
            }

            Surface(
                shape = CircleShape,
                color = theme.accentBrand.copy(0.12f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = theme.accentBrand,
                        modifier = Modifier.size(6.dp)
                    ) {}
                    Text(
                        "En vivo",
                        fontSize = 11.sp,
                        color = theme.accentBrand,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun HeroAQIArcMeter(estado: ClimaEstado, isDark: Boolean) {
    val pm25 = estado.clima?.hourly?.pm25?.firstOrNull() ?: 0f
    val listaPm25 = listOf<Float>(pm25, 10f, 35f, 55f, 150f, 250f, 300f)
    var currentIndex by remember { mutableIntStateOf(0) }

    val activePm25 = listaPm25[currentIndex]
    val risk = getRiskInfoAdaptive(activePm25, isDark)

    val animatedColor by animateColorAsState(
        targetValue = risk.color,
        animationSpec = tween(durationMillis = 350),
        label = "riskColorAnimation"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = animatedColor)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        getRiskIconForPM25(activePm25),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "CALIDAD DEL AIRE",
                        color = Color.White.copy(0.9f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(0.25f)
                ) {
                    Text(
                        text = risk.category.uppercase(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            // Métrica Interactiva PM2.5 (Demostración de ciclo mantenida)
            TextButton(
                onClick = {
                    currentIndex = (currentIndex + 1) % listaPm25.size
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$activePm25",
                            color = Color.White,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Black,
                            lineHeight = 48.sp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "µg/m³ PM2.5",
                            color = Color.White.copy(0.85f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(0.18f)
            ) {
                Text(
                    text = risk.message,
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileSwitchSegmented(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    theme: ThemeColors
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfilePillItem(
                title = "Perfil Deportista",
                icon = Icons.AutoMirrored.Outlined.DirectionsRun,
                isSelected = selectedTab == 0,
                theme = theme,
                onClick = { onTabSelected(0) },
                modifier = Modifier.weight(1f)
            )
            ProfilePillItem(
                title = "Sensible / Asma",
                icon = Icons.Outlined.Shield,
                isSelected = selectedTab == 1,
                theme = theme,
                onClick = { onTabSelected(1) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProfilePillItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    theme: ThemeColors,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(if (isSelected) theme.accentBrand else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else theme.textMuted,
                modifier = Modifier.size(18.dp)
            )
            Text(
                title,
                color = if (isSelected) Color.White else theme.textMuted,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ProfileRecommendationBox(
    estado: ClimaEstado,
    selectedTab: Int,
    theme: ThemeColors
) {
    val pm25 = estado.clima?.hourly?.pm25?.firstOrNull() ?: 0f

    val (title, body, icon) = if (selectedTab == 0) {
        when {
            pm25 <= 12f -> Triple("Entrenamiento Recomendado", "Condiciones óptimas para realizar actividades físicas al aire libre sin restricciones.", Icons.AutoMirrored.Outlined.DirectionsRun)
            pm25 <= 35.4f -> Triple("Precaución en Ejercicio Prolongado", "Calidad aceptable. Si realizas rutinas extensas, mantente hidratado.", Icons.Outlined.Info)
            else -> Triple("Restringir Ejercicio Exterior", "Altos niveles de contaminantes. Se aconseja entrenar en espacios cerrados.", Icons.Outlined.WarningAmber)
        }
    } else {
        when {
            pm25 <= 12f -> Triple("Entorno Seguro para Sensibles", "Calidad de aire excelente. Bajo riesgo de irritación o problemas respiratorios.", Icons.Outlined.Shield)
            pm25 <= 35.4f -> Triple("Atención Grupos Sensibles", "Sensibilidad leve. Ten a la mano tu inhalador o medicamentos habituales.", Icons.Outlined.Masks)
            else -> Triple("Riesgo para Vías Respiratorias", "Calidad del aire desfavorable. Minimiza actividades fuera de casa y usa mascarilla.", Icons.Outlined.Dangerous)
        }
    }

    Surface(
        shape = RoundedCornerShape(22.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.accentBrand.copy(0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = theme.accentBrand.copy(0.15f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = theme.accentBrand, modifier = Modifier.size(22.dp))
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title.uppercase(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = theme.accentBrand,
                    letterSpacing = 0.5.sp
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = body,
                    fontSize = 13.sp,
                    color = theme.textPrimary,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun EnvironmentalMetricsGrid(estado: ClimaEstado, theme: ThemeColors) {
    val hum = estado.clima?.hourly?.humidity?.firstOrNull() ?: 0f
    val temp = estado.clima?.hourly?.temperature?.firstOrNull() ?: 0f
    var co2 = estado.clima?.hourly?.carbonDioxide?.firstOrNull() ?: 0f
    co2 /= 10
    val pm10 = estado.clima?.hourly?.pm10?.firstOrNull() ?: 0f

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            "MÉTRICAS AMBIENTALES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = theme.textMuted,
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTileAdaptive(
                icon = Icons.Outlined.WaterDrop,
                label = "Humedad",
                value = "${hum.toInt()}%",
                theme = theme,
                modifier = Modifier.weight(1f)
            )
            MetricTileAdaptive(
                icon = Icons.Outlined.Thermostat,
                label = "Temperatura",
                value = "${temp.toInt()}°C",
                theme = theme,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTileAdaptive(
                icon = Icons.Outlined.Air,
                label = "Dióxido Carbono",
                value = "${co2.toInt()}% CO₂",
                theme = theme,
                modifier = Modifier.weight(1f)
            )
            MetricTileAdaptive(
                icon = Icons.Outlined.Assessment,
                label = "PM10",
                value = "${pm10.toInt()} µg/m³",
                theme = theme,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun MetricTileAdaptive(
    icon: ImageVector,
    label: String,
    value: String,
    theme: ThemeColors,
    modifier: Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = theme.accentBrand.copy(0.12f),
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = theme.accentBrand, modifier = Modifier.size(18.dp))
                }
            }

            Column {
                Text(label, fontSize = 11.sp, color = theme.textMuted, fontWeight = FontWeight.Medium)
                Text(value, fontSize = 14.sp, color = theme.textPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun HourlyForecast24h(estado: ClimaEstado, theme: ThemeColors, isDark: Boolean) {
    val clima = estado.clima ?: return
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text("Próximas 24 Horas", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = theme.textPrimary)
            Spacer(Modifier.height(14.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                val currentHour = LocalDateTime.now().hour
                val horas = sumarHorasUtc(clima.hourly.time, currentHour)
                val data = horas.zip(clima.hourly.pm25 ?: emptyList()).take(24)

                items(data) { (time, pm) ->
                    val icon = getRiskIconForPM25(pm)
                    val risk = getRiskInfoAdaptive(pm, isDark)
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = risk.color.copy(0.12f),
                        border = BorderStroke(1.dp, risk.color.copy(0.3f))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Text(
                                time.substringAfter("T").take(5),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = theme.textMuted
                            )
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = risk.color,
                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .size(22.dp)
                            )
                            Text(
                                "${pm.toInt()} µg",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeeklyForecast(estado: ClimaEstado, theme: ThemeColors, isDark: Boolean) {
    val clima = estado.clima ?: return
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text("Pronóstico Próximos Días", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = theme.textPrimary)
            Spacer(Modifier.height(12.dp))

            val hourlyTimes = clima.hourly.time
            val pm25Values = clima.hourly.pm25 ?: emptyList()

            val dayGroups = hourlyTimes.indices.groupBy { index ->
                hourlyTimes[index].substringBefore("T")
            }.values.toList()

            dayGroups.forEachIndexed { i, indices ->
                val dayPm = indices.map { pm25Values.getOrNull(it) ?: 0f }
                val avg = if (dayPm.isNotEmpty()) dayPm.average().toFloat() else 0f
                val date = LocalDateTime.parse(
                    hourlyTimes[indices.first()],
                    DateTimeFormatter.ISO_DATE_TIME
                )
                val label = if (i == 0) "Hoy" else date.format(
                    DateTimeFormatter.ofPattern("EEE")
                )
                val risk = getRiskInfoAdaptive(avg, isDark)

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        label.uppercase(),
                        Modifier.width(55.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = theme.textPrimary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = getRiskIconForPM25(avg),
                            contentDescription = null,
                            tint = risk.color,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            "${avg.toInt()} µg/m³",
                            color = theme.textMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Box(
                        Modifier
                            .width(60.dp)
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(risk.color)
                    )
                }
            }
        }
    }
}

@Composable
fun MainPollutantsCardExtended(
    estado: ClimaEstado,
    theme: ThemeColors,
    isDark: Boolean
) {
    val h = estado.clima?.hourly ?: return
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard)
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Detalle de Contaminantes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = theme.textPrimary
                )

                TextButton(
                    onClick = { creaInforme(h) },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        "Generar Informe",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.accentBrand
                    )
                }
            }

            val list = listOf(
                "PM2.5" to (h.pm25?.firstOrNull() ?: 0f) to 250f,
                "PM10" to (h.pm10?.firstOrNull() ?: 0f) to 500f,
                "NO2" to (h.nitrogenDioxide?.firstOrNull() ?: 0f) to 200f,
                "O3" to (h.ozone?.firstOrNull() ?: 0f) to 180f,
                "SO2" to (h.sulphurDioxide?.firstOrNull() ?: 0f) to 350f,
                "CO" to (h.carbonMonoxide?.firstOrNull() ?: 0f) to 10000f
            )

            list.forEach { (data, max) ->
                val (name, value) = data
                PollutantRowMinimalAdaptive(name, value, max, theme, isDark)
            }
        }
    }
}

@Composable
fun PollutantRowMinimalAdaptive(
    name: String,
    value: Float,
    maxValue: Float,
    theme: ThemeColors,
    isDark: Boolean
) {
    val progress = (value / maxValue).coerceIn(0f, 1f)
    val barColor = if (name == "PM2.5") getRiskInfoAdaptive(value, isDark).color else theme.accentBrand

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, color = theme.textMuted, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(
                "${value.toInt()}",
                color = theme.textPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = barColor,
            trackColor = theme.bgSubtle,
        )
    }
}

fun getRiskIconForPM25(pm25: Float): ImageVector = when {
    pm25 <= 12.0f -> Icons.Outlined.CheckCircle
    pm25 <= 35.4f -> Icons.Outlined.Info
    pm25 <= 55.4f -> Icons.Outlined.WarningAmber
    else -> Icons.Outlined.Dangerous
}

@Composable
fun NotificationsView(theme: ThemeColors, onClose: () -> Unit) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context.applicationContext.dataStore) }
    val sendedNotifications by userPreferences.sendedNotificationFlow.collectAsState(initial = emptyList())

    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard)
    ) {
        Column(
            Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = theme.accentBrand,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Notificaciones Enviadas", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = theme.textPrimary)
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = theme.textPrimary)
                }
            }

            if (sendedNotifications.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes notificaciones registradas", color = theme.textMuted, fontSize = 14.sp)
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (id in sendedNotifications) {
                        when (id) {
                            "n1" -> NotificationItemAdaptive(
                                "Alerta de Contaminación",
                                "El nivel de contaminación ha subido. Ten cuidado si vas a realizar actividades al aire libre.",
                                theme
                            )
                            "n2" -> NotificationItemAdaptive(
                                "Mejora del Aire",
                                "El nivel de contaminación ha bajado favorablemente.",
                                theme
                            )
                            "n3" -> NotificationItemAdaptive(
                                "Estado Estable",
                                "El nivel de contaminación se mantiene sin cambios significativos.",
                                theme
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItemAdaptive(title: String, desc: String, theme: ThemeColors) {
    Surface(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = theme.bgSubtle,
        border = BorderStroke(1.dp, theme.accentBrand.copy(0.2f))
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = theme.accentBrand)
            Spacer(Modifier.height(4.dp))
            Text(desc, fontSize = 13.sp, color = theme.textMuted, lineHeight = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EcoAlertScreenPreview() {
    EcoAlertScreen()
}
