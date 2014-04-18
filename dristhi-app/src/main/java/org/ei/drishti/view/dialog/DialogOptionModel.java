package org.ei.drishti.view.dialog;

public interface DialogOptionModel {
    DialogOption[] getDialogOptions();

    void onDialogOptionSelection(DialogOption option, Object tag);
}
