package com.example.demoone.ui.home

import android.view.View
import android.widget.ProgressBar
import com.example.demoone.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

    private lateinit var activity: HomeActivity

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(HomeActivity::class.java)
            .create().resume().get()
    }

    @Test
    fun shouldNotBeNull() {
        assertNotNull(activity)
    }

//    @Test
//    fun startingActivity_shouldShowSplashFragment() {
//        assertNotNull(activity.supportFragmentManager.findFragmentById(R.id.registrationFragment))
//    }

    @Test
    fun startingActivity_shouldHideProgressBar() {
        val progressBar = activity.findViewById<ProgressBar>(R.id.progress_bar)
        assertThat(progressBar.visibility, `is`(equalTo(View.INVISIBLE)))
    }

}