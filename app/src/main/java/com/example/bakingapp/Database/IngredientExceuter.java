package com.example.bakingapp.Database;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IngredientExceuter {

     private static final Object LOCK = new Object();
    private static IngredientExceuter sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;
     private IngredientExceuter(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }
        public static IngredientExceuter getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new IngredientExceuter(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }
     private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
