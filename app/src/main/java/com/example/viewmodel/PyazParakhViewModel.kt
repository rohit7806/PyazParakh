package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MockDataProvider
import com.example.model.BottomNavTab
import com.example.model.BulbGrade
import com.example.model.BulbReticle
import com.example.model.ChaalTelemetryData
import com.example.model.InspectionBatch
import com.example.model.InspectionProfile
import com.example.model.ReticleFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PyazParakhUiState(
    val currentTab: BottomNavTab = BottomNavTab.HOME,
    val selectedBatch: InspectionBatch = MockDataProvider.initialBatches.first(),
    val batches: List<InspectionBatch> = MockDataProvider.initialBatches,
    val telemetry: ChaalTelemetryData = MockDataProvider.initialTelemetry,
    val profile: InspectionProfile = InspectionProfile.CRATE,
    val reticleFilter: ReticleFilter = ReticleFilter.ALL,
    val selectedReticle: BulbReticle? = null,
    val isAerationOn: Boolean = false,
    val isSirenTesting: Boolean = false,
    val showReceiptDialog: Boolean = false,
    val showCaptureDialog: Boolean = false,
    val showDiagnosticDialog: Boolean = false,
    val showSirenDialog: Boolean = false,
    val show7DayLogDialog: Boolean = false,
    val toastMessage: String? = null
)

class PyazParakhViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PyazParakhUiState())
    val uiState: StateFlow<PyazParakhUiState> = _uiState.asStateFlow()

    fun setTab(tab: BottomNavTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectBatch(batch: InspectionBatch) {
        _uiState.update {
            it.copy(
                selectedBatch = batch,
                currentTab = BottomNavTab.SCAN,
                reticleFilter = ReticleFilter.ALL,
                selectedReticle = null
            )
        }
    }

    fun setInspectionProfile(profile: InspectionProfile) {
        _uiState.update { it.copy(profile = profile) }
    }

    fun setReticleFilter(filter: ReticleFilter) {
        _uiState.update { it.copy(reticleFilter = filter) }
    }

    fun selectReticle(reticle: BulbReticle?) {
        _uiState.update {
            it.copy(
                selectedReticle = reticle,
                showDiagnosticDialog = reticle != null
            )
        }
    }

    fun dismissDiagnosticDialog() {
        _uiState.update { it.copy(showDiagnosticDialog = false, selectedReticle = null) }
    }

    fun toggleAerationFans() {
        val newState = !_uiState.value.isAerationOn
        _uiState.update {
            it.copy(
                isAerationOn = newState,
                telemetry = it.telemetry.copy(aerationFansActive = newState),
                toastMessage = if (newState) "Ventilation Fan Relay 02 Engaged (Auto-off 45m)" else "Ventilation Fans Disengaged"
            )
        }
    }

    fun dismissAlert() {
        _uiState.update {
            it.copy(
                telemetry = it.telemetry.copy(hasAlert = false),
                toastMessage = "Early Rot Alert dismissed for Stack C2"
            )
        }
    }

    fun triggerSirenTest() {
        _uiState.update { it.copy(isSirenTesting = true, showSirenDialog = true) }
        viewModelScope.launch {
            delay(3000)
            _uiState.update { it.copy(isSirenTesting = false) }
        }
    }

    fun dismissSirenDialog() {
        _uiState.update { it.copy(showSirenDialog = false, isSirenTesting = false) }
    }

    fun open7DayLogDialog() {
        _uiState.update { it.copy(show7DayLogDialog = true) }
    }

    fun dismiss7DayLogDialog() {
        _uiState.update { it.copy(show7DayLogDialog = false) }
    }

    fun openCaptureDialog() {
        _uiState.update { it.copy(showCaptureDialog = true) }
    }

    fun dismissCaptureDialog() {
        _uiState.update { it.copy(showCaptureDialog = false) }
    }

    fun onBatchCaptured(farmerName: String, crateType: String) {
        val newId = "PP-${(8493..8599).random()}"
        val newBatch = InspectionBatch(
            id = newId,
            gate = "GATE 2",
            timestamp = "Just Now",
            relativeTime = "Just now",
            location = "Lasalgaon APMC Mandi Yard",
            farmerName = farmerName.ifBlank { "Vikram Deshmukh" },
            farmerPhone = "+91 98211 ${(10000..99999).random()}",
            entityLabel = "Farmer: ${farmerName.ifBlank { "Vikram Deshmukh" }}",
            totalBulbs = 138,
            crateLabel = crateType.ifBlank { "Crate #05" },
            weightKg = 46.8,
            gradeACount = 112,
            gradeAPercent = 81,
            ursCount = 18,
            ursPercent = 13,
            rejectCount = 8,
            rejectPercent = 6,
            commercialYieldPercent = 94,
            suggestedFairPrice = 2520,
            statusTag = "Grade A (81%)",
            statusTagType = BulbGrade.GRADE_A,
            statusNote = "Passed For Export",
            imageUrl = MockDataProvider.ONION_BATCH_VIEWPORT_URL,
            defectBasalRot = 5,
            defectBasalRotPercent = 3.6,
            defectBlackMold = 2,
            defectBlackMoldPercent = 1.4,
            defectDoubleBulb = 1,
            defectDoubleBulbPercent = 0.7,
            reticles = MockDataProvider.sampleReticles
        )

        _uiState.update {
            it.copy(
                batches = listOf(newBatch) + it.batches,
                selectedBatch = newBatch,
                showCaptureDialog = false,
                currentTab = BottomNavTab.SCAN,
                toastMessage = "Batch #$newId analyzed successfully!"
            )
        }
    }

    fun openReceiptDialog() {
        _uiState.update { it.copy(showReceiptDialog = true) }
    }

    fun dismissReceiptDialog() {
        _uiState.update { it.copy(showReceiptDialog = false) }
    }

    fun shareWhatsAppReceipt() {
        val batch = _uiState.value.selectedBatch
        _uiState.update {
            it.copy(toastMessage = "Dispatching WhatsApp Grade Summary to ${batch.farmerName} (${batch.farmerPhone})")
        }
    }

    fun recalibrateCamera() {
        _uiState.update {
            it.copy(toastMessage = "Optical calibration matrix reset. Ready for scan.")
        }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
