package coffee.prototype.android.cleandrinksapplication;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * created by Noodle on 27/03/2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private Helper helper = new Helper();
    //This rules handles starting and closing the application during the tests are run.
    @Rule
    public ActivityTestRule<MainActivity> MainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<Weight_and_Height_Activity> WeightAndHeightTestRule
            = new ActivityTestRule<>(Weight_and_Height_Activity.class);

    @Test
    public void TesttestIfUserHasAddedWeightThenWeightPageIsntDisplayed_ReqDroid8() {
        onView(withId(R.id.email_address_field)).perform(clearText(), typeText("nas29@gmail.com"));
        onView(withId(R.id.password_field)).perform(clearText(), typeText("password12"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.alcohol_heading)).check(matches(withText("Alcohol")));


    }
    @Test
    public void testSuccessfulLoginAndIfUserHasFilledWeightGoToWeightActivity_ReqDroid8() {


        onView(withId(R.id.email_address_field)).perform(clearText(), typeText("account@usersweight.com"));
        onView(withId(R.id.password_field)).perform(clearText(), typeText("show12"));
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.weight_paragraph_text)).check(matches(withText("Please enter weight and height.")));




    }



    @Test
    public void testIfForgottenPasswordActivityIsLaunchedIfUserEntersIncorrectCredentialsTwice_ReqDroid10() {

        for (int i = 0; i <= 2; i++) {
            onView(withId(R.id.email_address_field)).perform(clearText(), typeText("fail@email.com"));
            onView(withId(R.id.password_field)).perform(clearText(), typeText("show12"));
            onView(withId(R.id.login_button)).perform(click());

        }
        onView(withId(R.id.forgotten_password_heading)).check(matches(withText("Forgotten Password")));


    }


    @Test
    public void testIfNoAccountExistsWithUnknownAccount_ReqDroid9() {


        onView(withId(R.id.email_address_field)).perform(clearText(), typeText("nosuchaccountname@email.com"));
        onView(withText("Valid email")).inRoot(withDecorView(not(is(MainActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        //Has to be used to simulate the average user speed of typing.
        helper.pauseTestFor(5000);

        onView(withId(R.id.password_field)).perform(clearText(), typeText("show12"));
        //Has to be used to simulate the average user speed of typing.

        helper.pauseTestFor(5000);

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.login_button)).perform(click());


        onView(withText("No such account")).inRoot(withDecorView(not(is(MainActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));


    }



}
