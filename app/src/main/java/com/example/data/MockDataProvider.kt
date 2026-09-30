package com.example.data

import com.example.model.BulbGrade
import com.example.model.BulbReticle
import com.example.model.ChaalStack
import com.example.model.ChaalTelemetryData
import com.example.model.InspectionBatch

object MockDataProvider {

    const val LOGO_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuCOPG7Yw40JYOvW7W3DpV63S6e2SLsXaQYYVV1ymq-ngdmgyHO56MxFVR8vKkOPQ406F1n4Jxb_sme5GUQlFf5c8IELvM1TGYi_W-k3PGVPtNRmAJ26bB_YxB48t0-Ixp1vbTJMvI04Q7ndCDgpVz1-gzWdsT-K4yJvFxsQD7eUnDFBhgftK5i1-xkgDpQUqF2brYTu43Ov8NcqKDmV52_FYePVbLLYl2JH-RkmQuuS5uwqqvgDatJIcA"
    const val OFFICER_AVATAR_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuBg1UsMUEIdMucryf390IOYvUEq0ijn38gE7ff2m9zg5_1g986VSZTr2vpWN8qEh6qjeeRImS4Su2KKEoVt-7Dqk-lC1ojX95zbO5CTnECsS_YxMwRrpMwdOmvSFd29QtTdLHiyL19Wvx5dnvXs4zjLeuufZGksV7COf58LuIsE1HtHBZ93AjgKXfDPOFeYHkB9AV_PRjZ--0hn3SafVNjnvQ4UsMRKBGub22GOYYDT1IziSlX5cXmldw"
    const val ONION_BATCH_VIEWPORT_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuC0qIG8K8FnivzOpHb1wwonVVisWHqlGA5cWxwj49rCOZAd3KQJDZd248zfkqj6IQSLzNX4_zbGgVmAG0E9gOG1YOYhK49KDok7AanlTX5hyV824p-KqCuWjQuTwvMeYhNpWG3OjC8a8TISreWcpyqlYBIQdTTN44BxZhecxjykGPOWcAJ19Ha_GFi8gb0Q1pmF34bMyzPyfB2BWR72jbV7HTaqUQTfHTBtk3XNbR6s_HDfH2Oi_hFYNg"

    val sampleReticles = listOf(
        BulbReticle(
            id = "R1",
            gradeType = BulbGrade.GRADE_A,
            label = "Grade A • 55mm",
            sizeMm = 55,
            confidence = 98,
            defectReason = null,
            leftPercent = 0.26f,
            topPercent = 0.18f,
            widthPercent = 0.18f,
            heightPercent = 0.24f
        ),
        BulbReticle(
            id = "R2",
            gradeType = BulbGrade.GRADE_A,
            label = "Grade A (52mm) [98%]",
            sizeMm = 52,
            confidence = 98,
            defectReason = null,
            leftPercent = 0.46f,
            topPercent = 0.34f,
            widthPercent = 0.22f,
            heightPercent = 0.28f
        ),
        BulbReticle(
            id = "R3",
            gradeType = BulbGrade.GRADE_A,
            label = "Grade A • 50mm",
            sizeMm = 50,
            confidence = 95,
            defectReason = null,
            leftPercent = 0.14f,
            topPercent = 0.48f,
            widthPercent = 0.16f,
            heightPercent = 0.22f
        ),
        BulbReticle(
            id = "R4",
            gradeType = BulbGrade.URS,
            label = "URS Small (<35mm)",
            sizeMm = 34,
            confidence = 94,
            defectReason = "Sub-35mm Under-sized",
            leftPercent = 0.67f,
            topPercent = 0.22f,
            widthPercent = 0.15f,
            heightPercent = 0.20f
        ),
        BulbReticle(
            id = "R5",
            gradeType = BulbGrade.URS,
            label = "URS • 31mm",
            sizeMm = 31,
            confidence = 92,
            defectReason = "Moderate double heart",
            leftPercent = 0.42f,
            topPercent = 0.68f,
            widthPercent = 0.14f,
            heightPercent = 0.18f
        ),
        BulbReticle(
            id = "R6",
            gradeType = BulbGrade.REJECT,
            label = "Basal Rot Detected",
            sizeMm = 48,
            confidence = 96,
            defectReason = "Fusarium Basal Rot at stem plate",
            leftPercent = 0.64f,
            topPercent = 0.54f,
            widthPercent = 0.18f,
            heightPercent = 0.24f
        ),
        BulbReticle(
            id = "R7",
            gradeType = BulbGrade.REJECT,
            label = "Sprouted Neck",
            sizeMm = 46,
            confidence = 93,
            defectReason = "Vegetative green shoot emergence",
            leftPercent = 0.77f,
            topPercent = 0.40f,
            widthPercent = 0.15f,
            heightPercent = 0.20f
        )
    )

