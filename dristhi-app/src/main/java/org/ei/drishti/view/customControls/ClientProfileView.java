package org.ei.drishti.view.customControls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.viewHolder.ProfilePhotoLoader;

import static java.text.MessageFormat.format;

public class ClientProfileView extends RelativeLayout {
    private ImageView imgProfileView;
    private TextView txtNameView;
    private TextView txtHusbandNameView;
    private TextView txtVillageNameView;
    private TextView txtAgeView;
    private ImageView badgeHPView;
    private ImageView badgeBPLView;
    private ImageView badgeSCView;
    private ImageView badgeSTView;

    @SuppressWarnings("UnusedDeclaration")
    public ClientProfileView(Context context) {
        this(context, null, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public ClientProfileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClientProfileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize() {
        imgProfileView = (ImageView) findViewById(R.id.img_profile);
        txtNameView = (TextView) findViewById(R.id.txt_wife_name);
        txtHusbandNameView = (TextView) findViewById(R.id.txt_husband_name);
        txtVillageNameView = (TextView) findViewById(R.id.txt_village_name);
        txtAgeView = (TextView) findViewById(R.id.txt_age);
        badgeHPView = (ImageView) findViewById(R.id.img_hp_badge);
        badgeBPLView = (ImageView) findViewById(R.id.img_bpl_badge);
        badgeSCView = (ImageView) findViewById(R.id.img_sc_badge);
        badgeSTView = (ImageView) findViewById(R.id.img_st_badge);
    }

    public void bindData(SmartRegisterClient client, ProfilePhotoLoader photoLoader, String wifeAgeFormatString) {
        imgProfileView.setBackground(photoLoader.get(client));
        txtNameView.setText(client.name());
        txtHusbandNameView.setText(client.husbandName());
        txtVillageNameView.setText(client.village());
        txtAgeView.setText(format(wifeAgeFormatString, client.age()));
        badgeHPView.setVisibility(client.isHighPriority() ? View.VISIBLE : View.GONE);
        badgeBPLView.setVisibility(client.isBPL() ? View.VISIBLE : View.GONE);
        badgeSCView.setVisibility(client.isSC() ? View.VISIBLE : View.GONE);
        badgeSTView.setVisibility(client.isST() ? View.VISIBLE : View.GONE);
    }
}
