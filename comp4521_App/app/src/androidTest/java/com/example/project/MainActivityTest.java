package com.example.project;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mMainActivity = new ActivityTestRule<>(MainActivity.class);

    private String description = "just a description";
    @Test
    public void testView() {
        Espresso.onView(withId(R.id.returntomain)).check(matches(isDisplayed()));
    }
    @Test
    public void testclickDate() {
        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(0).perform(click());
////        Espresso.onView(withId(R.id.noteTitle)).perform(typeText(Title));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.delete)).perform(click());
    }

//    @Test
//    public void testAddEmptyEvent() {
//        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(0).perform(click());
//        Espresso.onView(withId(R.id.addevent)).perform(click());
//        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(0).perform(longClick());
//        Espresso.onView(withText("Time: 00:00AM")).check(matches(isDisplayed()));
//        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(0).perform(longClick());
//        Espresso.onView(withId(R.id.deleteevent)).perform(click());
//    }



//    @Test
//    public void testAddNonEmptyEvent() {
//        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(5).perform(click());
//        Espresso.onView(withId(R.id.description)).perform(typeText(description));
//        Espresso.closeSoftKeyboard();
//        Espresso.onView(withId(R.id.addevent)).perform(click());
//        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(5).perform(longClick());
//        Espresso.onView(withText("Description: " + description)).check(matches(isDisplayed()));
//        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(5).perform(longClick());
//        Espresso.onView(withId(R.id.deleteevent)).perform(click());
//    }


    @Test
    public void testScheduleStructure() {
        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(5).perform(longClick());
        pressBack();
        Espresso.onView(withId(R.id.returntomain)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyEvent() {
        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(2).perform(click());
        Espresso.onView(withId(R.id.addevent)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(2).perform(longClick());
        Espresso.onView(withId(R.id.deleteevent)).perform(click());
        Espresso.onView(withText("Time: 00:00AM")).check(doesNotExist());
    }

    @Test
    public void testNonEmptyEvent() {
        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(4).perform(click());
        Espresso.onView(withId(R.id.description)).perform(typeText(description));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.addevent)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.gridview)).atPosition(4).perform(longClick());
        Espresso.onView(withId(R.id.deleteevent)).perform(click());
        Espresso.onView(withText("Description: "+description)).check(doesNotExist());
    }
}