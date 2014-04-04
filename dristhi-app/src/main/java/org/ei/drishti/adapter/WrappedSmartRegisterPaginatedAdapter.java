package org.ei.drishti.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.provider.WrappedSmartRegisterClientsProvider;

public class WrappedSmartRegisterPaginatedAdapter extends SmartRegisterPaginatedAdapter
        implements View.OnClickListener {

    private final boolean shouldShowFooter;
    private final WrappedSmartRegisterClientsProvider wrappedListItemProvider;
    private Button btnNextPage;
    private Button btnPreviousPage;
    private TextView pageInfoView;

    private ViewGroup footerView;

    public WrappedSmartRegisterPaginatedAdapter(Context context, WrappedSmartRegisterClientsProvider listItemProvider) {
        super(context, listItemProvider);

        wrappedListItemProvider = listItemProvider;

        shouldShowFooter = listItemProvider.shouldShowFooter();
        if (shouldShowFooter) {
            footerView = wrappedListItemProvider.getFooterView();

            btnNextPage = (Button) footerView.findViewById(R.id.btn_next_page);
            btnNextPage.setOnClickListener(this);

            btnPreviousPage = (Button) footerView.findViewById(R.id.btn_previous_page);
            btnPreviousPage.setOnClickListener(this);

            pageInfoView = (TextView) footerView.findViewById(R.id.txt_page_info);
        }
    }

    @Override
    public int getCount() {
        return super.getCount() + (shouldShowFooter ? 1 : 0); // for for header and footers
    }

    @Override
    public View getView(int i, View parentView, ViewGroup viewGroup) {
        if (shouldShowFooter && i == getCount() - 1) {
            return footerView;
        } else {
            return super.getView(i, parentView, viewGroup);
        }
    }

    public void updateFooter() {
        pageInfoView.setText((currentPage() + 1) + " of " + (pageCount() + 1));
        btnNextPage.setEnabled(hasNextPage());
        btnPreviousPage.setEnabled(hasPreviousPage());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next_page:
                goToNextPage();
                break;
            case R.id.btn_previous_page:
                goToPreviousPage();
                break;
        }
    }

    private void goToPreviousPage() {
        super.previousPage();
        notifyDataSetChanged();
    }

    private void goToNextPage() {
        super.nextPage();
        notifyDataSetChanged();
    }

}
