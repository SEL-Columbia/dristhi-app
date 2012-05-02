package org.ei.drishti.util;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import static android.graphics.Bitmap.Config.ARGB_8888;

public class TextCanvas {
    private static TextCanvas instance;
    private final Context context;

    public Drawable drawableFor(String text) {
        float scale = context.getResources().getDisplayMetrics().density;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(18 * scale);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, 15, paint);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        drawable.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        return drawable;
    }

    public static TextCanvas getInstance(Context context) {
        if (instance == null) {
            instance = new TextCanvas(context);
        }
        return instance;
    }

    private TextCanvas(Context context) {
        this.context = context;
    }
}
