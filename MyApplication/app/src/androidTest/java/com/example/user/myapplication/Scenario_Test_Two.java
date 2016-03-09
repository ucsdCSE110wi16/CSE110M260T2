package com.example.user.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Han on 2016/3/9.
 * Description: Given that user is on the location_ask page, When user input his/her starting address
 *              and destination address, and press the button, "Go" , then the user will be directed
 *              to a Google Map Page.When user input the Route ID and press "FIND STOP" button,
 *              then the user will be directed to a Google Map Page. When user input the Stop ID and
 *              press "FIND STOP" button, then the user will be directed to a Google Map Page.
 *
 */
@RunWith(AndroidJUnit4.class)
public class Scenario_Test_Two {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void DirectToLocation_ask_page(){
        onView(withId(R.id.imageButton)).perform(click());
        onView(withId(R.id.submit_speed)).perform(click());
    }

    @Test
    public void clickGoButtonWithAddress(){
        onView(withId(R.id.auto_ucsd)).perform(click());
        onView(withId(R.id.destination)).perform(typeText("UCSD"), closeSoftKeyboard());
        onView(withId(R.id.enter_ucsd)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void clickFindStopButtonByRouteId(){
        onView(withId(R.id.routeID)).perform(typeText("201"), closeSoftKeyboard());
        onView(withId(R.id.findStopBut)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void clickFindStopButtonByStopId(){
        onView(withId(R.id.stopID)).perform(typeText("11456"), closeSoftKeyboard());
        onView(withId(R.id.findStopBut)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }
}
