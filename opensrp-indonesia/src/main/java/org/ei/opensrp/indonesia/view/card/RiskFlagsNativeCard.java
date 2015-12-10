package org.ei.opensrp.indonesia.view.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ei.opensrp.indonesia.AllConstantsINA;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.util.StringUtil;
import org.ei.opensrp.indonesia.view.contract.BidanSmartRegisterClient;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by Dimas Ciputra on 6/20/15.
 */
public class RiskFlagsNativeCard extends CardWithList {

    BidanSmartRegisterClient bidanClient;

    public RiskFlagsNativeCard(Context context) { super(context); }

    public void setBidanClient(BidanSmartRegisterClient bidanClient) {
        this.bidanClient = bidanClient;
    }

    @Override
    protected CardHeader initCardHeader() {
        // Add Header
        CardHeader header = new CardHeader(getContext(), R.layout.list_card_header) {
            @Override
            public void setupInnerViewElements(ViewGroup parent, View view) {
                super.setupInnerViewElements(parent, view);
                TextView subTitle = (TextView) view.findViewById(R.id.carddemo_googlenow_main_inner_lastupdate);
                if(subTitle!=null) {
                    subTitle.setText(
                            getContext().getResources().getString(R.string.risk_flags_subtitle));
                }
            }
        };

        header.setTitle(getContext().getResources().getString(R.string.risk_flags_title));
        return header;
    }

    @Override
    protected void initCard() {
        setSwipeable(false);
    }

    @Override
    protected List<ListObject> initChildren() {

        List<ListObject> mObject = new ArrayList<>();

        for(String reason : bidanClient.highRiskReason()) {
            StockObject highRiskReason = new StockObject(this);
            highRiskReason.value = StringUtil.humanize(reason);
            highRiskReason.subject = AllConstantsINA.HIGH_RISK;
            mObject.add(highRiskReason);
        }

        for(String reason1 : bidanClient.highPregnancyReason()) {
            StockObject highRiskPregnancyReason = new StockObject(this);
            highRiskPregnancyReason.value = StringUtil.humanize(reason1);
            highRiskPregnancyReason.subject = AllConstantsINA.HIGH_RISK_PREGNANCY;
            mObject.add(highRiskPregnancyReason);
        }

        for(String reason : bidanClient.highRiskLabourReason()) {
            StockObject highRiskLabourReason = new StockObject(this);
            highRiskLabourReason.value = StringUtil.humanize(reason);
            highRiskLabourReason.subject = AllConstantsINA.HIGH_RISK_LABOUR;
            mObject.add(highRiskLabourReason);
        }

        for(String reason : bidanClient.highRiskPostPartumReason()) {
            StockObject hrReason = new StockObject(this);
            hrReason.value = reason;
            hrReason.subject = AllConstantsINA.HIGH_RISK_POST_PARTUM;
            mObject.add(hrReason);
        }

        return mObject;
    }

    @Override
    public View setupChildView(int i, ListObject listObject, View convertView, ViewGroup viewGroup) {
        //Setup the ui elements inside the item
        TextView textViewSubject = (TextView) convertView.findViewById(R.id.text_view_reason);
        ImageView hrpBadge = (ImageView) convertView.findViewById(R.id.img_hp_badge);
        ImageView rtpBadge = (ImageView) convertView.findViewById(R.id.img_rtp_badge);
        ImageView rtkBadge = (ImageView) convertView.findViewById(R.id.img_rtk_badge);
        ImageView hrppBadge = (ImageView) convertView.findViewById(R.id.img_hrpp_badge);

        //Retrieve the values from the object
        StockObject stockObject = (StockObject) listObject;
        textViewSubject.setText(stockObject.value);

        switch (stockObject.subject) {
            case AllConstantsINA.HIGH_RISK :
                hrpBadge.setVisibility(View.VISIBLE);
                break;
            case AllConstantsINA.HIGH_RISK_LABOUR :
                rtpBadge.setVisibility(View.VISIBLE);
                break;
            case AllConstantsINA.HIGH_RISK_PREGNANCY :
                rtkBadge.setVisibility(View.VISIBLE);
                break;
            case AllConstantsINA.HIGH_RISK_POST_PARTUM :
                hrppBadge.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.risk_flags_list_card_inner_main;
    }

    public class StockObject extends DefaultListObject {

        public String subject;
        public String value;

        public StockObject(Card parentCard) {
            super(parentCard);
            init();
        }

        private void init() {
            //OnClick Listener
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {

                }
            });
        }

        @Override
        public String getObjectId() {
            return subject;
        }

        public String getValue() { return value; }
    }
}
