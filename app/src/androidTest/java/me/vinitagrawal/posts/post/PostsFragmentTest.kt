package me.vinitagrawal.posts.post

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains
import me.vinitagrawal.posts.HomeActivity
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.UrlMap
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PostsFragmentTest {

    @JvmField
    @Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java, false, false)

    @Before
    fun setUp() {
        RESTMockServer.reset()
    }

    @Test
    fun shouldShowPostsFetchedFromServer() {
        RESTMockServer.whenGET(pathContains(UrlMap.POSTS_URL))
            .thenReturnFile(200, "posts.json")

        activityRule.launchActivity(null)

        onView(withId(R.id.postsView))
                .check(matches(hasDescendant(withText("sample time"))))
    }

    @Test
    fun shouldShowEmptyStateWhenThereAreNoPosts() {
        activityRule.launchActivity(null)

        onView(withId(R.id.noDataView))
                .check(matches(CoreMatchers.allOf(isDisplayed(),
                        withText("No Posts"))))
    }
}