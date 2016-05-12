package com.cayden.collect.memo.util;

import android.support.test.espresso.IdlingResource;

/**
 * Created by cuiran on 16/5/12.
 */
public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}
