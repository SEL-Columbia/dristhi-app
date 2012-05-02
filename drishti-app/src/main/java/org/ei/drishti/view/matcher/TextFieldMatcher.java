package org.ei.drishti.view.matcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import org.ei.drishti.domain.Displayable;
import org.ei.drishti.view.AfterChangeListener;

public abstract class TextFieldMatcher implements Matcher<TextFieldMatcher.StringForDisplay> {
    private EditText editText;
    private StringForDisplay currentValue;

    public TextFieldMatcher(EditText editText) {
        this.editText = editText;
        currentValue = new StringForDisplay(editText.getText().toString());
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable text) {
                currentValue = new StringForDisplay(text.toString());
                afterChangeListener.afterChangeHappened();
            }
        });
    }

    public StringForDisplay currentValue() {
        return currentValue;
    }

    public static class StringForDisplay implements Displayable {
        private final String value;

        public StringForDisplay(String value) {
            this.value = value;
        }

        public String displayValue() {
            return value;
        }
    }
}
