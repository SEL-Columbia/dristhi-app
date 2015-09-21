package org.ei.opensrp.indonesia.view.activity;

import android.view.View;

import org.ei.opensrp.AllConstants;
import org.ei.opensrp.indonesia.Context;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.card.DetailsNativeCard;
import org.ei.opensrp.indonesia.view.card.RiskFlagsNativeCard;
import org.ei.opensrp.indonesia.view.controller.ProfileDetailController;
import org.ei.opensrp.view.activity.SecuredActivity;

import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by Dimas Ciputra on 9/21/15.
 */
public class MotherProfileViewActivity extends SecuredActivity {
    private ProfileDetailController profileDetailController;
    private String caseId;

    private final NavBarActionsHandler navBarActionsHandler = new NavBarActionsHandler();
    DetailsNativeCard detailsCard;
    RiskFlagsNativeCard riskFlagsCard;

    @Override
    protected void onCreation() {
        setContentView(R.layout.detail_main_card_view);
        initialize();
        setupNavBarViews();
    }

    @Override
    protected void onResumption() {

    }

    private void initialize() {

        // Create a card
        detailsCard = new DetailsNativeCard(this, controller().detailMap());
        detailsCard.setClient(controller().get());
        detailsCard.init();

        // Set card in cardView
        CardViewNative detailsCardView = (CardViewNative) findViewById(R.id.detail_list_card);
        detailsCardView.setCard(detailsCard);

        riskFlagsCard = new RiskFlagsNativeCard(this);
        riskFlagsCard.setBidanClient(controller().get());
        riskFlagsCard.init();

        // Set card in cardView
        CardViewNative riskFlagsCardView = (CardViewNative) findViewById(R.id.risk_flags_list_card);
        riskFlagsCardView.setCard(riskFlagsCard);

    }

    private void setupNavBarViews() {
        findViewById(R.id.btn_back_to_home).setOnClickListener(navBarActionsHandler);
    }

    public class NavBarActionsHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.title_layout:
                case R.id.btn_back_to_home:
                    goBack();
                    break;
            }
        }
    }

    private void goBack() {
        finish();
    }

    private String caseId() {
        if(caseId == null) {
            caseId = (String) getIntent().getExtras().get(AllConstants.CASE_ID);
        }
        return caseId;
    }

    private ProfileDetailController controller() {
        if(profileDetailController == null) {
            profileDetailController = new ProfileDetailController(caseId(),
                    ((Context)context).kartuIbuRegisterController(),
                    ((Context)context).allKartuIbus(),
                    ((Context)context).allKohort());
        }
        return profileDetailController;
    }
}