    val initialBatches = listOf(
        InspectionBatch(
            id = "PP-8492",
            gate = "GATE 2",
            timestamp = "Today, 11:24 AM",
            relativeTime = "2 mins ago",
            location = "Lasalgaon APMC Mandi Yard",
            farmerName = "Ramesh Patil",
            farmerPhone = "+91 98231 44812",
            entityLabel = "Farmer: Ramesh Patil",
            totalBulbs = 142,
            crateLabel = "Crate #04",
            weightKg = 48.5,
            gradeACount = 108,
            gradeAPercent = 76,
            ursCount = 24,
            ursPercent = 17,
            rejectCount = 10,
            rejectPercent = 7,
            commercialYieldPercent = 93,
            suggestedFairPrice = 2450,
            statusTag = "Grade A (84%)",
            statusTagType = BulbGrade.GRADE_A,
            statusNote = "Passed For Export",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB5unNx17yIVQYw6EtnUimGdqr2UH5_4Px6DPxBSL0kCY9HY6diSGQOP5eY_BbO9ADJ9fxZHqo2bLRg7YOAwosyDuYQXnPqsX09yu82yZ2OMOTJi4NizadnpKcsg8aoBPbiHUT2ScaXFBA_FNK-fC7YF6T2sWsX8lbk09NuHkL5zj6_QMH0HBDYGUzZgWXeiCdBrs1Ga6EHGvdn8QUCRDqVxGudibgL9KrkzEHGly174-Xnn30Oyk5OIg",
            defectBasalRot = 6,
            defectBasalRotPercent = 4.2,
            defectBlackMold = 3,
            defectBlackMoldPercent = 2.1,
            defectDoubleBulb = 1,
            defectDoubleBulbPercent = 0.7,
            reticles = sampleReticles
        ),
        InspectionBatch(
            id = "PP-8491",
            gate = "GATE 1",
            timestamp = "Today, 11:06 AM",
            relativeTime = "18 mins ago",
            location = "Lasalgaon APMC Mandi Yard",
            farmerName = "Anand Shinde",
            farmerPhone = "+91 98224 55901",
            entityLabel = "Farmer: Anand Shinde",
            totalBulbs = 95,
            crateLabel = "Burlap Sack #12",
            weightKg = 42.0,
            gradeACount = 59,
            gradeAPercent = 62,
            ursCount = 27,
            ursPercent = 28,
            rejectCount = 9,
            rejectPercent = 10,
            commercialYieldPercent = 90,
            suggestedFairPrice = 1850,
            statusTag = "URS Mixed (62%)",
            statusTagType = BulbGrade.URS,
            statusNote = "Re-sorting Suggested",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAhjPkpi0g6VSwNFs-vufCBmfYuDT7asWP74VnYDpnh5xDbQinT6ZzkFiT7k-PGxigIUnlwZnKxrfK1F1Vd1av2psiP2-1Ucde7zPO50a5tCFVd3FH91gVXDO9QpRSntYLyz8zKtX9e_eaWat1RaJSeGaN4XQgNjXSGXVyQRAzhBeU1WKMDxhZUsB3lJ9Y1WonFbQZnCA6boZcdXL8eoqn4sIRVQ1fdAzvbYl-PpQ3U0ivqLW6Jk-pUMw",
            defectBasalRot = 4,
            defectBasalRotPercent = 4.2,
            defectBlackMold = 3,
            defectBlackMoldPercent = 3.1,
            defectDoubleBulb = 2,
            defectDoubleBulbPercent = 2.1,
            reticles = sampleReticles
        ),
        InspectionBatch(
            id = "PP-8488",
            gate = "GATE 3",
            timestamp = "Today, 10:42 AM",
            relativeTime = "42 mins ago",
            location = "Lasalgaon Mandi Platform 2",
            farmerName = "Sahyadri FPO",
            farmerPhone = "+91 94220 18833",
            entityLabel = "Trader: Sahyadri FPO",
            totalBulbs = 180,
            crateLabel = "Bulk Crate #09",
            weightKg = 52.4,
            gradeACount = 164,
            gradeAPercent = 91,
            ursCount = 13,
            ursPercent = 7,
            rejectCount = 3,
            rejectPercent = 2,
            commercialYieldPercent = 98,
            suggestedFairPrice = 2650,
            statusTag = "Grade A (91%)",
            statusTagType = BulbGrade.GRADE_A,
            statusNote = "Passed For Cold Storage",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB5DwJ-muCzXR15ojBFkL_HS7onmcwrRPj3ynq1CSX1sJH1DQKxz1zvD4xHXFXqI54DB2cR3NEiKLwZtA_TPrXfagOIC7RhL9EZmB8-bxKjVd4BhUU6kAQzSsLjjvO0hNJp0B8a3mhtX1ukLsOaAlijVt7lLp6lcVYTO_t1Is2Yyz0oGByY5JCyiXluRgOaN0ljuAQQGrumpPKT7bwLNzwnjce72fJkpSVz6TKG8nK8LbE9csyjyjS0Fw",
            defectBasalRot = 1,
            defectBasalRotPercent = 0.5,
            defectBlackMold = 1,
            defectBlackMoldPercent = 0.5,
            defectDoubleBulb = 1,
            defectDoubleBulbPercent = 0.5,
            reticles = sampleReticles
        ),
        InspectionBatch(
            id = "PP-8485",
            gate = "GATE 2",
            timestamp = "Today, 10:24 AM",
            relativeTime = "1 hr ago",
            location = "Lasalgaon APMC Mandi Yard",
            farmerName = "Balu Gaikwad",
            farmerPhone = "+91 97655 31109",
            entityLabel = "Farmer: Balu Gaikwad",
            totalBulbs = 110,
            crateLabel = "Trolley Bed #01",
            weightKg = 45.0,
            gradeACount = 31,
            gradeAPercent = 28,
            ursCount = 24,
            ursPercent = 22,
            rejectCount = 55,
            rejectPercent = 50,
            commercialYieldPercent = 50,
            suggestedFairPrice = 1200,
            statusTag = "High Reject (28%)",
            statusTagType = BulbGrade.REJECT,
            statusNote = "Sprouting / Basal Rot",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDvsH-q9CydX2KJCGEopimbZzCqmsaI5bR9IZkI8mXehslU9-3-uO4E5kmGx_F-on2Ec-zwuplbV3zRqI7-iv5W1BqmjsYWeLX6hsjMCVsfUaS5-3aqF6ltfB52CpgWvbzY83wyjEJ-IeKR8jNgBwn2IG27aIjEo6yYgDuD-_yMuEKv-dJFUex2x2Qtdutdc6XBSpShzhkD2fVujalNj72iJrYTZr_bxy8lhdBTPoQwZpzsnfuTBCa9WQ",
            defectBasalRot = 32,
            defectBasalRotPercent = 29.0,
            defectBlackMold = 15,
            defectBlackMoldPercent = 13.6,
            defectDoubleBulb = 8,
            defectDoubleBulbPercent = 7.2,
            reticles = sampleReticles
        )
    )

