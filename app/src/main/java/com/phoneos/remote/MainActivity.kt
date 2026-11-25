package com.phoneos.remote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF10131B)
private val Panel = Color(0xFF1A2030)
private val Accent = Color(0xFF7C9CFF)
private val Muted = Color(0xFF9AA5BA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PhoneOsApp() }
    }
}

@Composable
private fun PhoneOsApp() {
    var role by remember { mutableStateOf("Controller") }
    var connection by remember { mutableStateOf("Automatic") }
    var showInfo by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Accent,
            background = Ink,
            surface = Panel,
            onPrimary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Surface(Modifier.fillMaxSize(), color = Ink) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("PHONEOS", fontSize = 29.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                    Text("REMOTE CONTROL", color = Accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                }

                Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(22.dp)) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Choose this phone's role", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            RoleButton("Controller", role == "Controller", Modifier.weight(1f)) { role = "Controller" }
                            RoleButton("Host", role == "Host", Modifier.weight(1f)) { role = "Host" }
                        }
                        Text(
                            if (role == "Host") "This is the phone you want to control."
                            else "This is the phone you use to control another device.",
                            color = Muted,
                            fontSize = 13.sp
                        )
                    }
                }

                Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(22.dp)) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Connection method", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        listOf("Automatic", "Same Wi-Fi", "Phone hotspot", "Wi-Fi Direct").forEach { option ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = connection == option,
                                    onClick = { connection = option },
                                    colors = RadioButtonDefaults.colors(selectedColor = Accent)
                                )
                                Text(option, fontSize = 14.sp)
                            }
                        }
                        Text("Automatic will choose from the available local connection methods.", color = Muted, fontSize = 12.sp)
                    }
                }

                Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(22.dp)) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Device connection", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(9.dp).background(Color(0xFFFFC15A), RoundedCornerShape(50)))
                            Spacer(Modifier.width(8.dp))
                            Text("Not connected", color = Muted, fontSize = 13.sp)
                        }
                        Button(
                            onClick = { showInfo = true },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Start pairing", fontWeight = FontWeight.Bold)
                        }
                        Text("Pairing is not active in this prototype yet.", color = Muted, fontSize = 11.sp)
                    }
                }

                Text("PLANNED FEATURES", color = Accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    FeatureRow("Live screen mirroring", "View the host phone screen")
                    FeatureRow("Remote touch & typing", "Tap, swipe and enter text")
                    FeatureRow("File transfer", "Send files over a local connection")
                    FeatureRow("Device status", "Battery and connection information")
                    FeatureRow("Device actions", "Launch apps and supported controls")
                }
                Text("PHONEOS Remote • Early prototype", color = Muted, fontSize = 11.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        if (showInfo) {
            AlertDialog(
                onDismissRequest = { showInfo = false },
                title = { Text("Pairing is coming next") },
                text = { Text("This build is the interface prototype. No device discovery, screen sharing, or remote input is connected yet.") },
                confirmButton = { TextButton(onClick = { showInfo = false }) { Text("Got it") } },
                containerColor = Panel
            )
        }
    }
}

@Composable
private fun RoleButton(label: String, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Accent.copy(alpha = 0.16f) else Color.Transparent,
            contentColor = if (selected) Accent else Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (selected) Accent else Color(0xFF394256)
        )
    ) { Text(label, fontWeight = FontWeight.SemiBold) }
}

@Composable
private fun FeatureRow(title: String, subtitle: String) {
    Row(
        Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(14.dp)).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(8.dp).background(Accent, RoundedCornerShape(50.dp)))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(subtitle, color = Muted, fontSize = 11.sp)
        }
        Spacer(Modifier.weight(1f))
        Text("PLANNED", color = Muted, fontSize = 9.sp, letterSpacing = 1.sp)
    }
}
