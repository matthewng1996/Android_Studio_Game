package com.workshop.hackventure;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    int x = 0, y = 0;
    Bitmap background, mountain_foreground, mountain_background, trees_foreground, trees_background, sky;

    Background(int screenX, int screenY, Resources res) {
        //background = BitmapFactory.decodeResource(res, R.drawable.background);
        //background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

        sky = BitmapFactory.decodeResource(res, R.drawable.sky);
        mountain_foreground = BitmapFactory.decodeResource(res, R.drawable.mountain_foreground);
        mountain_background = BitmapFactory.decodeResource(res, R.drawable.mountain_background);
        trees_foreground = BitmapFactory.decodeResource(res, R.drawable.trees_foreground);
        trees_background = BitmapFactory.decodeResource(res, R.drawable.trees);

        sky = Bitmap.createScaledBitmap(sky, screenX, screenY, true);
        mountain_foreground = Bitmap.createScaledBitmap(mountain_foreground, screenX, screenY, false);
        mountain_background = Bitmap.createScaledBitmap(mountain_background, screenX, screenY, false);
        trees_foreground = Bitmap.createScaledBitmap(trees_foreground, screenX, screenY, false);
        trees_background = Bitmap.createScaledBitmap(trees_background, screenX, screenY, false);
    }
}
