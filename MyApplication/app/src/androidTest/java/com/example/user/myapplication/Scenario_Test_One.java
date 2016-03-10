package com.example.user.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Han on 2016/3/9.
 * Description: Given that user open the app, when user clicks the MTS image button, then he/she
 *              will be directed to a new page where he/she can choose his/her walking speed; When
 *              the user clicks the UCSD image button, then he/she will be directed to a new page
 *              where he/she can choose his/her walking speed.
 */
@RunWith(AndroidJUnit4.class)
public class Scenario_Test_One {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void clickUCSDImageButton() {
        onView(withId(R.id.imageButton2)).perform(click());
        onView(withId(R.id.ask_bus_type)).check(matches(withText("UCSD")));
        onView(withId(R.id.walk_ask)).check(matches(withText("Choose your walking speed:")));
        onView(withId(R.id.submit_speed)).check(matches(withText("Next")));
        onView(withId(R.id.speed)).check(matches(withContentDescription("speed_img")));
    }

    @Test
    public void clickMTSImageButton(){
        onView(withId(R.id.imageButton)).perform(click());
        onView(withId(R.id.ask_bus_type)).check(matches(withText("MTS")));
        onView(withId(R.id.walk_ask)).check(matches(withText("Choose your walking speed:")));
        onView(withId(R.id.speed)).check(matches(withContentDescription("speed_img")));
        onView(withId(R.id.submit_speed)).check(matches(withText("Next")));
    }

}
