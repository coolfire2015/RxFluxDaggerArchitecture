package com.huyingbao.module.github.ui.login.view

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import com.huyingbao.test.utils.FragmentScenarioRule
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


/**
 * Created by liujunfeng on 2019/3/28.
 */
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class LoginFragmentTest {
    @get:Rule
    var scenarioRule: FragmentScenarioRule<LoginActivity, LoginFragment> = FragmentScenarioRule(
            LoginActivity::class.java,
            LoginFragment::class.java,
            null,
            null,
            R.id.fl_container)

    @Test
    fun testLogin() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onFragment { onView(allOf(isDisplayed(), withId(R.id.btn_login))).check(matches(withText(R.string.github_label_login))) }
    }

    @Test
    fun testCreated() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.mActionCreator) }
    }
}