    val initialTelemetry = ChaalTelemetryData(
        chaalId = "Kanda Chaal #07",
        facilityType = "Ventilated Structure — Niphad Yard",
        storedMt = 120,
        capacityPercent = 82,
        lastSync = "Zigbee Hub 03 • 45s ago",
        hasAlert = true,
        alertTitle = "EARLY ROT DETECTION ALERT",
        alertLevel = "MODERATE ELEVATED (Level 2/4)",
        alertZone = "Risk Zone C2",
        alertDesc = "Micro-pocket in Stack C2 indicates trapped humidity (68% RH) and thermal spike (+1.8°C). Spoilage risk rising over 6h horizon.",
        aerationFansActive = false,
        temperatureC = 29.2,
        tempOptRange = "Opt: 24-30°C",
        tempDelta = "+0.4°C / 24h",
        humidityPercent = 65.4,
        humidityOptRange = "Opt: 55-65%",
        humidityDelta = "+4.2% Dew",
        airflowMs = 1.4,
        airflowStatus = "Louvers 4 & 5 Open",
        ethylenePpm = 12,
        ethyleneLimit = "Limit: <25 ppm",
        stacks = listOf(
            ChaalStack(
                code = "A",
                name = "North Wall Tier 1-3",
                tempC = 28.1,
                humidityPercent = 58.0,
                ethylenePpm = 8,
                isAlert = false
            ),
            ChaalStack(
                code = "B",
                name = "Central Bay Tier 1-4",
                tempC = 29.0,
                humidityPercent = 62.0,
                ethylenePpm = 11,
                isAlert = false
            ),
            ChaalStack(
                code = "C2",
                name = "South Bay (Micro-pocket)",
                tempC = 30.8,
                humidityPercent = 68.0,
                ethylenePpm = 21,
                isAlert = true
            )
        )
    )
}
