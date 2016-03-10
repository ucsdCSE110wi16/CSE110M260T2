package com.example.user.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Han on 2016/3/9.
 * Description: Given that the user is on the walk_ask page, When the user scale the seekbar, and
 *              press the "NEXT" button, then the user will be directed to the location_ask page.
 */
@RunWith(AndroidJUnit4.class)
public class Scenario_Test_Three {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void DirectToLocation_walk_ask_page(){
        onView(withId(R.id.imageButton)).perform(click());
    }

    @Test
    public void ScaleSeekBarPressNext() {
        onView(withId(R.id.seekBar)).perform(swipeRight());
        onView(withId(R.id.submit_speed)).perform(click());
        onView(withId(R.id.ask_layout)).check(matches(isDisplayed()));
    }
}
