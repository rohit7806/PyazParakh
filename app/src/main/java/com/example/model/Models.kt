package com.example.model

enum class BottomNavTab {
    HOME,
    SCAN,
    STORAGE
}

enum class InspectionProfile {
    CRATE,
    TROLLEY
}

enum class BulbGrade {
    GRADE_A,
    URS,
    REJECT
}

enum class ReticleFilter {
    ALL,
    GRADE_A,
    URS,
    REJECT
}

data class BulbReticle(
    val id: String,
    val gradeType: BulbGrade,
    val label: String,
    val sizeMm: Int,
    val confidence: Int,
    val defectReason: String? = null,
    val leftPercent: Float,
    val topPercent: Float,
    val widthPercent: Float,
    val heightPercent: Float
)

data class InspectionBatch(
    val id: String,
    val gate: String,
    val timestamp: String,
    val relativeTime: String,
    val location: String,
    val farmerName: String,
    val farmerPhone: String,
    val entityLabel: String,
    val totalBulbs: Int,
    val crateLabel: String,
    val weightKg: Double,
    val gradeACount: Int,
    val gradeAPercent: Int,
    val ursCount: Int,
    val ursPercent: Int,
    val rejectCount: Int,
    val rejectPercent: Int,
    val commercialYieldPercent: Int,
    val suggestedFairPrice: Int,
    val statusTag: String,
    val statusTagType: BulbGrade,
    val statusNote: String,
    val imageUrl: String,
    val defectBasalRot: Int,
    val defectBasalRotPercent: Double,
    val defectBlackMold: Int,
    val defectBlackMoldPercent: Double,
    val defectDoubleBulb: Int,
    val defectDoubleBulbPercent: Double,
    val reticles: List<BulbReticle>
)

data class ChaalStack(
    val code: String,
    val name: String,
    val tempC: Double,
    val humidityPercent: Double,
    val ethylenePpm: Int,
    val isAlert: Boolean
)

data class ChaalTelemetryData(
    val chaalId: String,
    val facilityType: String,
    val storedMt: Int,
    val capacityPercent: Int,
    val lastSync: String,
    val hasAlert: Boolean,
    val alertTitle: String,
    val alertLevel: String,
    val alertZone: String,
    val alertDesc: String,
    val aerationFansActive: Boolean,
    val temperatureC: Double,
    val tempOptRange: String,
    val tempDelta: String,
    val humidityPercent: Double,
    val humidityOptRange: String,
    val humidityDelta: String,
    val airflowMs: Double,
    val airflowStatus: String,
    val ethylenePpm: Int,
    val ethyleneLimit: String,
    val stacks: List<ChaalStack>
)
