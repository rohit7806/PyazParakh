package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.BottomNavTab
import com.example.model.InspectionProfile
import com.example.model.ReticleFilter
import com.example.viewmodel.PyazParakhViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("PyazParakh", appName)
  }

  @Test
  fun `test viewModel initial state and navigation`() {
    val viewModel = PyazParakhViewModel()
    val state = viewModel.uiState.value

    assertEquals(BottomNavTab.HOME, state.currentTab)
    assertEquals(4, state.batches.size)
    assertEquals("PP-8492", state.selectedBatch.id)

    // Switch tab
    viewModel.setTab(BottomNavTab.SCAN)
    assertEquals(BottomNavTab.SCAN, viewModel.uiState.value.currentTab)

    viewModel.setTab(BottomNavTab.STORAGE)
    assertEquals(BottomNavTab.STORAGE, viewModel.uiState.value.currentTab)
  }

  @Test
  fun `test aeration fans toggle and alert dismissal`() {
    val viewModel = PyazParakhViewModel()
    assertFalse(viewModel.uiState.value.isAerationOn)

    viewModel.toggleAerationFans()
    assertTrue(viewModel.uiState.value.isAerationOn)

    viewModel.dismissAlert()
    assertFalse(viewModel.uiState.value.telemetry.hasAlert)
  }

  @Test
  fun `test batch capture flow`() {
    val viewModel = PyazParakhViewModel()
    val initialCount = viewModel.uiState.value.batches.size

    viewModel.onBatchCaptured("Suresh Kadam", "Plastic Crate #09")
    val updatedState = viewModel.uiState.value

    assertEquals(initialCount + 1, updatedState.batches.size)
    assertEquals(BottomNavTab.SCAN, updatedState.currentTab)
    assertEquals("Suresh Kadam", updatedState.selectedBatch.farmerName)
  }
}
