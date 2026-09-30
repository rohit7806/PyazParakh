package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Warehouse
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MockDataProvider
import com.example.model.BottomNavTab
import com.example.model.BulbGrade
import com.example.ui.theme.GradeABg
import com.example.ui.theme.GradeAGreen
import com.example.ui.theme.GradeAText
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.RejectRed
import com.example.ui.theme.RejectRedAccent
import com.example.ui.theme.RejectRedBg
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.UrsAmber
import com.example.ui.theme.UrsAmberAccent
import com.example.ui.theme.UrsAmberBg

@Composable
fun AppTopBar(
    title: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                if (showBackButton) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .testTag("top_bar_back_button")
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                // App Brand Icon
                AsyncImage(
                    model = MockDataProvider.LOGO_URL,
                    contentDescription = "PyazParakh Logo",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(10.dp))

                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "PyazParakh",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(PrimaryContainer)
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "AI",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Lasalgaon Mandi #4",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            // Pulsing offline ready dot
                            PulsingDot(color = Primary, size = 6.dp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Offline Ready",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                    }
                }
            }

            // Officer Profile Avatar with green online dot
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .testTag("officer_profile_avatar"),
                contentAlignment = Alignment.BottomEnd
            ) {
                AsyncImage(
                    model = MockDataProvider.OFFICER_AVATAR_URL,
                    contentDescription = "Officer Rajesh Kulkarni",
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.primaryContainer, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Primary)
                        .border(1.5.dp, Color.White, CircleShape)
                )
            }
        }
    }
}

@Composable
fun PulsingDot(
    color: Color = Primary,
    size: androidx.compose.ui.unit.Dp = 8.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotScale"
    )

    Box(
        modifier = Modifier
            .size(size)
            .scale(scale)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun SegmentedYieldBar(
    gradeAPercent: Int,
    ursPercent: Int,
    rejectPercent: Int,
    height: androidx.compose.ui.unit.Dp = 8.dp
) {
    val total = (gradeAPercent + ursPercent + rejectPercent).coerceAtLeast(1)
    val aWeight = gradeAPercent.toFloat() / total
    val uWeight = ursPercent.toFloat() / total
    val rWeight = rejectPercent.toFloat() / total

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(99.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        if (aWeight > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(aWeight)
                    .height(height)
                    .background(GradeAGreen)
            )
        }
        if (uWeight > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (rWeight > 0) uWeight / (uWeight + rWeight) else 1f)
                    .height(height)
                    .background(UrsAmberAccent)
            )
        }
        if (rWeight > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .background(RejectRedAccent)
            )
        }
    }
}

@Composable
fun GradeBadge(
    text: String,
    grade: BulbGrade,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, borderColor) = when (grade) {
        BulbGrade.GRADE_A -> Triple(GradeABg, GradeAText, Color(0xFF86EFAC))
        BulbGrade.URS -> Triple(UrsAmberBg, UrsAmber, Color(0xFFFCD34D))
        BulbGrade.REJECT -> Triple(RejectRedBg, RejectRed, Color(0xFFFCA5A5))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(99.dp))
            .padding(horizontal = 10.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = textColor
        )
    }
}

@Composable
fun PyazParakhBottomNav(
    currentTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceContainerLowest,
        tonalElevation = 6.dp,
        modifier = Modifier.testTag("bottom_nav_bar")
    ) {
        NavigationBarItem(
            selected = currentTab == BottomNavTab.HOME,
            onClick = { onTabSelected(BottomNavTab.HOME) },
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomNavTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = "Home",
                    fontWeight = if (currentTab == BottomNavTab.HOME) FontWeight.Bold else FontWeight.Medium
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_tab_home")
        )

        NavigationBarItem(
            selected = currentTab == BottomNavTab.SCAN,
            onClick = { onTabSelected(BottomNavTab.SCAN) },
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomNavTab.SCAN) Icons.Filled.DocumentScanner else Icons.Outlined.DocumentScanner,
                    contentDescription = "Scan"
                )
            },
            label = {
                Text(
                    text = "Scan",
                    fontWeight = if (currentTab == BottomNavTab.SCAN) FontWeight.Bold else FontWeight.Medium
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_tab_scan")
        )

        NavigationBarItem(
            selected = currentTab == BottomNavTab.STORAGE,
            onClick = { onTabSelected(BottomNavTab.STORAGE) },
            icon = {
                Icon(
                    imageVector = if (currentTab == BottomNavTab.STORAGE) Icons.Filled.Warehouse else Icons.Outlined.Warehouse,
                    contentDescription = "Storage"
                )
            },
            label = {
                Text(
                    text = "Storage",
                    fontWeight = if (currentTab == BottomNavTab.STORAGE) FontWeight.Bold else FontWeight.Medium
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Primary,
                selectedTextColor = Primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            ),
            modifier = Modifier.testTag("nav_tab_storage")
        )
    }
}
