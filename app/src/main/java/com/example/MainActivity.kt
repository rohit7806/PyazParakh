package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.BottomNavTab
import com.example.ui.components.PyazParakhBottomNav
import com.example.ui.dialogs.BulbDiagnosticDialog
import com.example.ui.dialogs.CameraCaptureDialog
import com.example.ui.dialogs.DigitalReceiptDialog
import com.example.ui.dialogs.IoTLogDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ScanDashboardScreen
import com.example.ui.screens.StorageTelemetryScreen
import com.example.ui.theme.PyazParakhTheme
import com.example.viewmodel.PyazParakhViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PyazParakhTheme {
                PyazParakhApp()
            }
        }
    }
}

@Composable
fun PyazParakhApp(
    viewModel: PyazParakhViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Toast feedback handler
    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Android back navigation handler
    BackHandler(enabled = uiState.currentTab != BottomNavTab.HOME) {
        viewModel.setTab(BottomNavTab.HOME)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            PyazParakhBottomNav(
                currentTab = uiState.currentTab,
                onTabSelected = { tab -> viewModel.setTab(tab) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            AnimatedContent(
                targetState = uiState.currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screenTransition"
            ) { tab ->
                when (tab) {
                    BottomNavTab.HOME -> {
                        HomeScreen(
                            uiState = uiState,
                            onCaptureClick = { viewModel.openCaptureDialog() },
                            onSelectProfile = { profile -> viewModel.setInspectionProfile(profile) },
                            onBatchClick = { batch -> viewModel.selectBatch(batch) }
                        )
                    }

                    BottomNavTab.SCAN -> {
                        ScanDashboardScreen(
                            uiState = uiState,
                            onBackClick = { viewModel.setTab(BottomNavTab.HOME) },
                            onReticleFilterChange = { filter -> viewModel.setReticleFilter(filter) },
                            onReticleClick = { reticle -> viewModel.selectReticle(reticle) },
                            onGenerateReceiptClick = { viewModel.openReceiptDialog() },
                            onShareWhatsAppClick = { viewModel.shareWhatsAppReceipt() },
                            onRecalibrateClick = { viewModel.recalibrateCamera() }
                        )
                    }

                    BottomNavTab.STORAGE -> {
                        StorageTelemetryScreen(
                            uiState = uiState,
                            onToggleFans = { viewModel.toggleAerationFans() },
                            onDismissAlert = { viewModel.dismissAlert() },
                            onTriggerSiren = { viewModel.triggerSirenTest() },
                            onOpen7DayLog = { viewModel.open7DayLogDialog() }
                        )
                    }
                }
            }
        }
    }

    // Dialogs
    if (uiState.showReceiptDialog) {
        DigitalReceiptDialog(
            batch = uiState.selectedBatch,
            onDismiss = { viewModel.dismissReceiptDialog() },
            onShare = { viewModel.shareWhatsAppReceipt() }
        )
    }

    if (uiState.showDiagnosticDialog && uiState.selectedReticle != null) {
        BulbDiagnosticDialog(
            reticle = uiState.selectedReticle!!,
            onDismiss = { viewModel.dismissDiagnosticDialog() }
        )
    }

    if (uiState.showCaptureDialog) {
        CameraCaptureDialog(
            onDismiss = { viewModel.dismissCaptureDialog() },
            onCaptured = { name, crate -> viewModel.onBatchCaptured(name, crate) }
        )
    }

    if (uiState.show7DayLogDialog) {
        IoTLogDialog(
            onDismiss = { viewModel.dismiss7DayLogDialog() }
        )
    }
}
