package org.ei.telemedicine.view.dialog;

import static android.view.View.VISIBLE;
import static org.ei.telemedicine.AllConstants.FormNames.FP_COMPLICATIONS;
import static org.ei.telemedicine.Context.getInstance;

import org.ei.telemedicine.R;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;
import org.ei.telemedicine.view.contract.ANCSmartRegisterClient;
import org.ei.telemedicine.view.contract.AlertStatus;
import org.ei.telemedicine.view.contract.ChildSmartRegisterClient;
import org.ei.telemedicine.view.contract.FPAlertType;
import org.ei.telemedicine.view.contract.FPSmartRegisterClient;
import org.ei.telemedicine.view.contract.RefillFollowUps;
import org.ei.telemedicine.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.telemedicine.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativePNCSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.OnClickFormLauncher;

import android.view.View;

public class FPAllMethodsServiceMode extends ServiceModeOption {

    public FPAllMethodsServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_register_service_mode_all_methods);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return new ClientsHeaderProvider() {
            @Override
            public int count() {
                return 6;
            }

            @Override
            public int weightSum() {
                return 100;
            }

            @Override
            public int[] weights() {
                return new int[]{24, 6, 11, 20, 20, 19};
            }

            @Override
            public int[] headerTextResourceIds() {
                return new int[]{
                        R.string.header_name, R.string.header_ec_no, R.string.header_gplsa,
                        R.string.header_method, R.string.header_side_effects, R.string.header_followup_refill};
            }
        };
    }

    @Override
    public void setupListView(FPSmartRegisterClient client,
                              NativeFPSmartRegisterViewHolder viewHolder,
                              View.OnClickListener onClickListener) {
        viewHolder.serviceModeFPMethod().setVisibility(VISIBLE);

        setupSideEffectsView(client, viewHolder);
        setupSideEffectsButtonView(client, viewHolder);
        setupUpdateButtonView(client, viewHolder, onClickListener);
        setupFPMethodView(client, viewHolder, getInstance().getColorResource(R.color.text_black));
        setupAlertView(client, viewHolder);
    }

    private void setupSideEffectsButtonView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.btnSideEffectsView().setOnClickListener(launchForm(FP_COMPLICATIONS, client.entityId(), null));
        viewHolder.btnSideEffectsView().setTag(client);
    }

    private void setupSideEffectsView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.clientSideEffectsView().bindData(client);
    }

    private void setupUpdateButtonView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder,
                                       View.OnClickListener onClickListener) {
        viewHolder.btnUpdateView().setOnClickListener(onClickListener);
        viewHolder.btnUpdateView().setTag(client);
    }

    private void setupFPMethodView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder, int txtColorBlack) {
        viewHolder.fpMethodView().bindData(client, txtColorBlack);
        viewHolder.fpMethodView().setTag(client);
    }

    private void setupAlertView(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.txtAlertTypeView().setTag(client);
        refreshAlertView(viewHolder);
        bindAlertData(client, viewHolder);
    }

    private void refreshAlertView(NativeFPSmartRegisterViewHolder viewHolder) {
        viewHolder.txtAlertTypeView().setText("");
        viewHolder.txtAlertTypeView().setTextColor(0);
        viewHolder.fpAlertLayout().setBackgroundResource(0);
        viewHolder.txtAlertDateView().setTextColor(0);
        viewHolder.txtAlertDateView().setText("");
        viewHolder.fpAlertLayout().setOnClickListener(null);
    }

    //#TODO: REMOVE THE HARDCODED METADATA
    private void bindAlertData(FPSmartRegisterClient client, NativeFPSmartRegisterViewHolder viewHolder) {
        RefillFollowUps refillFollowUps = client.refillFollowUps();
        if (refillFollowUps == null) {
            return;
        }
        AlertStatus alertStatus = refillFollowUps.alert().alertStatus();

        viewHolder.txtAlertTypeView().setText(FPAlertType.from(refillFollowUps.type()).getAlertType());
        viewHolder.txtAlertTypeView().setTextColor(alertStatus.fontColor());
        viewHolder.fpAlertLayout().setBackgroundResource(alertStatus.backgroundColorResourceId());
        viewHolder.txtAlertDateView().setTextColor(alertStatus.fontColor());
        viewHolder.txtAlertDateView().setText(getInstance().getStringResource(R.string.str_due) + refillFollowUps.alert().shortDate());
        viewHolder.fpAlertLayout().setOnClickListener(launchForm(FPAlertType.from(refillFollowUps.type()).getFormName(),
                client.entityId(), "{\"entityId\": \"" + client.entityId() + "\", \"alertName\":\"" + client.refillFollowUps().name() + "\"}"));
    }

    private OnClickFormLauncher launchForm(String formName, String entityId, String metaData) {
        return provider().newFormLauncher(formName, entityId, metaData);
    }

    @Override
    public void setupListView(ChildSmartRegisterClient client,
                              NativeChildSmartRegisterViewHolder viewHolder,
                              View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(ANCSmartRegisterClient client, NativeANCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

    @Override
    public void setupListView(PNCSmartRegisterClient client, NativePNCSmartRegisterViewHolder viewHolder, View.OnClickListener clientSectionClickListener) {

    }

}
