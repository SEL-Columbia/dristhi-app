package org.ei.drishti.view.matcher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import org.ei.drishti.view.AfterChangeListener;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.OnSelectionChangeListener;

import static android.graphics.Bitmap.Config.ARGB_8888;

public abstract class DialogMatcher<T> implements Matcher<T> {
    private DialogAction dialogForChoosingAnOption;
    private T currentValue;
    private final T defaultValue;

    public DialogMatcher(DialogAction<T> dialogForChoosingAnOption, T defaultValue) {
        this.dialogForChoosingAnOption = dialogForChoosingAnOption;
        this.defaultValue = defaultValue;
        currentValue = this.defaultValue;
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        dialogForChoosingAnOption.setOnSelectionChangedListener(new OnSelectionChangeListener<T>() {
            public void selectionChanged(View actionItemView, T selection) {
                currentValue = selection;
                actionItemView.setSelected(!selection.equals(defaultValue));
                actionItemView.setBackgroundDrawable(drawText(selection));
                afterChangeListener.afterChangeHappened();
            }

            private Drawable drawText(T selection) {
                Bitmap bitmap = Bitmap.createBitmap(100, 20, ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
                paint.setColor(Color.WHITE);
                paint.setTextSize(18);
                canvas.drawText(selection.toString(), 0, 15, paint);
                BitmapDrawable drawable = new BitmapDrawable(bitmap);
                drawable.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                return drawable;
            }
        });
    }

    public T currentValue() {
        return currentValue;
    }
}
