package he_arc.balljump;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by pedrocosta on 28.10.17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 532;
    public static final int HEIGHT = 850;
    private MainThread mainThread;
    private BackGround backGround;

    public GamePanel(Context context){
        super(context);

        //Add the callback to the surface to intercept events
        getHolder().addCallback(this);

        mainThread = new MainThread(getHolder(),this);

        //Make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;

        while (retry) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        backGround = new BackGround(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void update(){

    }

    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;
        if(canvas != null){
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            backGround.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}