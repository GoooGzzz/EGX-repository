package com.egxintelligence.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF081321)
private val Panel = Color(0xFF102136)
private val Teal = Color(0xFF18C4AE)
private val Gold = Color(0xFFD6B46C)
private val Muted = Color(0xFF91A4B8)
private val Up = Color(0xFF41D4A2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EgxApp() }
    }
}

private data class Security(val symbol: String, val name: String, val price: String, val change: String)
private val securities = listOf(
    Security("COMI", "Commercial International Bank", "92.40", "+1.82%"),
    Security("FWRY", "Fawry for Banking Technology", "6.85", "+2.24%"),
    Security("TMGH", "Talaat Moustafa Group", "58.10", "+0.94%"),
    Security("ETEL", "Telecom Egypt", "36.75", "-0.42%"),
    Security("ABUK", "Abu Qir Fertilizers", "61.20", "+0.65%")
)

@Composable
private fun EgxApp() {
    var tab by remember { mutableStateOf("Home") }
    var query by remember { mutableStateOf("") }
    var arabic by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf<Security?>(null) }
    MaterialTheme(colorScheme = darkColorScheme(primary = Teal, background = Navy, surface = Panel)) {
        Scaffold(
            containerColor = Navy,
            topBar = {
                Column(Modifier.fillMaxWidth().background(Panel).padding(18.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(if (arabic) "ذكاء البورصة المصرية" else "EGX INTELLIGENCE", color = Gold, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
                            Text("WHITE-LABEL INVESTOR EXPERIENCE", color = Muted, fontSize = 10.sp)
                        }
                        TextButton(onClick = { arabic = !arabic }) { Text(if (arabic) "EN" else "عربي") }
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(if (arabic) "منصة المستثمر" else "Investor workspace", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    Text("Market overview · DEMO DATA", color = Muted, fontSize = 11.sp)
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Panel) {
                    listOf("Home", "Markets", "Portfolio", "Research", "Account").forEach { item ->
                        NavigationBarItem(
                            selected = tab == item, onClick = { tab = item },
                            icon = { Icon(when (item) {
                                "Home" -> Icons.Default.Home
                                "Markets" -> Icons.Default.ShowChart
                                "Portfolio" -> Icons.Default.PieChart
                                "Research" -> Icons.Default.Article
                                else -> Icons.Default.Person
                            }, contentDescription = item) },
                            label = { Text(item, fontSize = 9.sp) },
                            colors = NavigationBarItemDefaults.colors(selectedIconColor = Teal, selectedTextColor = Teal, indicatorColor = Color(0xFF173B42), unselectedIconColor = Muted, unselectedTextColor = Muted)
                        )
                    }
                }
            }
        ) { padding ->
            when (tab) {
                "Home" -> Home(padding) { tab = "Markets" }
                "Markets" -> Markets(padding, query, { query = it }) { selected = it }
                "Portfolio" -> Portfolio(padding)
                "Research" -> Research(padding)
                else -> Account(padding)
            }
        }
        selected?.let { item ->
            AlertDialog(
                onDismissRequest = { selected = null }, containerColor = Panel,
                title = { Text("${item.symbol} · ${item.name}") },
                text = { Text("Illustrative price EGP ${item.price} (${item.change}). Production details require licensed market data and verified source timestamps.") },
                confirmButton = { TextButton(onClick = { selected = null }) { Text("Close") } }
            )
        }
    }
}

@Composable
private fun Page(padding: PaddingValues, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().padding(padding).background(Navy).padding(horizontal = 16.dp), content = content)
}
@Composable
private fun Metric(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier.background(Panel, RoundedCornerShape(16.dp)).padding(16.dp)) {
        Text(title, color = Muted, fontSize = 11.sp)
        Spacer(Modifier.height(7.dp))
        Text(value, fontSize = 21.sp, fontWeight = FontWeight.Bold)
    }
}
@Composable
private fun DemoNote() {
    Text("DEMO ONLY: all figures are fictional. No live EGX feed, investor account or brokerage execution is connected.", color = Muted, fontSize = 10.sp, modifier = Modifier.padding(vertical = 18.dp))
}
@Composable
private fun Home(padding: PaddingValues, onMarkets: () -> Unit) {
    Page(padding) {
        Spacer(Modifier.height(16.dp))
        Column(Modifier.fillMaxWidth().background(Color(0xFF173A4B), RoundedCornerShape(20.dp)).padding(18.dp)) {
            Text("EGX30 · DEMO INDEX", color = Muted, fontSize = 11.sp)
            Text("31,482.65", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("▲ 1.24%  +385.20 pts", color = Up, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(18.dp))
            Text("SAMPLE DATA · NOT LIVE", color = Gold, fontSize = 10.sp)
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Metric("Turnover · demo", "EGP 2.84B", Modifier.weight(1f))
            Metric("Transactions · demo", "84.2K", Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Market leaders", fontSize = 17.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = onMarkets) { Text("View all") }
        }
        securities.take(3).forEach { SecurityItem(it) {} }
        Metric("Latest disclosure · demo", "Illustrative issuer filing with source and timestamp to be connected.")
        DemoNote()
    }
}
@Composable
private fun Markets(padding: PaddingValues, query: String, onQuery: (String) -> Unit, onSelect: (Security) -> Unit) {
    Page(padding) {
        Spacer(Modifier.height(16.dp))
        Text("MARKETS & SECURITIES", color = Teal, fontWeight = FontWeight.Bold)
        OutlinedTextField(query, onQuery, Modifier.fillMaxWidth().padding(vertical = 12.dp), placeholder = { Text("Search symbol or company") }, singleLine = true)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(securities.filter { it.symbol.contains(query, true) || it.name.contains(query, true) }) { item ->
                SecurityItem(item) { onSelect(item) }
            }
            item { DemoNote() }
        }
    }
}
@Composable
private fun SecurityItem(item: Security, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(14.dp)).clickable(onClick = onClick).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(item.symbol, fontWeight = FontWeight.Bold)
            Text(item.name, color = Muted, fontSize = 10.sp)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("EGP ${item.price}", fontWeight = FontWeight.Bold)
            Text(item.change, color = if (item.change.startsWith("+")) Up else Color(0xFFF07878), fontSize = 11.sp)
        }
    }
}
@Composable
private fun Portfolio(padding: PaddingValues) {
    Page(padding) {
        Spacer(Modifier.height(18.dp))
        Metric("Portfolio value · demo", "EGP 248,650.00")
        Text("▲ 4.82% · +11,450 EGP", color = Up, modifier = Modifier.padding(top = 10.dp))
        listOf("Banks" to "38%", "Real estate" to "27%", "Telecom" to "20%", "Other" to "15%").forEach {
            Row(Modifier.fillMaxWidth().padding(vertical = 14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(it.first, color = Muted); Text(it.second, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider(color = Color(0xFF263B52))
        }
        DemoNote()
    }
}
@Composable
private fun Research(padding: PaddingValues) {
    Page(padding) {
        Spacer(Modifier.height(18.dp))
        Text("RESEARCH & DISCLOSURES", color = Teal, fontWeight = FontWeight.Bold)
        Metric("Issuer disclosure · demo", "Original source and publication timestamp required.")
        Metric("Corporate action · demo", "Dividend and shareholder notices.")
        Metric("Market update · demo", "Published statistics with source attribution.")
        DemoNote()
    }
}
@Composable
private fun Account(padding: PaddingValues) {
    Page(padding) {
        Spacer(Modifier.height(18.dp))
        Text("ACCOUNT & PREFERENCES", color = Teal, fontWeight = FontWeight.Bold)
        Metric("Demo investor", "Unverified demonstration account")
        listOf("Security & devices", "Fees and risk disclosures", "Help and technical complaints", "White-label configuration").forEach {
            Text(it, Modifier.fillMaxWidth().padding(vertical = 16.dp), fontWeight = FontWeight.SemiBold)
            HorizontalDivider(color = Color(0xFF263B52))
        }
        DemoNote()
    }
}
