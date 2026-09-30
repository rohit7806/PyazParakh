package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallSplit
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Coronavirus
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MockDataProvider
import com.example.model.BulbGrade
import com.example.model.BulbReticle
import com.example.model.InspectionBatch
import com.example.model.ReticleFilter
import com.example.ui.components.AppTopBar
import com.example.ui.components.PulsingDot
import com.example.ui.components.SegmentedYieldBar
import com.example.ui.theme.Error
import com.example.ui.theme.GradeABg
import com.example.ui.theme.GradeAGreen
import com.example.ui.theme.GradeAText
import com.example.ui.theme.InverseSurface
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.RejectRed
import com.example.ui.theme.RejectRedBg
import com.example.ui.theme.Secondary
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.UrsAmber
import com.example.ui.theme.UrsAmberAccent
import com.example.ui.theme.UrsAmberBg
import com.example.viewmodel.PyazParakhUiState

@Composable
fun ScanDashboardScreen(
    uiState: PyazParakhUiState,
    onBackClick: () -> Unit,
    onReticleFilterChange: (ReticleFilter) -> Unit,
    onReticleClick: (BulbReticle) -> Unit,
    onGenerateReceiptClick: () -> Unit,
    onShareWhatsAppClick: () -> Unit,
    onRecalibrateClick: () -> Unit
) {
    val batch = uiState.selectedBatch

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AppTopBar(
            title = "Batch Inspection Detail",
            showBackButton = true,
            onBackClick = onBackClick
        )

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                // 1. Top Batch Header & Context
                item {
                    BatchHeaderContextCard(batch = batch)
                }

                // 2. AI Viewport Container with Detection Overlay
                item {
                    AiDetectionViewportCard(
                        batch = batch,
                        activeFilter = uiState.reticleFilter,
                        onFilterSelect = onReticleFilterChange,
                        onReticleClick = onReticleClick
                    )
                }

                // 3. Data Dashboard Section (Grading Breakdown)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Grading Breakdown",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Primary.copy(alpha = 0.12f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Standard APMC-V4",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Primary
                            )
                        }
                    }
                }

                // 4. Hero Total Counter Metric Card
                item {
                    HeroTotalCounterCard(batch = batch)
                }

                // 5. Segmented Defect Distribution Gauge Card
                item {
                    DefectDistributionGaugeCard(batch = batch)
                }

                // 6. 3-Tier Grade Breakdown Cards
                item {
                    GradeTiersCards(batch = batch)
                }

                // 7. Identified Defect Telemetry Mini List
                item {
                    DefectTelemetryCard(batch = batch)
                }

                // 8. Suggested APMC Fair Price Recommendation Banner
                item {
                    FairPriceBanner(batch = batch)
                }

                // 9. Auxiliary Action Buttons (WhatsApp Share & Recalibrate)
                item {
                    AuxiliaryActionButtons(
                        farmerPhone = batch.farmerPhone,
                        onShareWhatsApp = onShareWhatsAppClick,
                        onRecalibrate = onRecalibrateClick
                    )
                }

                item { Spacer(modifier = Modifier.height(84.dp)) }
            }

            // Bottom Sticky Primary CTA Dock: "Generate Digital Receipt"
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Button(
                        onClick = onGenerateReceiptClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("generate_digital_receipt_button"),
                        shape = RoundedCornerShape(99.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryContainer,
                            contentColor = OnPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ReceiptLong,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Generate Digital Receipt",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BatchHeaderContextCard(batch: InspectionBatch) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("batch_header_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Primary.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Verified,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Lot #${batch.id}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(99.dp))
                        .background(SurfaceContainer)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        PulsingDot(color = PrimaryContainer, size = 6.dp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = batch.gate,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.secondary,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = batch.timestamp,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "•",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = batch.location,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun AiDetectionViewportCard(
    batch: InspectionBatch,
    activeFilter: ReticleFilter,
    onFilterSelect: (ReticleFilter) -> Unit,
    onReticleClick: (BulbReticle) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ai_detection_viewport_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Viewport Box with Reticle Overlays
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .background(InverseSurface)
            ) {
                val boxWidth = maxWidth
                val boxHeight = maxHeight

                // Base Onion Image
                AsyncImage(
                    model = MockDataProvider.ONION_BATCH_VIEWPORT_URL,
                    contentDescription = "Onion Inspection Batch in Crate",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dark Contrast Scrim for glare protection
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    InverseSurface.copy(alpha = 0.45f),
                                    Color.Transparent,
                                    InverseSurface.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                // Top Status telemetry pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(99.dp))
                            .background(InverseSurface.copy(alpha = 0.85f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.CenterFocusStrong,
                                contentDescription = null,
                                tint = PrimaryFixed,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Live Reticles",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(99.dp))
                            .background(InverseSurface.copy(alpha = 0.85f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.SmartToy,
                                contentDescription = null,
                                tint = PrimaryFixed,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Model v4.2 • 97.8% Conf",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                // AI Bounding Box Overlays
                batch.reticles.forEach { reticle ->
                    val isVisible = when (activeFilter) {
                        ReticleFilter.ALL -> true
                        ReticleFilter.GRADE_A -> reticle.gradeType == BulbGrade.GRADE_A
                        ReticleFilter.URS -> reticle.gradeType == BulbGrade.URS
                        ReticleFilter.REJECT -> reticle.gradeType == BulbGrade.REJECT
                    }

                    if (isVisible) {
                        BoundingReticleBox(
                            reticle = reticle,
                            parentWidth = boxWidth,
                            parentHeight = boxHeight,
                            onClick = { onReticleClick(reticle) }
                        )
                    }
                }

                // Bottom Hint Bar inside Viewport
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(InverseSurface.copy(alpha = 0.8f))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.TouchApp,
                                contentDescription = null,
                                tint = PrimaryFixed,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Tap boxes to inspect bulb diagnostics",
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "${batch.totalBulbs} Found",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryFixed
                        )
                    }
                }
            }

            // Segment Filter Pill Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainerLow)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // All
                FilterTabPill(
                    label = "All",
                    count = batch.totalBulbs.toString(),
                    dotColor = null,
                    isSelected = activeFilter == ReticleFilter.ALL,
                    onClick = { onFilterSelect(ReticleFilter.ALL) },
                    modifier = Modifier.weight(1f)
                )

                // Grade A
                FilterTabPill(
                    label = "Grade A",
                    count = batch.gradeACount.toString(),
                    dotColor = PrimaryContainer,
                    isSelected = activeFilter == ReticleFilter.GRADE_A,
                    onClick = { onFilterSelect(ReticleFilter.GRADE_A) },
                    modifier = Modifier.weight(1.1f)
                )

                // URS
                FilterTabPill(
                    label = "URS",
                    count = batch.ursCount.toString(),
                    dotColor = UrsAmberAccent,
                    isSelected = activeFilter == ReticleFilter.URS,
                    onClick = { onFilterSelect(ReticleFilter.URS) },
                    modifier = Modifier.weight(1f)
                )

                // Reject
                FilterTabPill(
                    label = "Reject",
                    count = batch.rejectCount.toString(),
                    dotColor = Error,
                    isSelected = activeFilter == ReticleFilter.REJECT,
                    onClick = { onFilterSelect(ReticleFilter.REJECT) },
                    modifier = Modifier.weight(1.1f)
                )
            }
        }
    }
}

@Composable
private fun BoundingReticleBox(
    reticle: BulbReticle,
    parentWidth: androidx.compose.ui.unit.Dp,
    parentHeight: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit
) {
    val (borderColor, bgColor, tagColor) = when (reticle.gradeType) {
        BulbGrade.GRADE_A -> Triple(PrimaryContainer, PrimaryContainer.copy(alpha = 0.2f), PrimaryContainer)
        BulbGrade.URS -> Triple(UrsAmberAccent, UrsAmberAccent.copy(alpha = 0.2f), UrsAmber)
        BulbGrade.REJECT -> Triple(RejectRed, RejectRed.copy(alpha = 0.25f), RejectRed)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulseReticle")
    val pulseScale by if (reticle.gradeType == BulbGrade.REJECT) {
        infiniteTransition.animateFloat(
            initialValue = 0.98f,
            targetValue = 1.02f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "rejectPulse"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    Box(
        modifier = Modifier
            .offset(
                x = parentWidth * reticle.leftPercent,
                y = parentHeight * reticle.topPercent
            )
            .width(parentWidth * reticle.widthPercent)
            .height(parentHeight * reticle.heightPercent)
            .scale(pulseScale)
            .clip(RoundedCornerShape(6.dp))
            .border(2.dp, borderColor, RoundedCornerShape(6.dp))
            .background(bgColor)
            .clickable { onClick() }
    ) {
        // Tag label at top
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = (-18).dp)
                .clip(RoundedCornerShape(4.dp))
                .background(tagColor)
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (reticle.gradeType == BulbGrade.REJECT) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }
                Text(
                    text = reticle.label,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }

        // Corner indicator dot
        Box(
            modifier = Modifier
                .size(5.dp)
                .align(Alignment.TopStart)
                .offset(x = (-2).dp, y = (-2).dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

@Composable
private fun FilterTabPill(
    label: String,
    count: String,
    dotColor: Color?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (isSelected) InverseSurface else SurfaceContainerLowest
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(99.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (dotColor != null) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = count,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White.copy(alpha = 0.75f) else MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun HeroTotalCounterCard(batch: InspectionBatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "TOTAL BULBS SCANNED",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${batch.totalBulbs}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Bulbs in ${batch.crateLabel}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.GridView,
                    contentDescription = null,
                    tint = PrimaryContainer,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
private fun DefectDistributionGaugeCard(batch: InspectionBatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Batch Proportions",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${batch.commercialYieldPercent}% Commercial Yield",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            SegmentedYieldBar(
                gradeAPercent = batch.gradeAPercent,
                ursPercent = batch.ursPercent,
                rejectPercent = batch.rejectPercent,
                height = 10.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(GradeAGreen)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${batch.gradeAPercent}% Premium",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(UrsAmberAccent)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${batch.ursPercent}% Table URS",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(RejectRed)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${batch.rejectPercent}% Rejection",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun GradeTiersCards(batch: InspectionBatch) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Grade A Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainer)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Grade A",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(99.dp))
                                .background(GradeABg)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${batch.gradeACount} Bulbs",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = GradeAText
                            )
                        }
                    }

                    Text(
                        text = "${batch.gradeAPercent}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Primary
                    )
                }

                Text(
                    text = "Export & Premium Market standard. Uniform 45–65mm diameter, dry intact skins, tight necks.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 16.sp
                )
            }
        }

        // URS Grade Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(UrsAmberAccent)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "URS Grade",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(99.dp))
                                .background(UrsAmberBg)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${batch.ursCount} Bulbs",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = UrsAmber
                            )
                        }
                    }

                    Text(
                        text = "${batch.ursPercent}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = UrsAmber
                    )
                }

                Text(
                    text = "Under-sized produce (<35mm) & moderate doubles. Suited for domestic table retail or dehydration.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 16.sp
                )
            }
        }

        // Defects & Rejects Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(RejectRed)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Defects & Rejects",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(99.dp))
                                .background(RejectRedBg)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${batch.rejectCount} Bulbs",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = RejectRed
                            )
                        }
                    }

                    Text(
                        text = "${batch.rejectPercent}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RejectRed
                    )
                }

                Text(
                    text = "Unfit for storage. Shows active rot, fungal lesions, or vegetative sprouting.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun DefectTelemetryCard(batch: InspectionBatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "IDENTIFIED DEFECT TELEMETRY",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = 0.5.sp
            )

            // Defect 1: Basal Rot
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Coronavirus,
                        contentDescription = null,
                        tint = Error,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Basal Rot",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${batch.defectBasalRot} bulbs",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(RejectRedBg)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${batch.defectBasalRotPercent}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RejectRed
                        )
                    }
                }
            }

            HorizontalDivider(color = SurfaceContainer)

            // Defect 2: Black Mold
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Grain,
                        contentDescription = null,
                        tint = Error,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Black Mold (Aspergillus)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${batch.defectBlackMold} bulbs",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(RejectRedBg)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${batch.defectBlackMoldPercent}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RejectRed
                        )
                    }
                }
            }

            HorizontalDivider(color = SurfaceContainer)

            // Defect 3: Double Bulbing
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CallSplit,
                        contentDescription = null,
                        tint = UrsAmber,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Double Bulbing",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${batch.defectDoubleBulb} bulb",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${batch.defectDoubleBulbPercent}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FairPriceBanner(batch: InspectionBatch) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Primary, PrimaryContainer)
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SUGGESTED APMC FAIR PRICE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${batch.suggestedFairPrice}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "/ Quintal",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.TrendingUp,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Premium Grade A Batch Index",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CurrencyRupee,
                        contentDescription = "Rupee",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AuxiliaryActionButtons(
    farmerPhone: String,
    onShareWhatsApp: () -> Unit,
    onRecalibrate: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable { onShareWhatsApp() },
            color = SurfaceContainerLowest,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    tint = Primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Share via WhatsApp to Farmer ($farmerPhone)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onRecalibrate() }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.FlipCameraAndroid,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Recalibrate / Take Rescan Photo",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
