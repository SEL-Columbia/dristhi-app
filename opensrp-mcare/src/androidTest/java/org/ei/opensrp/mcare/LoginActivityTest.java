package org.ei.opensrp.mcare;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by keyman on 11/08/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTR = new ActivityTestRule<LoginActivity>(LoginActivity.class, true, true);

    @Test
    public void testUsernameExist() {

        // View exists but not displayed
        Espresso.onView(ViewMatchers.withId(R.id.login_userNameText)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

    }

    @Test
    public void testPasswordExist() {

        // View exists but not displayed
        Espresso.onView(ViewMatchers.withId(R.id.login_passwordText)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
    }

    @Test
    public void testLoginExist() {

        // View exists but not displayed
        Espresso.onView(ViewMatchers.withId(R.id.login_loginButton)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

    }

    @Test
    public void testSettingMenuExist() {

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        Espresso.onView(ViewMatchers.withText("Settings"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    @Test
    public void testBaseUrlOptionMenuExist() {

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        Espresso.onView(ViewMatchers.withText("Settings"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText("Settings"))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("OpenSRP base URL")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


    }

   /* @Test
    public void doLogin() {

        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        // Click the item.
        onView(ViewMatchers.withText("Settings"))
                .perform(click());

        Espresso.onView(ViewMatchers.withText("OpenSRP base URL")).perform(click());

        try {
            Thread.sleep(200);
        }catch (InterruptedException e){

        }

        Espresso.onView(ViewMatchers.isAssignableFrom(EditText.class)).perform(ViewActions.clearText(), typeText(baseUrl));
        Espresso.onView(ViewMatchers.withText("OK")).perform(click());

        Espresso.pressBack();

        // Type text and then press the button.
        Espresso.onView(ViewMatchers.withId(R.id.login_userNameText))
                .perform(typeText(username), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_passwordText))
                .perform(typeText(password), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.login_loginButton)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(NativeHomeActivity.class.getName()));
    }*/

}