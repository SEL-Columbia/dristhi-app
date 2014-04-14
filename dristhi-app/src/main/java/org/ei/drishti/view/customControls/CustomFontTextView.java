package org.ei.drishti.view.customControls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import org.ei.drishti.R;

public class CustomFontTextView extends TextView {

    @SuppressWarnings("UnusedDeclaration")
    public CustomFontTextView(Context context) {
        this(context, null, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public CustomFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray attributes = context.obtainStyledAttributes(
                attrs, R.styleable.org_ei_drishti_view_customControls_CustomFontTextView, defStyle, 0);
        int variant = attributes.getInt(
                R.styleable.org_ei_drishti_view_customControls_CustomFontTextView_fontVariant, 0);
        attributes.recycle();

        setFontVariant(variant);
    }

    public void setFontVariant(int variant) {
        setFontVariant(FontVariant.tryParse(variant, FontVariant.REGULAR));
    }

    public void setFontVariant(FontVariant variant) {
        setTypeface(
                Typeface.createFromAsset(
                        getContext().getAssets(),
                        variant.fontFile()));
    }
}
