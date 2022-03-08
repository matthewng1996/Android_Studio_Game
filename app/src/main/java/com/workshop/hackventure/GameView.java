package com.workshop.hackventure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying = true;
    private int screenWidth, screenHeight;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private Background sky, tree_foreground, tree_background, mountain_foreground, mountain_background;
    int tree_foregroundX = 0, tree_backgroundX = 0, mountain_foregroundX = 0, mountain_backgroundX = 0;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenWidth = screenX;
        this.screenHeight = screenY;

        //To make it compatible to multiple screen sizes
        screenRatioX = (int) 1920f / screenX;
        screenRatioY = (int) 1080f / screenY;

        sky = new Background(screenX, screenY, getResources());
        tree_background = new Background(screenX, screenY, getResources());
        tree_foreground = new Background(screenX, screenY, getResources());
        mountain_foreground = new Background(screenX, screenY, getResources());
        mountain_background = new Background(screenX, screenY, getResources());

        //background2.x = screenX;

        paint = new Paint();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {

        //Move background backwards to simluate moving forward
        /*
        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioY;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }
        */

    }

    private void draw() {
        if (getHolder().getSurface().isValid()) { //Need this to check if canvas has been initialised, ie not null
            Canvas canvas = getHolder().lockCanvas(); //Get current canvas displayed on the screen

            canvas.drawBitmap(sky.sky, sky.x, sky.y, paint);

            //Parallax speed
            tree_foregroundX -= 2;
            tree_backgroundX -= 3;
            mountain_backgroundX -= 1;
            mountain_foregroundX -= 2;

            canvas.drawBitmap(mountain_background.mountain_background, mountain_backgroundX,0, paint);
            canvas.drawBitmap(mountain_background.mountain_background, mountain_backgroundX + mountain_background.mountain_background.getWidth(), 0, paint);
            if (mountain_backgroundX < -(mountain_background.mountain_background.getWidth() / 2)) {
                mountain_backgroundX = 0;
                canvas.drawBitmap(mountain_background.mountain_background, mountain_background.mountain_background.getWidth(),0, paint);
            }

            canvas.drawBitmap(mountain_foreground.mountain_foreground, mountain_foregroundX,0, paint);
            canvas.drawBitmap(mountain_foreground.mountain_foreground, mountain_foregroundX + mountain_foreground.mountain_foreground.getWidth(), 0, paint);
            if (mountain_foregroundX < -(mountain_foreground.mountain_foreground.getWidth() / 2)) {
                mountain_foregroundX = 0;
                canvas.drawBitmap(mountain_foreground.mountain_foreground, mountain_foreground.mountain_foreground.getWidth(),0, paint);
            }

            canvas.drawBitmap(tree_foreground.trees_foreground, tree_foregroundX,0, paint);
            canvas.drawBitmap(tree_foreground.trees_foreground, tree_foregroundX + tree_foreground.trees_foreground.getWidth(), 0, paint);
            if (tree_foregroundX < -(tree_foreground.trees_foreground.getWidth() / 2)) {
                tree_foregroundX = 0;
                canvas.drawBitmap(tree_foreground.trees_foreground, tree_foreground.trees_foreground.getWidth(),0, paint);
            }

            canvas.drawBitmap(tree_background.trees_background, tree_backgroundX,0, paint);
            canvas.drawBitmap(tree_background.trees_background, tree_backgroundX + tree_foreground.trees_background.getWidth(), 0, paint);
            if (tree_backgroundX < -(tree_background.trees_background.getWidth() / 2)) {
                tree_backgroundX = 0;
                canvas.drawBitmap(tree_background.trees_background, tree_background.trees_background.getWidth(),0, paint);
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
