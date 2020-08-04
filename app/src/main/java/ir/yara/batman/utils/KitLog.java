package ir.yara.batman.utils;


import androidx.annotation.NonNull;

import ir.yara.batman.BuildConfig;
import timber.log.Timber;

public final class KitLog {
    private KitLog() {
    }

    public static void e(Object o) {
        Timber.e("KitLog says :         %s", o);
    }

    public static void d(Object o) {
        Timber.d("KitLog says :         %s ", o);
    }

    public static void http(Object o) {
        Timber.e("%s", o);
    }

    public static void init() {
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
        else
            Timber.plant(new NotLoggingTree());
    }

    public static class NotLoggingTree extends Timber.Tree {
        @Override
        protected void log(final int priority, final String tag, @NonNull final String message, final Throwable throwable) {
            // just an empty block
        }
    }
}
