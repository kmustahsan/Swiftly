package com.example.swiftly.swiftly;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kevin on 11/26/16.
 */

public class TouchHandler implements View.OnTouchListener {

    MainActivity mainActivity;
    CartActivity cartActivity;
    GestureDetectorCompat gestureDetectorCompat;
    boolean main = true;

    public TouchHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        gestureDetectorCompat = new GestureDetectorCompat(this.mainActivity, new MyGestureListener());
    }

    public TouchHandler(CartActivity cartActivity) {
        this.cartActivity = cartActivity;
        main = false;
        gestureDetectorCompat = new GestureDetectorCompat(this.cartActivity, new MyGestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);

        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            final float xDistance = Math.abs(e1.getX() - e2.getX());
            if (Math.abs(velocityX) > 100 && xDistance > 100) {
                if (e1.getX() > e2.getX()) {
                    // swiped left
                    System.out.println("LEFT");
                    if (main)
                        mainActivity.onSwipeLeft();
                    else
                        cartActivity.onSwipeLeft();
                }
                else {
                    System.out.println("RIGHT");
                    if (main)
                        mainActivity.onSwipeRight();
                    else
                        cartActivity.onSwipeRight();
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
