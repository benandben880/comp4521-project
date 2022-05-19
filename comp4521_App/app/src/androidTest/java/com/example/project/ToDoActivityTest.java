package com.example.project;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ToDoActivityTest {

    @Rule
    public ActivityTestRule <ToDoActivity> mTodoActivityRule = new ActivityTestRule<>(ToDoActivity.class);

    private String Tasks = "buy egg";
    private String Deletecontent = "delete";

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testInputDeleteTodo() {
        Espresso.onView(withId(R.id.fab)).perform(click());
        Espresso.onView(withId(R.id.newTaskText)).perform(typeText(Tasks));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.newTaskButton)).perform(click());
//        Espresso.onView(withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition())
        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withText(Tasks)).check(matches(isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        Espresso.onView(withText("CONFIRM")).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withText(Deletecontent)).check(doesNotExist());
    }

    @Test
    public void testRecyclerView() {
        Espresso.onView(withId(R.id.tasksRecyclerView)).check(matches(isDisplayed()));
//        Espresso.onView(withId(R.id.tasksRecyclerView)).check(matches(isDisplayed()));
    }
    @Test
    public void testInputAgain() {
        Espresso.onView(withId(R.id.fab)).perform(click());
        Espresso.onView(withId(R.id.newTaskText)).perform(typeText(Tasks));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.newTaskButton)).perform(click());
//        Espresso.onView(withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition())
        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        Espresso.onView(withId(R.id.newTaskText)).check(matches(isDisplayed()));
    }

    @Test
    public void testInputDeleteDialog() {
        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        Espresso.onView(withText("Are you sure you want to delete this Task?")).check(matches(isDisplayed()));
    }

//    @Test
//    public void testInputDelete() {
//        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
//        Espresso.onView(withText("CONFIRM")).perform(click());
//        Espresso.onView(ViewMatchers.withId(R.id.tasksRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//        Espresso.onView(withText(Deletecontent)).check(doesNotExist());
//
//    }


    @After
    public void tearDown() throws Exception {
    }
}