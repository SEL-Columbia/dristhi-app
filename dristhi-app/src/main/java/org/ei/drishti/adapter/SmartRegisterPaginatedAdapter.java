package org.ei.drishti.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import org.ei.drishti.provider.WrappedSmartRegisterClientsProvider;
import org.ei.drishti.view.contract.Person;

import java.util.List;

public class SmartRegisterPaginatedAdapter extends BaseAdapter implements Filterable {
    private int peopleCount;
    private int pageCount;
    private int currentPage = 0;
    protected static final int peoplePerPage = 20;

    protected final Context context;

    private List<Person> peopleInView;

    protected final WrappedSmartRegisterClientsProvider listItemProvider;

    public SmartRegisterPaginatedAdapter(Context context, WrappedSmartRegisterClientsProvider listItemProvider) {
        this.context = context;

        this.listItemProvider = listItemProvider;

        setupPeopleInView(listItemProvider.getListItems());
    }

    private void setupPeopleInView(List<Person> people) {
        this.peopleInView = people;
        peopleCount = peopleInView.size();
        pageCount = peopleCount / peoplePerPage;
    }

    @Override
    public int getCount() {
        if (peopleCount <= peoplePerPage) {
            return peopleCount;
        } else if (currentPage == pageCount) {
            return peopleCount % peoplePerPage;
        }
        return peoplePerPage;
    }

    @Override
    public Object getItem(int i) {
        return peopleInView.get(i);
    }

    @Override
    public long getItemId(int i) {
        return actualPosition(i);
    }

    @Override
    public View getView(int i, View parentView, ViewGroup viewGroup) {
        return listItemProvider.getView((Person) getItem(actualPosition(i)), parentView, viewGroup);
    }

    private int actualPosition(int i) {
        return i + (currentPage * peoplePerPage);
    }


    public int pageCount() {
        return pageCount;
    }

    public int currentPage() {
        return currentPage;
    }

    public void nextPage() {
        if (hasNextPage()) {
            currentPage++;
        }
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
        }
    }

    public boolean hasNextPage() {
        return currentPage < pageCount;
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    public void sortBy(String sortBy) {
        //Toast.makeText(context, "sort by : " + sortBy, Toast.LENGTH_SHORT).show();
        listItemProvider.sort(sortBy);
    }

    public void showSection(String section) {
        listItemProvider.showSection(section);
    }

    Filter searchFilter = new Filter() {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence cs) {
            FilterResults results = new FilterResults();
            results.values = listItemProvider.filter(cs);
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults
                filterResults) {
            setupPeopleInView((List<Person>) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
