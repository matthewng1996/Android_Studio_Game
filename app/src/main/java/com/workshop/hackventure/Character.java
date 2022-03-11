package com.workshop.hackventure;

import static com.workshop.hackventure.GameView.screenRatioX;
import static com.workshop.hackventure.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Character {
    boolean isGoingUp = false;
    int x, y, width, height, frameCounter = 1;
    Bitmap ufoFrame1, ufoFrame2, ufoFrame3, ufoFrame4; //For character dead we just use Explode animation
    private GameView gameView;

    Character(GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;

        ufoFrame1 = BitmapFactory.decodeResource(res, R.drawable.ufo_0);
        ufoFrame2 = BitmapFactory.decodeResource(res, R.drawable.ufo_1);
        ufoFrame3 = BitmapFactory.decodeResource(res, R.drawable.ufo_2);
        ufoFrame4 = BitmapFactory.decodeResource(res, R.drawable.ufo_3);

        width = ufoFrame1.getWidth();
        height = ufoFrame1.getHeight();

        width /= 4;
        height /= 4;

        width *= Math.ceil(screenRatioX);
        height *= (int) screenRatioY;

        ufoFrame1 = Bitmap.createScaledBitmap(ufoFrame1, width * 10, height * 10, false);
        ufoFrame2 = Bitmap.createScaledBitmap(ufoFrame2, width * 10, height * 10, false);
        ufoFrame3 = Bitmap.createScaledBitmap(ufoFrame3, width * 10, height * 10, false);
        ufoFrame4 = Bitmap.createScaledBitmap(ufoFrame4, width * 10, height * 10, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    Bitmap getFrame() {
        if (frameCounter != 0) {
            if (frameCounter == 1) {
                frameCounter++;
                return ufoFrame1;
            }
            if (frameCounter == 2) {
                frameCounter++;
                return ufoFrame2;
            }
            if (frameCounter == 3) {
                frameCounter++;
                return ufoFrame3;
            }
            if (frameCounter == 4) {
                frameCounter++;
                return ufoFrame4;
            }
            frameCounter = 1;
            gameView.newBullet();
        }
        return ufoFrame1;
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
