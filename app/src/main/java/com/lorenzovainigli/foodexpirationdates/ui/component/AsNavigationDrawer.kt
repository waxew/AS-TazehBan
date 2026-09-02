package com.lorenzovainigli.foodexpirationdates.ui.component

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.lorenzovainigli.foodexpirationdates.BuildConfig
import kotlinx.coroutines.launch

private const val AS_APP_NAME = "تازه‌بان"
private const val AS_SUPPORT_EMAIL = "AS.Developers.Support@Gmail.Com"

/**
 * Unified AS Team product drawer.
 *
 * Material opens its "start" drawer from the right while the drawer is in RTL. The imported
 * application content keeps its own locale direction, so enabling the AS drawer does not change
 * the layout of existing screens before their Persian localization is completed.
 */
@Composable
fun AsNavigationDrawer(
    onHome: () -> Unit,
    onSettings: () -> Unit,
    onTheme: () -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val contentDirection = LocalLayoutDirection.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showAbout by remember { mutableStateOf(false) }

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    fun closeThen(action: () -> Unit) {
        scope.launch {
            drawerState.close()
            action()
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = 328.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 24.dp, vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Surface(
                            modifier = Modifier.size(80.dp),
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = "پروفایل",
                                    modifier = Modifier.size(60.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                        }
                        Text(
                            AS_APP_NAME,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            "AS Team",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            "نسخه ${BuildConfig.VERSION_NAME}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(Modifier.height(8.dp))

                    NavigationDrawerItem(
                        label = { Text("خانه") },
                        selected = false,
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        onClick = { closeThen(onHome) },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    NavigationDrawerItem(
                        label = { Text("تنظیمات") },
                        selected = false,
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        onClick = { closeThen(onSettings) },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    NavigationDrawerItem(
                        label = { Text("پوسته و ظاهر") },
                        selected = false,
                        icon = { Icon(Icons.Default.DarkMode, contentDescription = null) },
                        onClick = { closeThen(onTheme) },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    NavigationDrawerItem(
                        label = { Text("اشتراک‌گذاری برنامه") },
                        selected = false,
                        icon = { Icon(Icons.Default.Share, contentDescription = null) },
                        onClick = {
                            closeThen {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "$AS_APP_NAME — مدیریت تاریخ مصرف و تازگی مواد غذایی، توسعه توسط AS Team",
                                    )
                                }
                                context.startActivity(Intent.createChooser(intent, "اشتراک‌گذاری $AS_APP_NAME"))
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    NavigationDrawerItem(
                        label = { Text("تماس با ما") },
                        selected = false,
                        icon = { Icon(Icons.Default.Email, contentDescription = null) },
                        onClick = {
                            closeThen {
                                runCatching {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_SENDTO,
                                            Uri.parse("mailto:$AS_SUPPORT_EMAIL?subject=$AS_APP_NAME"),
                                        )
                                    )
                                }
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    NavigationDrawerItem(
                        label = { Text("درباره نرم‌افزار") },
                        selected = false,
                        icon = { Icon(Icons.Default.Info, contentDescription = null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            showAbout = true
                        },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "Develop by AS Team Group • نسخه ${BuildConfig.VERSION_NAME}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    )
                }
            },
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides contentDirection) {
                Box(Modifier.fillMaxSize()) {
                    content()
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .statusBarsPadding()
                            .padding(top = 8.dp, end = 8.dp),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = 4.dp,
                        shadowElevation = 2.dp,
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "باز کردن منوی اصلی")
                        }
                    }
                }
            }
        }
    }

    if (showAbout) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            AlertDialog(
                onDismissRequest = { showAbout = false },
                confirmButton = {
                    TextButton(onClick = { showAbout = false }) { Text("بستن") }
                },
                icon = { Icon(Icons.Default.Info, contentDescription = null) },
                title = { Text(AS_APP_NAME) },
                text = {
                    Text(
                        "$AS_APP_NAME برای ثبت مواد غذایی و پیگیری تاریخ مصرف و تازگی آن‌ها طراحی شده است.\n\n" +
                            "Develop by AS Team Group\n" +
                            "پشتیبانی: $AS_SUPPORT_EMAIL\n" +
                            "نسخه: ${BuildConfig.VERSION_NAME}"
                    )
                },
            )
        }
    }
}
