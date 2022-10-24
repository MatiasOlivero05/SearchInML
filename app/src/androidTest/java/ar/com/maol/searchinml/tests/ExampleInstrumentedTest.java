package ar.com.maol.searchinml.tests;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import ar.com.maol.searchinml.util.Util;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void test_getStringConditionNew() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("Condición: Nuevo", Util.getStringCondition(appContext, "new"));
    }

    @Test
    public void test_getStringConditionUsed() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("Condición: Usado", Util.getStringCondition(appContext, "used"));
    }
}