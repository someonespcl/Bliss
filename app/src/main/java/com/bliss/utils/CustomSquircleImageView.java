package com.bliss.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CustomSquircleImageView extends AppCompatImageView {

    private Paint squirclePaint;

    public CustomSquircleImageView(Context context) {
        super(context);
        init();
    }

    public CustomSquircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSquircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        squirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // Define the radius for the rounded corners (adjust as needed)
        float cornerRadius = 120.0f;

        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, Path.Direction.CW);

        // Clip the canvas with the squircle path
        canvas.clipPath(path);

        // Draw the image
        super.onDraw(canvas);
    }

    @Override
    public void setImageResource(int resId) {
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(resId)).getBitmap();
        super.setImageBitmap(bitmap);
    }
}
