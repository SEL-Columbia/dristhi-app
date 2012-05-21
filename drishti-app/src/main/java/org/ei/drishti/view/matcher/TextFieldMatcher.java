package org.ei.drishti.view.matcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import org.ei.drishti.view.AfterChangeListener;

public abstract class TextFieldMatcher<T> implements Matcher<DisplayableString, T> {
    private EditText editText;
    private DisplayableString currentValue;

    public TextFieldMatcher(EditText editText) {
        this.editText = editText;
        currentValue = new DisplayableString(editText.getText().toString());
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable text) {
                currentValue = new DisplayableString(text.toString());
                afterChangeListener.afterChangeHappened();
            }
        });
    }

    public DisplayableString currentValue() {
        return currentValue;
    }

    public boolean isActive() {
        return !"".equals(currentValue().displayValue());
    }

}
