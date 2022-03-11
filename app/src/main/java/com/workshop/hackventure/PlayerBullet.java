package com.workshop.hackventure;

import static com.workshop.hackventure.GameView.screenRatioX;
import static com.workshop.hackventure.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class PlayerBullet {

    int x, y, width, height;
    Bitmap bullet;

    PlayerBullet (Resources res) {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;
        height /= 4;

        width *= (int) Math.ceil(screenRatioX);
        height *= (int) screenRatioY;

        bullet = Bitmap.createScaledBitmap(bullet, width * 10, height * 10, false);
    }

    int getDamage() {
        return 0;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, (x + width), y + height);
    }
}
