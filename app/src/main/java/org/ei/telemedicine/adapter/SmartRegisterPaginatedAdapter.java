package org.ei.telemedicine.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.contract.SmartRegisterClients;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;

public class SmartRegisterPaginatedAdapter extends BaseAdapter {
    private static final int CLIENTS_PER_PAGE = 20;

    private int clientCount;
    private int pageCount;
    private int currentPage = 0;
    private SmartRegisterClients filteredClients;

    private final int clientsPerPage;
    private final SmartRegisterClientsProvider listItemProvider;

    public SmartRegisterPaginatedAdapter(SmartRegisterClientsProvider listItemProvider) {
        this(CLIENTS_PER_PAGE, listItemProvider);
    }

    public SmartRegisterPaginatedAdapter(
            int clientsPerPage, SmartRegisterClientsProvider listItemProvider) {
        this.clientsPerPage = clientsPerPage;
        this.listItemProvider = listItemProvider;
        refreshClients(listItemProvider.getClients());
    }

    private void refreshClients(SmartRegisterClients filteredClients) {
        this.filteredClients = filteredClients;
        clientCount = filteredClients.size();
        pageCount = (int) Math.ceil((double) clientCount / (double) clientsPerPage);
        currentPage = 0;
    }

    @Override
    public int getCount() {
        if (clientCount <= clientsPerPage) {
            return clientCount;
        } else if (currentPage == pageCount() - 1) {
            return clientCount - currentPage * clientsPerPage;
        }
        return clientsPerPage;
    }

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
        return listItemProvider.getView((SmartRegisterClient) getItem(actualPosition(i)), parentView, viewGroup);
    }

    private int actualPosition(int i) {
        if (clientCount <= clientsPerPage) {
            return i;
        } else {
            return i + (currentPage * clientsPerPage);
        }
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
        return currentPage < pageCount() - 1;
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    public void refreshList(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                            FilterOption searchFilter, SortOption sortOption) {
        SmartRegisterClients filteredClients = listItemProvider
                .updateClients(villageFilter, serviceModeOption, searchFilter, sortOption);
        refreshClients(filteredClients);
        notifyDataSetChanged();
    }
}
