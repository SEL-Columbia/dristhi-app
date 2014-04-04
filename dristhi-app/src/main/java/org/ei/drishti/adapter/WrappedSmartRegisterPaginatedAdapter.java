package org.ei.drishti.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;

public class WrappedSmartRegisterPaginatedAdapter extends SmartRegisterPaginatedAdapter
        implements View.OnClickListener {

    private Button btnNextPage;
    private Button btnPreviousPage;
    private TextView pageInfoView;
    private ViewGroup footerView;

    public WrappedSmartRegisterPaginatedAdapter(Context context, SmartRegisterClientsProvider listItemProvider) {
        super(context, listItemProvider);

        footerView = listItemProvider.getFooterView();

        btnNextPage = (Button) footerView.findViewById(R.id.btn_next_page);
        btnNextPage.setOnClickListener(this);

        btnPreviousPage = (Button) footerView.findViewById(R.id.btn_previous_page);
        btnPreviousPage.setOnClickListener(this);

        pageInfoView = (TextView) footerView.findViewById(R.id.txt_page_info);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public View getView(int i, View parentView, ViewGroup viewGroup) {
        if (i == getCount() - 1) {
            return footerView;
        } else {
            return super.getView(i, parentView, viewGroup);
        }
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

    public void updateFooter() {
        pageInfoView.setText((currentPage() + 1) + " of " + (pageCount() + 1));
        btnNextPage.setEnabled(hasNextPage());
        btnPreviousPage.setEnabled(hasPreviousPage());
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
