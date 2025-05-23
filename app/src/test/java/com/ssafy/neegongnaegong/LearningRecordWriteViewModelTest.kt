package com.ssafy.neegongnaegong

import com.ssafy.neegongnaegong.presentation.timer.learning.LearningRecordWriteContract
import com.ssafy.neegongnaegong.presentation.timer.learning.LearningRecordWriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class LearningRecordWriteViewModelTest {

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
    fun updateDialogTagsWithoutKmpPerformanceTest() {
        val viewModel = LearningRecordWriteViewModel()

        val time = measureTimeMillis {
            repeat(10000) {
                viewModel.setEvent(
                    LearningRecordWriteContract.Event.OnSearchTextChanged("aaa")
                )
            }
        }

        println("updateDialogTags (기본 contains) 1000회 수행 시간: ${time}ms")
    }

    @Test
    fun updateDialogTagsWithKmpPerformanceTest() {
        val viewModel = LearningRecordWriteViewModel()

        val time = measureTimeMillis {
            repeat(10000) {
                viewModel.setEvent(
                    LearningRecordWriteContract.Event.OnSearchTextChangedWithKmp("aaa")
                )
            }
        }

        println("updateDialogTags (KMP) 1000회 수행 시간: ${time}ms")
    }


}
