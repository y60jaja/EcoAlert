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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
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
    val correo: String = "juan.perez@aerotrack.com",
    val peso: String = "75 kg",
    val sexo: String = "Masculino",
    val alergias: String = "Polen de Gramíneas",
    val tipoCiudad: String = "Ciudad Valle Central (Santiago, Rancagua, Talca)"
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
    var activeMetricInfo by remember { mutableStateOf<Triple<String, String, ImageVector>?>(null) }

    val reportAction = {
        if (!userProfile.isLoggedIn) {
            showProfile = true
        } else {
            activeMetricInfo = Triple(
                "Reporte Próximo Día Generado",
                "¡Excelente! El reporte predictivo de calidad de aire para el próximo día ha sido generado y sincronizado con tu perfil (${userProfile.nombre} ${userProfile.apellido}).",
                Icons.Outlined.Assessment
            )
        }
    }

    CompositionLocalProvider(LocalContentColor provides theme.textPrimary) {
        Box(modifier = Modifier.fillMaxSize()) {
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
                                // Panel Izquierdo: Ubicación + Hero AQI Arc + Reporte/Detalle
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    LocationCardAdaptive(estado = estado, theme = theme)
                                    HeroAQIArcMeter(estado = estado, selectedTab = selectedTab, isDark = isDark)
                                    ProfileSwitchSegmented(
                                        selectedTab = selectedTab,
                                        onTabSelected = { selectedTab = it },
                                        theme = theme
                                    )
                                }

                                // Panel Derecho: Métricas Ambientales + Pronóstico 24h + Vista Semanal o Detalle
                                Column(
                                    modifier = Modifier.weight(1.1f),
                                    verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    if (selectedTab == 0) {
                                        EnvironmentalMetricsGrid(
                                            estado = estado,
                                            theme = theme,
                                            onMetricClick = { title, desc, icon -> activeMetricInfo = Triple(title, desc, icon) }
                                        )
                                        HourlyForecast24h(estado = estado, theme = theme, isDark = isDark)
                                        WeeklyForecast(estado = estado, theme = theme, isDark = isDark)
                                        GenerateReportButton(theme = theme, isLoggedIn = userProfile.isLoggedIn, onGenerate = reportAction)
                                    } else {
                                        MainPollutantsCardExtended(
                                            estado = estado,
                                            userProfile = userProfile,
                                            theme = theme,
                                            isDark = isDark
                                        )
                                        GenerateReportButton(theme = theme, isLoggedIn = userProfile.isLoggedIn, onGenerate = reportAction)
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
                                HeroAQIArcMeter(estado = estado, selectedTab = selectedTab, isDark = isDark)

                                ProfileSwitchSegmented(
                                    selectedTab = selectedTab,
                                    onTabSelected = { selectedTab = it },
                                    theme = theme
                                )

                                if (selectedTab == 0) {
                                    EnvironmentalMetricsGrid(
                                        estado = estado,
                                        theme = theme,
                                        onMetricClick = { title, desc, icon -> activeMetricInfo = Triple(title, desc, icon) }
                                    )
                                    HourlyForecast24h(estado = estado, theme = theme, isDark = isDark)
                                    WeeklyForecast(estado = estado, theme = theme, isDark = isDark)
                                    GenerateReportButton(theme = theme, isLoggedIn = userProfile.isLoggedIn, onGenerate = reportAction)
                                } else {
                                    MainPollutantsCardExtended(
                                        estado = estado,
                                        userProfile = userProfile,
                                        theme = theme,
                                        isDark = isDark
                                    )
                                    GenerateReportButton(theme = theme, isLoggedIn = userProfile.isLoggedIn, onGenerate = reportAction)
                                }
                            }
                        }
                    }
                }
            }

            // Diálogo emergente interactivo con transparencia glassmorphism e icono específico
            activeMetricInfo?.let { (title, description, icon) ->
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { activeMetricInfo = null },
                    confirmButton = {
                        TextButton(onClick = { activeMetricInfo = null }) {
                            Text("Entendido", fontWeight = FontWeight.Bold, color = theme.accentBrand)
                        }
                    },
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Surface(
                                shape = CircleShape,
                                color = theme.accentBrand.copy(0.15f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(icon, contentDescription = null, tint = theme.accentBrand, modifier = Modifier.size(20.dp))
                                }
                            }
                            Text(title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = theme.textPrimary)
                        }
                    },
                    text = {
                        Text(description, fontSize = 14.sp, color = theme.textPrimary, lineHeight = 21.sp)
                    },
                    containerColor = theme.bgCard.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(24.dp),
                    tonalElevation = 8.dp
                )
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
                        "AERO",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
            Text(
                "Track",
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
    var isRegisterTab by remember { mutableStateOf(false) }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.5.dp, theme.accentBrand.copy(0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header con botón de cierre
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = theme.accentBrand.copy(0.18f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                if (userProfile.isLoggedIn) Icons.Outlined.Person else Icons.Outlined.Lock,
                                contentDescription = null,
                                tint = theme.accentBrand,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (userProfile.isLoggedIn) "Mi Perfil AeroTrack" else "Bienvenido a AeroTrack",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = theme.textPrimary
                        )
                        Text(
                            text = if (userProfile.isLoggedIn) "Sesión sincronizada" else "Monitoreo de aire para alto rendimiento",
                            fontSize = 11.sp,
                            color = theme.textMuted,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = theme.textPrimary)
                }
            }

            if (!userProfile.isLoggedIn) {
                // Segmented Tab Switcher (Iniciar Sesión / Registrarse)
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = theme.bgSubtle,
                    border = BorderStroke(1.dp, theme.borderCard),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (!isRegisterTab) theme.accentBrand else Color.Transparent)
                                .clickable { isRegisterTab = false }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Iniciar Sesión",
                                color = if (!isRegisterTab) Color.White else theme.textMuted,
                                fontWeight = if (!isRegisterTab) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isRegisterTab) theme.accentBrand else Color.Transparent)
                                .clickable { isRegisterTab = true }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Registrarse",
                                color = if (isRegisterTab) Color.White else theme.textMuted,
                                fontWeight = if (isRegisterTab) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
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
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${userProfile.nombre.take(1)}${userProfile.apellido.take(1)}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = theme.accentBrand
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

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

                    Spacer(Modifier.height(18.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ProfileDetailRow("Nombre", userProfile.nombre, Icons.Outlined.Badge, theme)
                        ProfileDetailRow("Apellido", userProfile.apellido, Icons.Outlined.Badge, theme)
                        ProfileDetailRow("Correo", userProfile.correo, Icons.Outlined.Email, theme)
                        ProfileDetailRow("Peso", userProfile.peso, Icons.Outlined.FitnessCenter, theme)
                        ProfileDetailRow("Sexo", userProfile.sexo, Icons.Outlined.Wc, theme)
                        ProfileDetailRow("Alergias / Polen", userProfile.alergias, Icons.Outlined.Shield, theme)
                        ProfileDetailRow("Tipo de Entorno", userProfile.tipoCiudad, Icons.Outlined.Place, theme)
                    }

                    Spacer(Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = onLogout,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Cerrar Sesión", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            } else {
                if (isRegisterTab) {
                    // --- VISTA REGISTRARSE ---
                    var regNombre by remember { mutableStateOf("") }
                    var regApellido by remember { mutableStateOf("") }
                    var regCorreo by remember { mutableStateOf("") }
                    var regPassword by remember { mutableStateOf("") }
                    var regPeso by remember { mutableStateOf("70 kg") }
                    var regSexo by remember { mutableStateOf("Masculino") }
                    var regAlergias by remember { mutableStateOf("Polen de Gramíneas") }
                    var regTipoCiudad by remember { mutableStateOf("Urbana (Alta densidad / Smog urbano)") }

                    val listaAlergias = listOf(
                        "Polen de Gramíneas",
                        "Polen de Plátano Oriental",
                        "Polen de Olivo",
                        "Polen de Abedul",
                        "Esporas de Hongo / Moho",
                        "Material Particulado / Smog Urbano",
                        "Ninguna / Sin alergias"
                    )

                    val listaTiposCiudad = listOf(
                        "Urbana (Alta densidad / Smog urbano)",
                        "Rural / Agrícola (Exposición a polen y cultivos)",
                        "Industrial (Emisiones y alta concentración PM2.5/PM10)",
                        "Costera / Marítima (Alta humedad y brisa marina)",
                        "Montaña / Cordillerana (Aire seco, baja presión y frío)"
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = regNombre,
                            onValueChange = { regNombre = it },
                            label = { Text("Nombre") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null, tint = theme.accentBrand) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = theme.accentBrand,
                                unfocusedBorderColor = theme.borderCard,
                                focusedLabelColor = theme.accentBrand,
                                focusedContainerColor = theme.bgSubtle,
                                unfocusedContainerColor = theme.bgSubtle
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = regApellido,
                            onValueChange = { regApellido = it },
                            label = { Text("Apellido") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Outlined.Badge, contentDescription = null, tint = theme.accentBrand) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = theme.accentBrand,
                                unfocusedBorderColor = theme.borderCard,
                                focusedLabelColor = theme.accentBrand,
                                focusedContainerColor = theme.bgSubtle,
                                unfocusedContainerColor = theme.bgSubtle
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = regCorreo,
                            onValueChange = { regCorreo = it },
                            label = { Text("Correo Electrónico") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null, tint = theme.accentBrand) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = theme.accentBrand,
                                unfocusedBorderColor = theme.borderCard,
                                focusedLabelColor = theme.accentBrand,
                                focusedContainerColor = theme.bgSubtle,
                                unfocusedContainerColor = theme.bgSubtle
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = regPassword,
                            onValueChange = { regPassword = it },
                            label = { Text("Contraseña") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = theme.accentBrand) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = theme.accentBrand,
                                unfocusedBorderColor = theme.borderCard,
                                focusedLabelColor = theme.accentBrand,
                                focusedContainerColor = theme.bgSubtle,
                                unfocusedContainerColor = theme.bgSubtle
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        AeroTrackDropdown(
                            label = "Alergia / Polinizante (Aire libre)",
                            selectedValue = regAlergias,
                            options = listaAlergias,
                            onItemSelected = { regAlergias = it },
                            theme = theme
                        )

                        AeroTrackDropdown(
                            label = "Tipo de Entorno Urbano / Rural",
                            selectedValue = regTipoCiudad,
                            options = listaTiposCiudad,
                            onItemSelected = { regTipoCiudad = it },
                            theme = theme
                        )

                        Spacer(Modifier.height(4.dp))

                        Button(
                            onClick = {
                                onLoginSuccess(
                                    UserProfileData(
                                        isLoggedIn = true,
                                        nombre = if (regNombre.isNotBlank()) regNombre else "Atleta",
                                        apellido = if (regApellido.isNotBlank()) regApellido else "Pro",
                                        correo = if (regCorreo.isNotBlank()) regCorreo else "atleta@aerotrack.com",
                                        peso = regPeso,
                                        sexo = regSexo,
                                        alergias = regAlergias,
                                        tipoCiudad = regTipoCiudad
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.accentBrand),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Text("Crear Cuenta AeroTrack", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                        }
                    }
                } else {
                    // --- VISTA INICIAR SESIÓN (NO LOGUEADO) ---
                    var inputCorreo by remember { mutableStateOf("") }
                    var inputPassword by remember { mutableStateOf("") }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
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
                                focusedLabelColor = theme.accentBrand,
                                focusedContainerColor = theme.bgSubtle,
                                unfocusedContainerColor = theme.bgSubtle
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
                                focusedLabelColor = theme.accentBrand,
                                focusedContainerColor = theme.bgSubtle,
                                unfocusedContainerColor = theme.bgSubtle
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        Spacer(Modifier.height(4.dp))

                        Button(
                            onClick = {
                                val emailToUse = if (inputCorreo.isNotBlank()) inputCorreo else "atleta.pro@aerotrack.com"
                                onLoginSuccess(
                                    UserProfileData(
                                        isLoggedIn = true,
                                        nombre = "Carlos",
                                        apellido = "Mendoza",
                                        correo = emailToUse,
                                        peso = "72 kg",
                                        sexo = "Masculino",
                                        alergias = "Polen de Gramíneas",
                                        tipoCiudad = "Urbana (Alta densidad / Smog urbano)"
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.accentBrand),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Text("Iniciar Sesión", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                        }
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
fun HeroAQIArcMeter(estado: ClimaEstado, selectedTab: Int, isDark: Boolean) {
    val pm25 = estado.clima?.hourly?.pm25?.firstOrNull() ?: 0f
    val listaPm25 = listOf<Float>(pm25, 10f, 35f, 55f, 150f, 250f, 300f)
    var currentIndex by remember { mutableIntStateOf(0) }

    val activePm25 = listaPm25[currentIndex]
    val risk = getRiskInfoAdaptive(activePm25, isDark)

    val recommendation = if (selectedTab == 0) {
        when {
            activePm25 <= 12f -> "Condiciones óptimas para realizar entrenamientos de alto rendimiento al aire libre."
            activePm25 <= 35.4f -> "Calidad aceptable. Si realizas rutinas extensas o de alta intensidad, mantente hidratado."
            else -> "Altos niveles de contaminantes. Se aconseja trasladar la sesión a espacios cerrados."
        }
    } else {
        when {
            activePm25 <= 12f -> "Entorno seguro y óptimo para vías respiratorias sensibles."
            activePm25 <= 35.4f -> "Sensibilidad leve detectada. Ten a la mano tus medicamentos preventivos."
            else -> "Calidad desfavorable. Minimiza la exposición al aire libre y usa protección."
        }
    }

    val animatedColor by animateColorAsState(
        targetValue = risk.color,
        animationSpec = tween(durationMillis = 350),
        label = "riskColorAnimation"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = animatedColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.18f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.12f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Header con insignia Elite
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(0.25f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    getRiskIconForPM25(activePm25),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                "CALIDAD DEL AIRE",
                                color = Color.White.copy(0.85f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.5.sp
                            )
                            Text(
                                "MODO ATLETA PRO",
                                color = Color.White.copy(0.7f),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White.copy(0.3f),
                        border = BorderStroke(1.dp, Color.White.copy(0.4f))
                    ) {
                        Text(
                            text = risk.category.uppercase(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Métrica Central PM2.5 (Interactiva)
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
                                fontSize = 52.sp,
                                fontWeight = FontWeight.Black,
                                lineHeight = 52.sp
                            )
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.padding(bottom = 6.dp)) {
                                Text(
                                    text = "µg/m³",
                                    color = Color.White.copy(0.9f),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "PM2.5 (Toca para simular)",
                                    color = Color.White.copy(0.7f),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Tarjeta de Recomendación / Mensaje Combinado Glassmorphism Elite Refinado
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White.copy(0.18f),
                    border = BorderStroke(1.dp, Color.White.copy(0.35f))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(0.25f),
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Outlined.FitnessCenter,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = "ESTADO Y RECOMENDACIÓN ATLETA PRO",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = risk.message,
                            color = Color.White,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = recommendation,
                            color = Color.White.copy(0.95f),
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
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
                title = "Reporte",
                icon = Icons.Outlined.Assessment,
                isSelected = selectedTab == 0,
                theme = theme,
                onClick = { onTabSelected(0) },
                modifier = Modifier.weight(1f)
            )
            ProfilePillItem(
                title = "Detalle contaminantes",
                icon = Icons.Outlined.Air,
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
fun HourlyForecast24h(estado: ClimaEstado, theme: ThemeColors, isDark: Boolean) {
    val clima = estado.clima ?: return
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        Icons.Outlined.Thermostat,
                        contentDescription = null,
                        tint = theme.accentBrand,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        "Próximas 24 Horas",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = theme.textPrimary
                    )
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                val currentHour = LocalDateTime.now().hour
                val horas = sumarHorasUtc(clima.hourly.time, currentHour)
                val data = horas.zip(clima.hourly.pm25 ?: emptyList()).take(24)

                items(data) { (time, pm) ->
                    val risk = getRiskInfoAdaptive(pm, isDark)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = risk.color.copy(0.12f),
                        border = BorderStroke(1.5.dp, risk.color.copy(0.4f)),
                        modifier = Modifier.width(78.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                time.substringAfter("T").take(5),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textMuted
                            )
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = risk.color.copy(0.25f),
                                border = BorderStroke(1.dp, risk.color.copy(0.5f))
                            ) {
                                Box(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${pm.toInt()} µg",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = risk.color
                                    )
                                }
                            }
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
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        Icons.Outlined.Thermostat,
                        contentDescription = null,
                        tint = theme.accentBrand,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        "Pronóstico Próximos Días",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = theme.textPrimary
                    )
                }
            }

            val hourlyTimes = clima.hourly.time
            val pm25Values = clima.hourly.pm25 ?: emptyList()
            val tempValues = clima.hourly.temperature ?: emptyList()
            val humValues = clima.hourly.humidity ?: emptyList()

            val dayGroups = hourlyTimes.indices.groupBy { index ->
                hourlyTimes[index].substringBefore("T")
            }.values.toList()

            dayGroups.take(7).forEachIndexed { i, indices ->
                val dayPm = indices.map { pm25Values.getOrNull(it) ?: 0f }
                val dayTemp = indices.map { tempValues.getOrNull(it) ?: 28f }
                val dayHum = indices.map { humValues.getOrNull(it) ?: 50f }

                val avgPm = if (dayPm.isNotEmpty()) dayPm.average().toFloat() else 0f
                val maxTemp = if (dayTemp.isNotEmpty()) dayTemp.maxOrNull()?.toInt() ?: 28 else 28
                val minTemp = if (dayTemp.isNotEmpty()) dayTemp.minOrNull()?.toInt() ?: 14 else 14
                val avgHum = if (dayHum.isNotEmpty()) dayHum.average().toInt() else 50

                val date = LocalDateTime.parse(
                    hourlyTimes[indices.first()],
                    DateTimeFormatter.ISO_DATE_TIME
                )
                val label = if (i == 0) "Hoy" else date.format(
                    DateTimeFormatter.ofPattern("EEE")
                )
                val risk = getRiskInfoAdaptive(avgPm, isDark)

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = theme.bgSubtle,
                    border = BorderStroke(1.dp, theme.borderCard.copy(0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Día
                        Text(
                            text = label.uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = theme.textPrimary,
                            modifier = Modifier.width(45.dp)
                        )

                        // Humedad / Condición
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.Outlined.WaterDrop,
                                contentDescription = null,
                                tint = theme.accentBrand,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "$avgHum%",
                                fontSize = 12.sp,
                                color = theme.textMuted,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Badge con valor y unidad integrados
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = risk.color.copy(0.2f),
                            border = BorderStroke(1.dp, risk.color.copy(0.5f))
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${avgPm.toInt()} µg",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = risk.color
                                )
                            }
                        }

                        // Rango de Temperaturas (Máx / Mín estilo app clima pro)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$maxTemp°",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = theme.textPrimary
                            )
                            Text(
                                text = "$minTemp°",
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = theme.textMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainPollutantsCardExtended(
    estado: ClimaEstado,
    userProfile: UserProfileData,
    theme: ThemeColors,
    isDark: Boolean
) {
    val h = estado.clima?.hourly ?: return
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Detalle de Contaminantes",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = theme.textPrimary
                    )
                    Text(
                        "Incluye polinizantes y alérgenos de tu perfil",
                        fontSize = 11.sp,
                        color = theme.accentBrand,
                        fontWeight = FontWeight.Bold
                    )
                }

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
                Triple("PM2.5", (h.pm25?.firstOrNull() ?: 0f), "µg/m³") to 250f,
                Triple("PM10", (h.pm10?.firstOrNull() ?: 0f), "µg/m³") to 500f,
                Triple("NO2", (h.nitrogenDioxide?.firstOrNull() ?: 0f), "µg/m³") to 200f,
                Triple("O3", (h.ozone?.firstOrNull() ?: 0f), "µg/m³") to 180f,
                Triple("SO2", (h.sulphurDioxide?.firstOrNull() ?: 0f), "µg/m³") to 350f,
                Triple("CO", (h.carbonMonoxide?.firstOrNull() ?: 0f), "mg/m³") to 10000f,
                Triple("Polen / Alérgenos (${userProfile.alergias})", 38f, "granos/m³") to 100f
            )

            list.forEach { (data, max) ->
                val (name, value, unit) = data
                PollutantRowMinimalAdaptive(name, value, unit, max, theme, isDark)
            }
        }
    }
}

@Composable
fun PollutantRowMinimalAdaptive(
    name: String,
    value: Float,
    unit: String,
    maxValue: Float,
    theme: ThemeColors,
    isDark: Boolean
) {
    val progress = (value / maxValue).coerceIn(0f, 1f)
    val barColor = if (name.contains("PM2.5") || name.contains("Polen")) getRiskInfoAdaptive(value, isDark).color else theme.accentBrand

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, color = theme.textMuted, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "${value.toInt()}",
                    color = theme.textPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    unit,
                    color = theme.textMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
            }
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

@Composable
fun EnvironmentalMetricsGrid(
    estado: ClimaEstado,
    theme: ThemeColors,
    onMetricClick: (String, String, ImageVector) -> Unit
) {
    val hum = estado.clima?.hourly?.humidity?.firstOrNull() ?: 0f
    val temp = estado.clima?.hourly?.temperature?.firstOrNull() ?: 0f
    var co2 = estado.clima?.hourly?.carbonDioxide?.firstOrNull() ?: 0f
    co2 /= 10
    val pm10 = estado.clima?.hourly?.pm10?.firstOrNull() ?: 0f
    val pressure = estado.clima?.hourly?.surfacePressure?.firstOrNull() ?: 1013f

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
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Humedad del Aire",
                        "Es la cantidad de vapor de agua presente en la atmósfera.\n\nImpacto en el deporte al aire libre:\nValores superiores al 70-80% dificultan la evaporación del sudor, elevando la temperatura corporal y provocando fatiga prematura y deshidratación acelerada.",
                        Icons.Outlined.WaterDrop
                    )
                }
            )
            MetricTileAdaptive(
                icon = Icons.Outlined.Thermostat,
                label = "Temperatura",
                value = "${temp.toInt()}°C",
                theme = theme,
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Temperatura Ambiental",
                        "El nivel de calor o frío medido en el entorno.\n\nImpacto en el deporte al aire libre:\nTemperaturas elevadas aumentan el estrés cardiovascular y el riesgo de golpes de calor. En frío extremo, se requiere mayor calentamiento para evitar lesiones musculares.",
                        Icons.Outlined.Thermostat
                    )
                }
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
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Dióxido de Carbono (CO₂)",
                        "Concentración de gas CO₂ en el entorno urbano o natural.\n\nImpacto en el deporte al aire libre:\nConcentraciones altas en zonas de tráfico intenso reducen la oxigenación óptima durante entrenamientos aeróbicos de alta intensidad.",
                        Icons.Outlined.Air
                    )
                }
            )
            MetricTileAdaptive(
                icon = Icons.Outlined.Assessment,
                label = "PM10",
                value = "${pm10.toInt()} µg/m³",
                theme = theme,
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Material Particulado (PM10)",
                        "Partículas sólidas o líquidas de polvo, polen y hollín menores a 10 micrómetros.\n\nImpacto en el deporte al aire libre:\nPenetran en las vías respiratorias superiores, causando irritación, congestión y reducción de la capacidad pulmonar durante carreras o ciclismo.",
                        Icons.Outlined.Assessment
                    )
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTileAdaptive(
                icon = Icons.Outlined.Info,
                label = "Presión Atmosférica",
                value = "${pressure.toInt()} hPa",
                theme = theme,
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Presión Atmosférica",
                        "El peso que ejerce la columna de aire de la atmósfera sobre la superficie terrestre.\n\nImpacto en el deporte al aire libre:\nCambios bruscos de presión pueden influir en la presión arterial, la sensación de fatiga y la oxigenación en deportistas durante entrenamientos prolongados.",
                        Icons.Outlined.Info
                    )
                }
            )
            Box(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun MetricTileAdaptive(
    icon: ImageVector,
    label: String,
    value: String,
    theme: ThemeColors,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = modifier.clickable { onClick() }
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
fun GenerateReportButton(
    theme: ThemeColors,
    isLoggedIn: Boolean,
    onGenerate: () -> Unit
) {
    Button(
        onClick = onGenerate,
        colors = ButtonDefaults.buttonColors(containerColor = theme.accentBrand),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                Icons.Outlined.Assessment,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                "Generar Reporte Próximo Día",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun AeroTrackDropdown(
    label: String,
    selectedValue: String,
    options: List<String>,
    onItemSelected: (String) -> Unit,
    theme: ThemeColors
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = theme.textMuted, fontSize = 13.sp) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        Icons.Outlined.Place,
                        contentDescription = null,
                        tint = theme.accentBrand
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = theme.accentBrand,
                unfocusedBorderColor = theme.borderCard,
                focusedLabelColor = theme.accentBrand,
                focusedTextColor = theme.textPrimary,
                unfocusedTextColor = theme.textPrimary,
                cursorColor = theme.accentBrand,
                focusedContainerColor = theme.bgCard,
                unfocusedContainerColor = theme.bgCard
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            shape = RoundedCornerShape(16.dp),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        ) {}

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(theme.bgCard)
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            color = if (item == selectedValue) theme.accentBrand else theme.textPrimary,
                            fontWeight = if (item == selectedValue) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = theme.textPrimary
                    ),
                    modifier = Modifier.background(if (item == selectedValue) theme.accentBrand.copy(0.12f) else Color.Transparent)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EcoAlertScreenPreview() {
    EcoAlertScreen()
}
