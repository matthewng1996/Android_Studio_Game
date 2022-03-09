package com.workshop.hackventure;

import static com.workshop.hackventure.GameView.screenRatioX;
import static com.workshop.hackventure.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Character {
    int toShoot = 0; //Might change to boolean
    boolean isShooting = false, isGoingUp = false;
    int x, y, width, height, wingCounter = 0, shootCounter = 1;
    Bitmap characterFrame1, characterFrame2; //For character dead we just use Explode animation
    private GameView gameView;

    Character(GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;

        //TODO: Split to array Bitmap
        characterFrame1 = BitmapFactory.decodeResource(res, R.drawable.yellow_ufo);
        characterFrame2 = BitmapFactory.decodeResource(res, R.drawable.yellow_ufo);

        width = characterFrame1.getWidth();
        height = characterFrame1.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        characterFrame1 = Bitmap.createScaledBitmap(characterFrame1, width, height, false);
        characterFrame2 = Bitmap.createScaledBitmap(characterFrame2, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    /* TODO:
    Replace shooting animation with original idle/moving animation
    Bitmap getCharacter() {
        if (isShooting) {
            gameView.newBullet(); //TODO: Add bullet to gameView
            return null;
        }
    }
     */

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
