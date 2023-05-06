package com.example.beadando;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.Random;

public class RandomAsyncLoader extends AsyncTaskLoader<String> {

    public RandomAsyncLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Random random = new Random();
        int number = random.nextInt(5);
        int ms = number * 200;

        try{Thread.sleep(ms);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return "Logged in as guest after " + ms + " ms!";

    }
}
