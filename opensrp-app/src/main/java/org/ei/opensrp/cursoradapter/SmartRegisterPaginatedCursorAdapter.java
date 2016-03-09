package org.ei.opensrp.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by raihan on 3/9/16.
 */
public class SmartRegisterPaginatedCursorAdapter extends CursorAdapter {
    private final SmartRegisterCLientsProviderForCursorAdapter listItemProvider;
    Context context;

    public SmartRegisterPaginatedCursorAdapter(Context context, Cursor c,SmartRegisterCLientsProviderForCursorAdapter listItemProvider) {
        super(context, c);
        this.listItemProvider = listItemProvider;
        this.context= context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
      return  listItemProvider.inflatelayoutForCursorAdapter();
//        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }
}
