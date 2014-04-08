package org.ei.drishti.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.DialogOption;

public class SmartRegisterPaginatedAdapter extends BaseAdapter implements Filterable {
    private int clientCount;
    private int pageCount;
    private int currentPage = 0;
    protected static final int CLIENTS_PER_PAGE = 20;

    protected final Context context;

    private SmartRegisterClients filteredClients;

    protected final SmartRegisterClientsProvider listItemProvider;

    public SmartRegisterPaginatedAdapter(Context context, SmartRegisterClientsProvider listItemProvider) {
        this.context = context;
        this.listItemProvider = listItemProvider;
        refreshClients(listItemProvider.getListItems());
    }

    protected void refreshClients(SmartRegisterClients filteredClients) {
        this.filteredClients = filteredClients;
        clientCount = this.filteredClients.size();
        pageCount = clientCount / CLIENTS_PER_PAGE;
    }

    @Override
    public int getCount() {
        if (clientCount <= CLIENTS_PER_PAGE) {
            return clientCount;
        } else if (currentPage == pageCount) {
            return clientCount % CLIENTS_PER_PAGE;
        }
        return CLIENTS_PER_PAGE;
    }

    //#TODO: Fix filter from second page of smart register
    @Override
    public Object getItem(int i) {
        return filteredClients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return actualPosition(i);
    }

    @Override
    public View getView(int i, View parentView, ViewGroup viewGroup) {
        return listItemProvider.getView((ECClient) getItem(actualPosition(i)), parentView, viewGroup);
    }

    private int actualPosition(int i) {
        return i + (currentPage * CLIENTS_PER_PAGE);
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

    public void sortBy(DialogOption sortBy) {
        refreshClients(listItemProvider.sortBy(sortBy));
    }

    public void filterBy(DialogOption filterBy) {
        refreshClients(listItemProvider.filterBy(filterBy));
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
            refreshClients((ECClients) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
