package com.workshop.hackventure;

import static com.workshop.hackventure.GameView.screenRatioX;
import static com.workshop.hackventure.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Enemy {
    int x, y, width, height, frameCounter = 1;;
    public boolean win = false;

    Bitmap enemy, explosion0, explosion1, explosion2, explosion3, explosion4, explosion5;

    Enemy (Resources res) {
        enemy = BitmapFactory.decodeResource(res, R.drawable.enemy);
        explosion0 = BitmapFactory.decodeResource(res, R.drawable.explosion0);
        explosion1 = BitmapFactory.decodeResource(res, R.drawable.explosion1);
        explosion2 = BitmapFactory.decodeResource(res, R.drawable.explosion2);
        explosion3 = BitmapFactory.decodeResource(res, R.drawable.explosion3);
        explosion4 = BitmapFactory.decodeResource(res, R.drawable.explosion4);
        explosion5 = BitmapFactory.decodeResource(res, R.drawable.explosion5);

        width = enemy.getWidth();
        height = enemy.getHeight();

        width /= 4;
        height /= 4;

        width *= Math.ceil(screenRatioX);
        height *= (int) screenRatioY;

        enemy = Bitmap.createScaledBitmap(enemy, width * 10, height * 10, false);
        explosion0 = Bitmap.createScaledBitmap(explosion0, width * 10, height * 10, false);
        explosion1 = Bitmap.createScaledBitmap(explosion1, width * 10, height * 10, false);
        explosion2 = Bitmap.createScaledBitmap(explosion2, width * 10, height * 10, false);
        explosion3 = Bitmap.createScaledBitmap(explosion3, width * 10, height * 10, false);
        explosion4 = Bitmap.createScaledBitmap(explosion4, width * 10, height * 10, false);
        explosion5 = Bitmap.createScaledBitmap(explosion5, width * 10, height * 10, false);
    }

    Bitmap getExplosionFrame() {
        if (frameCounter != 0) {
            if (frameCounter == 1) {
                frameCounter++;
                return explosion0;
            }
            if (frameCounter == 2) {
                frameCounter++;
                return explosion1;
            }
            if (frameCounter == 3) {
                frameCounter++;
                return explosion2;
            }
            if (frameCounter == 4) {
                frameCounter++;
                return explosion3;
            }
            if (frameCounter == 5) {
                frameCounter++;
                return explosion4;
            }
            if (frameCounter == 5) {
                frameCounter++;
                return explosion5;
            }
            frameCounter = 1;
        }
        return explosion0;
    }

    Bitmap getEnemy() {
        return enemy;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, (x + width * 3), (y + height * 8));
    }
}
