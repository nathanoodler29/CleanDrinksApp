package coffee.prototype.android.cleandrinksapplication;


import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * created by Noodle on 17/03/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    private Helper helper = new Helper();
    //This rules handles starting and closing the application during the tests are run.
    @Rule
    public ActivityTestRule<Sign_Up_Activity> sign_up_activityActivityTestRule
            = new ActivityTestRule<>(Sign_Up_Activity.class);

    /**
     * This test fills out the email, password field, clicks the sign up button.
     * The test then checks if it's on the correct activity by checking the sub headings.
     *
     * @throws Exception This catches an exepction related to unable to finding an ID, to allow tests to keep running.
     */
    @Test
    public void testSuccessfulAccountSignUpAndEmailToastAppears_ReqDroid1() throws Exception {
        //Calls the time stamp method to apply to the email address.
        String timeStamp = helper.getCurrentTime();
        //Adds a timestamp to the email, this is so, a consistent test can be made for creating a new account.
        onView(withId(R.id.email_address_sign_up_field)).perform(clearText(), typeText("successtest" + timeStamp + "@email.com"));
        //Code uses to how to check for toast  https://gist.github.com/brunodles/badaa6de2ad3a84138d517795f15efc7
        onView(withText("Valid Email")).inRoot(withDecorView(not(is(sign_up_activityActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //adds password text.
        onView(withId(R.id.password_address_sign_up_field)).perform(clearText(), typeText("password1"));
        //Closes the keyboard that has just been opened.
        Espresso.closeSoftKeyboard();
        //Clicks the Sign up Button
        onView(withId(R.id.sign_activity_button_signupactivity)).perform(click());
        //Checks that the paragraph text in the weight and height activity, matches the expected test, to prove the app is currently on weight activity.
        onView(withId(R.id.weight_paragraph_text)).check(matches(withText("Please enter weight and height.")));

    }


    @Test
    public void testSuccessfulAccountSignUpAndPasswordToastAppears_ReqDroid1() throws Exception {
        onView(withId(R.id.password_address_sign_up_field)).perform(clearText(), typeText("password1"));
        onView(withText("Valid Password")).inRoot(withDecorView(not(is(sign_up_activityActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        String timeStamp = helper.getCurrentTime();
        onView(withId(R.id.email_address_sign_up_field)).perform(clearText(), typeText("successtest" + timeStamp + "@email.com"));


        //Closes the keyboard that has just been opened.
        Espresso.closeSoftKeyboard();
        //Clicks the Sign up Button
        onView(withId(R.id.sign_activity_button_signupactivity)).perform(click());
        //Checks that the paragraph text in the weight and height activity, matches the expected test, to prove the app is currently on weight activity.
        onView(withId(R.id.weight_paragraph_text)).check(matches(withText("Please enter weight and height.")));

    }

    @Test
    public void testIncorrectEmailThrowsMustBeValidEmailError_ReqDroid2() {

        onView(withId(R.id.email_address_sign_up_field)).perform(clearText(), typeText("nathan@"));

        onView(withId(R.id.email_address_sign_up_field)).check(matches(hasErrorText("Please include a valid email")));




    }

    @Test
    public void testIncorrectPasswordThrowsPasswordMustContainANumberError_ReqDroid3() {

        onView(withId(R.id.password_address_sign_up_field)).perform(clearText(), typeText("password"));

        onView(withId(R.id.password_address_sign_up_field)).check(matches(hasErrorText("Password Needs to contain 1 number")));




    }

    @Test
    public void testIncorrectPasswordThrowsPasswordMustBeLongerThanFourCharsError_ReqDroid3() {

        onView(withId(R.id.password_address_sign_up_field)).perform(clearText(), typeText("aaa"));

        onView(withId(R.id.password_address_sign_up_field)).check(matches(hasErrorText("Password must be longer than four characters")));



    }

    @Test
    public void testIfBlankFieldsAreSubmittedItWillThrowToastErrorAndNotShowNextActivity_ReqDroid4(){

        onView(withId(R.id.email_address_sign_up_field)).perform(clearText());

        onView(withId(R.id.password_address_sign_up_field)).perform(clearText());

        onView(withId(R.id.sign_activity_button_signupactivity)).perform(click());

        onView(withText("Please ensure email or password fields aren't blank.")).inRoot(withDecorView(not(is(sign_up_activityActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.weight_paragraph_text)).check(doesNotExist());



    }




}
