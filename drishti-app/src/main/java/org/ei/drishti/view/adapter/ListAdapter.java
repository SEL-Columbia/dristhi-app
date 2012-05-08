package org.ei.drishti.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import org.ei.drishti.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T> extends ArrayAdapter<T> {
    private List<T> items;
    private List<T> itemsUnfiltered;
    private List<WeakReference<OnDataSourceChangedListener>> onDataSourceChangedListeners;

    public ListAdapter(Context context, int listItemResourceId, List<T> items) {
        super(context, listItemResourceId, items);
        this.items = items;
        this.itemsUnfiltered = new ArrayList<T>(items);
        this.onDataSourceChangedListeners = new ArrayList<WeakReference<OnDataSourceChangedListener>>();
    }

    public void updateItems(List<T> items) {
        this.itemsUnfiltered.clear();
        this.itemsUnfiltered.addAll(items);

        for (WeakReference<OnDataSourceChangedListener> dataSourceChangedListener : onDataSourceChangedListeners) {
            OnDataSourceChangedListener listener = dataSourceChangedListener.get();
            if (listener != null) {
                listener.dataSourceChanged();
            }
        }
    }

    public void refreshDisplayWithoutUpdatingItems(List<T> newItemsToDisplay) {
        this.items.clear();
        this.items.addAll(newItemsToDisplay);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater viewInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = viewInflater.inflate(R.layout.list_item, null);
        }

        populateListItem(view, items.get(position));

        return view;
    }

    protected abstract void populateListItem(View view, T item);

    public List<T> getItems() {
        return itemsUnfiltered;
    }

    public void setOnDataSourceChanged(OnDataSourceChangedListener onDataSourceChangedListener) {
        this.onDataSourceChangedListeners.add(new WeakReference<OnDataSourceChangedListener>(onDataSourceChangedListener));
    }

    public static interface OnDataSourceChangedListener {
        void dataSourceChanged();
    }
}
