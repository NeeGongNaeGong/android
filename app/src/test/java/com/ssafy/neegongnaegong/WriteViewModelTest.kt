package com.ssafy.neegongnaegong

import com.ssafy.neegongnaegong.presentation.timer.WriteContract
import com.ssafy.neegongnaegong.presentation.timer.WriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class WriteViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateDialogTags without kmp performance test`() {
        val viewModel = WriteViewModel()

        val time = measureTimeMillis {
            repeat(1000) {
                viewModel.setEvent(
                    WriteContract.Event.OnSearchTextChanged("프")
                )
            }
        }

        println("updateDialogTags 1000회 수행 시간: ${time}ms")
    }


}