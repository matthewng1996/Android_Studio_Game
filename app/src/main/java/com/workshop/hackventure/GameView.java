package com.workshop.hackventure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying = true;
    private int screenWidth, screenHeight;
    int enemyHealth = 100000;
    public static float screenRatioX, screenRatioY;
    public float enemyLastLocY;
    private Paint paint;
    private Character character;
    private Enemy enemy;
    private List<PlayerBullet> bullets;
    private List<Enemy> enemies;
    private Background sky, tree_foreground, tree_background, mountain_foreground, mountain_background;
    private int enemyMovingVectorY = 1; // -1 is up, 1 is down
    public static final float VELOCITY = 20f;
    int tree_foregroundX = 0, tree_backgroundX = 0, mountain_foregroundX = 0, mountain_backgroundX = 0;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenWidth = screenX;
        this.screenHeight = screenY;

        //To make it compatible to multiple screen sizes
        screenRatioX = (float) 1920f / screenX;
        System.out.println(screenRatioX);
        screenRatioY = (int) 1080f / screenY;

        sky = new Background(screenX, screenY, getResources());
        tree_background = new Background(screenX, screenY, getResources());
        tree_foreground = new Background(screenX, screenY, getResources());
        mountain_foreground = new Background(screenX, screenY, getResources());
        mountain_background = new Background(screenX, screenY, getResources());

        character = new Character(this, screenY, getResources());

        enemy = new Enemy(getResources());

        bullets = new ArrayList<>();
        enemies = new ArrayList<>();

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
        /*
        Character movement
         */
        if (character.isGoingUp) {
            character.y -= 30 * screenRatioY;
        } else {
            character.y += 30 * screenRatioY;
        }

        if (character.y < 0) {
            character.y = -10;
        }

        if (character.y >= screenHeight - (character.height * 10)) {
            character.y = screenHeight - (character.height * 10);
        }

        /*
        Enemy movement
         */

        enemy.y +=  (int)(VELOCITY* enemyMovingVectorY);

        if (enemy.y < 0) {
            enemyMovingVectorY = 1;
        }

        if (enemy.y >= screenHeight - (enemy.height * 10)) {
            enemyMovingVectorY = -1;
        }

        List<PlayerBullet> trash = new ArrayList<>();

        if (enemy != null && enemies.size() < 1) {
            enemies.add(enemy);
        }

        for (PlayerBullet bullet : bullets) {
            if (bullet.x > screenWidth) {
                trash.add(bullet);
            }

            if (Rect.intersects(enemy.getCollisionShape(), bullet.getCollisionShape())) {
                int bulletDamage = bullet.getDamage();
                enemyHealth -= bulletDamage;
                if (enemyHealth <= 0 && enemy.win == false) {
                    this.enemyLastLocY = bullet.getCollisionShape().left;
                    enemy.win = true;
                    enemies.remove(enemy);
                }
            }

            bullet.x += 150 * screenRatioX;
        }

        for (PlayerBullet bullet : trash){
            bullets.remove(bullet);
        }
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

            canvas.drawBitmap(character.getFrame(), character.x, character.y, paint);

            for (PlayerBullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            if (enemy.win) {
                canvas.drawBitmap(enemy.getExplosionFrame(), screenWidth/2, enemyLastLocY, paint);
            } else {
                canvas.drawBitmap(enemy.getEnemy(), screenWidth / 2, enemy.y, paint);
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

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (motionEvent.getX() > 0) {
                    character.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                character.isGoingUp = false;
                break;
        }
        return true;
    }

    public void newBullet() {
        PlayerBullet bullet = new PlayerBullet(getResources());
        bullet.x = character.x + character.width;
        bullet.y = character.y + character.height - (character.height / 2);
        bullets.add(bullet);
    }
}
