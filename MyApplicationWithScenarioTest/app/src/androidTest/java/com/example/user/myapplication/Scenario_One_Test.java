package com.example.user.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Han on 2016/3/8.
 */
@RunWith(AndroidJUnit4.class)
public class Scenario_One_Test {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void clickUCSDImageButton() {
        onView(withId(R.id.imageButton2)).perform(click());
        onView(withId(R.id.ask_layout)).check(matches(withText("UCSD")));
        onView(withId(R.id.ask_layout)).check(matches(withText("Choose your walking speed:")));
    }
}
