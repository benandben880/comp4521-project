package com.example.project;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MemoActivityTest {
    @Rule
    public ActivityTestRule<MemoActivity> mMemoActivityRule = new ActivityTestRule<>(MemoActivity.class);

    private String Title = "What Are Articles?";
    private String content = "Articles are words that define a noun as specific or unspecific. Consider the following examples:";

    @Test
    public void testMemoRecyclerView() {
        Espresso.onView(withId(R.id.listOfNotes)).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteMemoWhenEdit() {
        Espresso.onView(withId(R.id.add)).perform(click());
        Espresso.onView(withId(R.id.noteTitle)).perform(typeText(Title));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.delete)).perform(click());
        Espresso.onView(withId(R.id.listOfNotes)).check(matches(isDisplayed()));
    }

    @Test
    public void testInputEmptyTitleMemo() {
        Espresso.onView(withId(R.id.add)).perform(click());
        Espresso.onView(withId(R.id.save)).perform(click());
//        Espresso.onView(withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition())
        Espresso.onView(withId(R.id.noteTitle)).check(matches(hasErrorText("Title can not be blank.")));
    //        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//        Espresso.onView(withText(Title)).check(matches(isDisplayed()));
    }

    @Test
    public void testMemoStructure() {
        Espresso.onView(ViewMatchers.withId(R.id.listOfNotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.pressBack();
        Espresso.onView(withId(R.id.listOfNotes)).check(matches(isDisplayed()));
    }
    @Test
    public void testInputMemo() {
        Espresso.onView(withId(R.id.add)).perform(click());
        Espresso.onView(withId(R.id.noteTitle)).perform(typeText(Title));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.noteDetails)).perform(typeText(content));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.save)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.listOfNotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withText(content)).check(matches(isDisplayed()));
    }

    @Test
    public void testMemoEditAgain() {
        Espresso.onView(ViewMatchers.withId(R.id.listOfNotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.edit)).perform(click());
        Espresso.onView(withId(R.id.noteTitle)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.noteDetails)).check(matches(isDisplayed()));
    }
    @Test
    public void testMemoDelete() {
        Espresso.onView(ViewMatchers.withId(R.id.listOfNotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.fab)).perform(click());
        Espresso.onView(withId(R.id.listOfNotes)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.listOfNotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withText(content)).check(doesNotExist());
    }


}