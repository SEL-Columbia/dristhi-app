package org.ei.drishti.matcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.util.AfterChangeListener;

public abstract class TextFieldMatcher implements Matcher {
    private EditText editText;
    private String currentValue;

    public TextFieldMatcher(EditText editText) {
        this.editText = editText;
        currentValue = editText.getText().toString();
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable text) {
                currentValue = text.toString();
                afterChangeListener.afterChangeHappened();
            }
        });
    }

    public Object currentValue() {
        return currentValue;
    }

    public abstract boolean matches(Alert alert);
}
