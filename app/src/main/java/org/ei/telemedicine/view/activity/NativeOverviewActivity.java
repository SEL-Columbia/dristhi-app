package org.ei.telemedicine.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.apache.commons.lang3.text.WordUtils;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.adapter.OverviewTimelineAdapter;
import org.ei.telemedicine.doctor.DoctorFormDataConstants;
import org.ei.telemedicine.doctor.ViewPreVisitScreenActivity;
import org.ei.telemedicine.domain.Child;
import org.ei.telemedicine.domain.EligibleCouple;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.TimelineEvent;
import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.event.CapturedPhotoInformation;
import org.ei.telemedicine.event.Event;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.image.ImageLoader;
import org.ei.telemedicine.view.controller.ANCDetailController;
import org.ei.telemedicine.view.controller.ChildDetailController;
import org.ei.telemedicine.view.controller.EligibleCoupleDetailController;
import org.ei.telemedicine.view.controller.PNCDetailController;
import org.ei.telemedicine.view.customControls.CustomFontTextView;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static android.view.View.GONE;
import static org.ei.telemedicine.AllConstants.ALLFORMDATA;
import static org.ei.telemedicine.AllConstants.CHILD_TYPE;
import static org.ei.telemedicine.AllConstants.ENTITY_ID;
import static org.ei.telemedicine.AllConstants.FORMINFO;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_CLOSE;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_INVESTIGATIONS;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_REGISTRATION;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_VISIT;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_VISIT_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_CLOSE;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_ILLNESS;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_IMMUNIZATIONS;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_REGISTRATION_EC;
import static org.ei.telemedicine.AllConstants.FormNames.DELIVERY_OUTCOME;
import static org.ei.telemedicine.AllConstants.FormNames.DELIVERY_PLAN;
import static org.ei.telemedicine.AllConstants.FormNames.EC_CLOSE;
import static org.ei.telemedicine.AllConstants.FormNames.EC_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.FP_CHANGE;
import static org.ei.telemedicine.AllConstants.FormNames.HB_TEST;
import static org.ei.telemedicine.AllConstants.FormNames.IFA;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_CLOSE;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_POSTPARTUM_FAMILY_PLANNING;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_VISIT;
import static org.ei.telemedicine.AllConstants.FormNames.TT;
import static org.ei.telemedicine.AllConstants.FormNames.VIEW_EC_REGISTRATION;
import static org.ei.telemedicine.AllConstants.FormNames.VITAMIN_A;
import static org.ei.telemedicine.AllConstants.IS_FP;
import static org.ei.telemedicine.AllConstants.VISIT_TYPE;
import static org.ei.telemedicine.AllConstants.WOMAN_TYPE;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_gender;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.id_no;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.visit_type;
import static org.ei.telemedicine.event.Event.ON_PHOTO_CAPTURED;

public class NativeOverviewActivity extends SecuredActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {
    ImageButton ib_overview_options, ib_profile_pic;
    ImageView iv_home;
    CustomFontTextView tv_wife_name, tv_woman_name, tv_priority, tv_summary_priority, tv_husband_name, tv_id_no, tv_village_name, tv_fp_value, tv_register_type;
    CustomFontTextView anc_summary_priority, anc_pregnency_time, anc_edd_summary_date, anc_pregnency_title;
    CustomFontTextView pnc_summary_priority, pnc_summary_risks, pnc_postpartum_days, pnc_delivery_date, postpartum_title;
    CustomFontTextView tv_child_name, child_summary_priority, child_age, child_date_of_birth;
    ListView lv_timeline_events;
    List<org.ei.telemedicine.domain.TimelineEvent> timelineEvents;
    String caseId, visitType, formData, allFormData;
    View ecSummaryLayout, ancSummaryLayout, pncSummaryLayout, childSummaryLayout;
    Context context;
    LinearLayout ec_summary, anc_summary, pnc_summary, child_summary, anc_pregnency_summary, pnc_pregnency_summary;
    static Boolean isANCOA = false, isFromEC = false;
    Boolean isPoc = false, isFP = false;

    private Listener<CapturedPhotoInformation> photoCaptureListener = new Listener<CapturedPhotoInformation>() {
        @Override
        public void onEvent(CapturedPhotoInformation data) {
            Log.e("Cpatu", "Think pgto is capture.");

        }
    };


    private void setupViews() {

        tv_priority = (CustomFontTextView) findViewById(R.id.tv_priority);
        tv_woman_name = (CustomFontTextView) findViewById(R.id.tv_woman_name);
        tv_wife_name = (CustomFontTextView) findViewById(R.id.tv_wife_name);
        tv_husband_name = (CustomFontTextView) findViewById(R.id.tv_husband_name);
        tv_village_name = (CustomFontTextView) findViewById(R.id.tv_village_name);
        tv_id_no = (CustomFontTextView) findViewById(R.id.tv_id_no);
        tv_register_type = (CustomFontTextView) findViewById(R.id.tv_register_type);

        ib_overview_options = (ImageButton) findViewById(R.id.ib_overview_options);
        lv_timeline_events = (ListView) findViewById(R.id.lv_timeline_events);
        iv_home = (ImageView) findViewById(R.id.iv_home);

        ecSummaryLayout = findViewById(R.id.ec_summary_layout);
        ancSummaryLayout = findViewById(R.id.anc_summary_layout);
        pncSummaryLayout = findViewById(R.id.pnc_summary_layout);
        childSummaryLayout = findViewById(R.id.child_summary_layout);

        ec_summary = (LinearLayout) findViewById(R.id.ec_summary);
        anc_summary = (LinearLayout) findViewById(R.id.anc_summary);
        pnc_summary = (LinearLayout) findViewById(R.id.pnc_summary);
        child_summary = (LinearLayout) findViewById(R.id.child_summary);

        tv_summary_priority = (CustomFontTextView) ecSummaryLayout.findViewById(R.id.summary_priority);
        tv_fp_value = (CustomFontTextView) ecSummaryLayout.findViewById(R.id.tv_fp_value);


        anc_summary_priority = (CustomFontTextView) ancSummaryLayout.findViewById(R.id.anc_summary_priority);
        anc_pregnency_time = (CustomFontTextView) ancSummaryLayout.findViewById(R.id.anc_pregnency_time);
        anc_edd_summary_date = (CustomFontTextView) ancSummaryLayout.findViewById(R.id.anc_edd_summary_date);
        anc_pregnency_title = (CustomFontTextView) ancSummaryLayout.findViewById(R.id.anc_pregnency_title);
        anc_pregnency_summary = (LinearLayout) ancSummaryLayout.findViewById(R.id.anc_pregnency_summary);

        pnc_pregnency_summary = (LinearLayout) pncSummaryLayout.findViewById(R.id.pnc_pregnency_summary);
        pnc_summary_priority = (CustomFontTextView) pncSummaryLayout.findViewById(R.id.pnc_summary_priority);
        pnc_summary_risks = (CustomFontTextView) pncSummaryLayout.findViewById(R.id.pnc_summary_risks);
        pnc_postpartum_days = (CustomFontTextView) pncSummaryLayout.findViewById(R.id.pnc_postpartum_days);
        pnc_delivery_date = (CustomFontTextView) pncSummaryLayout.findViewById(R.id.pnc_delivery_date);
        postpartum_title = (CustomFontTextView) pncSummaryLayout.findViewById(R.id.postpartum_title);

        tv_child_name = (CustomFontTextView) findViewById(R.id.tv_child_name);
        child_summary_priority = (CustomFontTextView) childSummaryLayout.findViewById(R.id.child_summary_priority);
        child_age = (CustomFontTextView) childSummaryLayout.findViewById(R.id.child_age);
        child_date_of_birth = (CustomFontTextView) childSummaryLayout.findViewById(R.id.child_date_of_birth);
        ib_profile_pic = (ImageButton) findViewById(R.id.ib_profile_pic);

        ib_overview_options.setOnClickListener(this);
        iv_home.setOnClickListener(this);
    }

    private boolean isExist(String title, String referDate, List<org.ei.telemedicine.domain.TimelineEvent> timelineEventslist) {
        if (timelineEventslist.size() != 0) {
            ListIterator<TimelineEvent> listIterator = timelineEventslist.listIterator();
            while (listIterator.hasNext()) {
                TimelineEvent event = listIterator.next();
                if (title.equalsIgnoreCase(event.title()) && referDate.equalsIgnoreCase(event.referenceDate().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onCreation() {
        {
            setContentView(R.layout.overview_layout);

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                caseId = bundle.getString("caseId");
                formData = bundle.containsKey(FORMINFO) ? bundle.getString(FORMINFO) : "";
                allFormData = bundle.containsKey(ALLFORMDATA) ? bundle.getString(ALLFORMDATA) : "";
                isFP = bundle.containsKey(IS_FP) ? bundle.getBoolean(IS_FP) : false;
                visitType = bundle.getString(VISIT_TYPE);
                context = Context.getInstance();
                setupViews();
                timelineEvents = context.allTimelineEvents().forCase(caseId);
                if (isFP)
                    ib_overview_options.setVisibility(GONE);

                switch (visitType.toLowerCase()) {
                    case "ec":
                        ec_summary.setVisibility(View.VISIBLE);
                        isFromEC = true;
                        ib_profile_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                takePhoto(WOMAN_TYPE);
                                dispatchTakePictureIntent(ib_profile_pic, caseId);
                            }
                        });
                        String ecData = new EligibleCoupleDetailController(this, caseId, context.allEligibleCouples(), context.allTimelineEvents()).get();
                        String coupleDetails = getDataFromJson(ecData, "coupleDetails");
                        String details = getDataFromJson(ecData, "details");
                        EligibleCouple byCaseID = context.allEligibleCouples().findByCaseID(caseId);
                        if (byCaseID.photoPath() != null && byCaseID.photoPath().equals("") && byCaseID.photoPath().toLowerCase().startsWith("http"))
                            new ImageLoader(NativeOverviewActivity.this).DisplayImage(byCaseID.photoPath(), ib_profile_pic, getResources().getDrawable(R.drawable.woman_placeholder));
                        else
                            ib_profile_pic.setImageBitmap(getImageBitmap(byCaseID.photoPath()));
                        if (byCaseID.photoPath() == null)
                            ib_profile_pic.setImageDrawable(getResources().getDrawable(R.drawable.woman_placeholder));
                        Mother motherData = context.allBeneficiaries().findMotherByECCaseId(caseId);
                        if (motherData != null) {
                            android.util.Log.e("ecData", ecData + "------------" + motherData.caseId());
                            timelineEvents.addAll(context.allTimelineEvents().forCase(motherData.caseId()));
                        }

                        tv_register_type.setText(isFP ? "FP" : visitType.toUpperCase());
                        tv_woman_name.setText(WordUtils.capitalize(getDataFromJson(coupleDetails, "wifeName")));
                        tv_wife_name.setText(WordUtils.capitalize(getDataFromJson(coupleDetails, "wifeName")));
                        tv_husband_name.setText(WordUtils.capitalize(getDataFromJson(coupleDetails, "husbandName")));
                        tv_id_no.setText("No: " + WordUtils.capitalize(getDataFromJson(coupleDetails, "ecNumber")));
                        tv_village_name.setText(WordUtils.capitalize(getDataFromJson(ecData, "village")));

                        tv_priority.setText(getDataFromJson(details, "isHighPriority").equals("yes") ? "High Priority" : "Normal Priority");
                        tv_priority.setTextColor(getDataFromJson(details, "isHighPriority").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));

                        tv_summary_priority.setText(getDataFromJson(details, "isHighPriority").equals("yes") ? "High Priority" : "Normal Priority");
                        tv_summary_priority.setTextColor(getDataFromJson(details, "isHighPriority").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
                        tv_fp_value.setText(WordUtils.capitalize(!getDataFromJson(details, "currentMethod").equals("") ? getDataFromJson(details, "currentMethod") : "none"));
                        break;
                    case "anc":
//                        ImageLoader imageLoader = new ImageLoader(this);
//                        imageLoader.DisplayImage("http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/1.jpg", ib_profile_pic);
                        anc_summary.setVisibility(View.VISIBLE);
                        final Mother mother = context.allBeneficiaries().findMother(caseId);
                        ib_profile_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                takePhoto(WOMAN_TYPE);
                                dispatchTakePictureIntent(ib_profile_pic, mother != null ? mother.ecCaseId() : caseId);
                            }
                        });
                        if (mother != null) {
                            String pocInfo = mother.getDetail("docPocInfo") != null ? mother.getDetail("docPocInfo") : "";
                            if (!pocInfo.equals("")) {
                                isPoc = true;
                            }
                        }
                        String ancData = new ANCDetailController(this, caseId, context.allEligibleCouples(), context.allBeneficiaries(), context.allTimelineEvents()).get();
                        String anccoupleDetails = getDataFromJson(ancData, "coupleDetails");
                        String ancDetails = getDataFromJson(ancData, "details");
                        String locationDetails = getDataFromJson(ancData, "location");
                        String pregnancyDetails = getDataFromJson(ancData, "pregnancyDetails");

                        android.util.Log.e("anc Data", ancData);
                        tv_register_type.setText(visitType.toUpperCase());
                        tv_woman_name.setText(WordUtils.capitalize(getDataFromJson(anccoupleDetails, "wifeName")));
                        tv_wife_name.setText(WordUtils.capitalize(getDataFromJson(anccoupleDetails, "wifeName")));
                        tv_husband_name.setText(WordUtils.capitalize(getDataFromJson(anccoupleDetails, "husbandName")));
//                        isANCOA = getDataFromJson(ancDetails, "ancNumber").toLowerCase().contains("oa");
                        tv_id_no.setText("ANC No: " + WordUtils.capitalize(getDataFromJson(ancDetails, "ancNumber")));
                        tv_village_name.setText(WordUtils.capitalize(getDataFromJson(locationDetails, "villageName")));

                        tv_priority.setText(getDataFromJson(ancDetails, "isHighRisk").equals("yes") ? "High Risk" : "Normal Risk");
                        tv_priority.setTextColor(getDataFromJson(ancDetails, "isHighRisk").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));

                        anc_summary_priority.setText(getDataFromJson(ancDetails, "isHighRisk").equals("yes") ? "High Risk" : "Normal Risk");
                        anc_summary_priority.setTextColor(getDataFromJson(ancDetails, "isHighRisk").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
                        anc_pregnency_time.setText(getDataFromJson(pregnancyDetails, "monthsPregnant"));
                        anc_edd_summary_date.setText(getDataFromJson(ancDetails, "edd").replace(" 00:00:00 GMT", ""));

                        break;
                    case "pnc":

                        pnc_summary.setVisibility(View.VISIBLE);
                        final Mother pncMother = context.allBeneficiaries().findMother(caseId);
                        ib_profile_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                takePhoto(WOMAN_TYPE);
                                dispatchTakePictureIntent(ib_profile_pic, pncMother != null ? pncMother.ecCaseId() : caseId);
                            }
                        });
                        if (pncMother != null) {
                            String pocInfo = pncMother.getDetail("docPocInfo") != null ? pncMother.getDetail("docPocInfo") : "";
                            if (!pocInfo.equals("")) {
                                isPoc = true;
                            }
                        }
//                                try {
//                                isPoc = true;
//                                    JSONArray pocJsonArray = new JSONArray(pocInfo);
//                                    for (int i = 0; i < pocJsonArray.length(); i++) {
//                                        String pocData = getDataFromJson(pocJsonArray.getJSONObject(i).toString(), "poc");
//                                        String title = "Plan of care for " + getDataFromJson(pocData, "visitType");
//                                        timelineEvents.add(new TimelineEvent(caseId, "Plan Of Care", LocalDate.parse(changeDateFormat(getDataFromJson(pocData, "planofCareDate"))), title, "", ""));
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
                        String pncData = new PNCDetailController(this, caseId, context.allEligibleCouples(), context.allBeneficiaries(), context.allTimelineEvents()).get();
                        String pnccoupleDetails = getDataFromJson(pncData, "coupleDetails");
                        String pncDetails = getDataFromJson(pncData, "details");
                        String pncLocationDetails = getDataFromJson(pncData, "location");
                        String pncDelieveryDetails = getDataFromJson(pncData, "pncDetails");

                        android.util.Log.e("pncData", pncData);

                        tv_register_type.setText(visitType.toUpperCase());
                        tv_woman_name.setText(WordUtils.capitalize(getDataFromJson(pnccoupleDetails, "wifeName")));
                        tv_wife_name.setText(WordUtils.capitalize(getDataFromJson(pnccoupleDetails, "wifeName")));
                        tv_husband_name.setText(WordUtils.capitalize(getDataFromJson(pnccoupleDetails, "husbandName")));
                        tv_id_no.setText("PNC No: " + WordUtils.capitalize(getDataFromJson(pncDetails, "pncNumber")));
                        tv_village_name.setText(WordUtils.capitalize(getDataFromJson(pncLocationDetails, "villageName")));

                        tv_priority.setText(getDataFromJson(pncDetails, "isHighRisk").equals("yes") ? "High Risk" : "Normal Risk");
                        tv_priority.setTextColor(getDataFromJson(pncDetails, "isHighRisk").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
                        pnc_summary_priority.setText(getDataFromJson(pncDetails, "isHighRisk").equals("yes") ? "High Risk" : "Normal Risk");
                        pnc_summary_priority.setTextColor(getDataFromJson(pncDetails, "isHighRisk").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));

                        pnc_summary_risks.setText(getDataFromJson(pncDetails, "complications"));
                        pnc_postpartum_days.setVisibility(GONE);
                        pnc_delivery_date.setText(getDataFromJson(pncDelieveryDetails, "dateOfDelivery"));

                        break;
                    case "child":
                        child_summary.setVisibility(View.VISIBLE);
                        tv_child_name.setVisibility(View.VISIBLE);
                        String childData = new ChildDetailController(this, caseId, context.allEligibleCouples(), context.allBeneficiaries(), context.allTimelineEvents()).get();

                        final Child child = context.allBeneficiaries().findChild(caseId);
                        Toast.makeText(this, "sda" + child.motherCaseId(), Toast.LENGTH_SHORT).show();
                        final Mother childMother = context.allBeneficiaries().findMother(child.motherCaseId());
                        ib_profile_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                takePhoto(CHILD_TYPE);
                                dispatchTakePictureIntent(ib_profile_pic, childMother != null ? child.motherCaseId() : caseId);
                            }
                        });

                        if (childMother != null) {
                            String pocInfo = childMother.getDetail("docPocInfo") != null ? childMother.getDetail("docPocInfo") : "";
                            if (!pocInfo.equals("")) {
                                isPoc = true;
                            }
                        }
//                        try {
//                            isPoc = true;
//                                    JSONArray pocJsonArray = new JSONArray(pocInfo);
//                                    for (int i = 0; i < pocJsonArray.length(); i++) {
//                                        String pocData = getDataFromJson(pocJsonArray.getJSONObject(i).toString(), "poc");
//                                        String title = "Plan of care for child";
//                                        timelineEvents.add(new TimelineEvent(caseId, "Plan Of Care", LocalDate.parse(changeDateFormat(getDataFromJson(pocData, "planofCareDate"))), title, "", ""));
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }


                        String childCoupleDetails = getDataFromJson(childData, "coupleDetails");
                        String childDetails = getDataFromJson(childData, "details");
                        String childLocationDetails = getDataFromJson(childData, "location");
                        String childInfoDetails = getDataFromJson(childData, "childDetails");


                        tv_register_type.setText(visitType.toUpperCase());

                        tv_child_name.setText(getDataFromJson(childDetails, "name"));
                        tv_woman_name.setText(WordUtils.capitalize(getDataFromJson(childCoupleDetails, "wifeName")));
                        tv_wife_name.setText(WordUtils.capitalize(getDataFromJson(childCoupleDetails, "wifeName")));
                        tv_husband_name.setText(WordUtils.capitalize(getDataFromJson(childCoupleDetails, "husbandName")));
                        tv_id_no.setText("EC No: " + WordUtils.capitalize(getDataFromJson(childCoupleDetails, "ecNumber")));
                        tv_village_name.setText(WordUtils.capitalize(getDataFromJson(childLocationDetails, "villageName")));

                        tv_priority.setText(getDataFromJson(childDetails, "childReferral").equals("yes") ? "High Risk" : "Normal Risk");
                        tv_priority.setTextColor(getDataFromJson(childDetails, "childReferral").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
                        child_summary_priority.setText(getDataFromJson(childDetails, "childReferral").equals("yes") ? "High Risk" : "Normal Risk");
                        child_summary_priority.setTextColor(getDataFromJson(childDetails, "childReferral").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
                        ib_profile_pic.setImageResource(getDataFromJson(childInfoDetails, "gender").equalsIgnoreCase("female") ? R.drawable.child_girl_infant : R.drawable.child_boy_infant);
                        child_age.setText(getDataFromJson(childInfoDetails, "age"));
                        child_date_of_birth.setText(getDataFromJson(childInfoDetails, "dateOfBirth"));
                        break;
                    case "doctor":
//                        ImageLoader imageLoader2 = new ImageLoader(this);
//                        imageLoader2.DisplayImage("http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/1.jpg", ib_profile_pic);
                        ib_overview_options.setVisibility(GONE);
                        pnc_pregnency_summary.setVisibility(GONE);
                        anc_pregnency_summary.setVisibility(GONE);

                        tv_register_type.setText(visitType);
                        tv_woman_name.setText(WordUtils.capitalize(getDataFromJson(formData, "wifeName")));
                        tv_wife_name.setText(WordUtils.capitalize(getDataFromJson(formData, "wifeName")));
                        tv_husband_name.setText(WordUtils.capitalize(getDataFromJson(formData, "husbandName")));
                        tv_village_name.setText(WordUtils.capitalize(getDataFromJson(formData, "villageName")));
                        tv_id_no.setText(getDataFromJson(formData, id_no));
                        String visitType = getDataFromJson(formData, visit_type);

                        switch (visitType) {
                            case DoctorFormDataConstants.childVisit:
                                ib_profile_pic.setImageResource(getDataFromJson(formData, child_gender).equalsIgnoreCase("female") ? R.drawable.child_girl_infant : R.drawable.child_boy_infant);
                                child_summary.setVisibility(View.VISIBLE);

                                child_summary_priority.setText(getDataFromJson(formData, "childReferral").equals("yes") ? "High Risk" : "Normal Risk");
                                child_summary_priority.setTextColor(getDataFromJson(formData, "childReferral").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));

                                break;
                            case DoctorFormDataConstants.ancvisit:
                                ib_profile_pic.setImageResource(R.drawable.woman_placeholder);
                                anc_summary.setVisibility(View.VISIBLE);

                                anc_summary_priority.setText(getDataFromJson(formData, "isHighRisk").equals("yes") ? "High Risk" : "Normal Risk");
                                anc_summary_priority.setTextColor(getDataFromJson(formData, "isHighRisk").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
                                anc_pregnency_time.setVisibility(GONE);
                                anc_edd_summary_date.setText(getDataFromJson(formData, "edd").replace(" 00:00:00 GMT", ""));


                                break;
                            case DoctorFormDataConstants.pncVisit:
                                ib_profile_pic.setImageResource(R.drawable.woman_placeholder);
                                pnc_summary.setVisibility(View.VISIBLE);

                                pnc_summary_priority.setText(getDataFromJson(formData, "isHighRisk").equals("yes") ? "High Risk" : "Normal Risk");
                                pnc_summary_priority.setTextColor(getDataFromJson(formData, "isHighRisk").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));

                                break;

                        }
                        tv_summary_priority.setText(getDataFromJson(formData, "isHighPriority").equals("yes") ? "High Priority" : "Normal Priority");
                        tv_summary_priority.setTextColor(getDataFromJson(formData, "isHighPriority").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));

                        tv_priority.setText(getDataFromJson(formData, "isHighRisk").equals("yes") ? "High Risk" : "Normal Risk");
                        tv_priority.setTextColor(getDataFromJson(formData, "isHighRisk").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
                        try {
                            JSONArray visitsArray = new JSONArray(allFormData);
//                            android.util.Log.e("size", allFormInfo.length() + "");
//                            JSONArray visitsArray = new JSONArray(getDataFromJson(allFormInfo.getJSONObject(0).toString(), "riskinfo"));
                            timelineEvents.clear();
                            for (int i = 0; i < visitsArray.length(); i++) {
                                String jsonData = visitsArray.getJSONObject(i).toString();
                                String caseId = getDataFromJson(formData, entityId);
                                String title = "";
                                String date = getDataFromJson(formData, "visitDate");
                                LocalDate referDate = new LocalDate();
                                if (!date.equals(""))
                                    referDate = LocalDate.parse(changeDateFormat(date));
                                switch (getDataFromJson(jsonData, "visit_type")) {
                                    case DoctorFormDataConstants.ancvisit:
                                        title = "ANC " + (!getDataFromJson(jsonData, "visitNumber").equals("") ? (" - " + getDataFromJson(jsonData, "visitNumber")) : "");
                                        break;
                                    case DoctorFormDataConstants.pncVisit:
                                        title = "PNC Visit";
                                        break;
                                    case DoctorFormDataConstants.childVisit:
                                        title = "Child Illness";
                                        break;
                                }

                                String type = "doctorOverview";
                                String details2 = jsonData;
                                String details1 = (!getDataFromJson(jsonData, "bpDiastolic").equals("") ? "Bp Dia :" + getDataFromJson(jsonData, "bpDiastolic") + " " : "") +
                                        (!getDataFromJson(jsonData, "bpSystolic").equals("") ? "Bp Sys :" + getDataFromJson(jsonData, "bpSystolic") + " " : "") +
                                        (!getDataFromJson(jsonData, "temperature").equals("") ? "Temperature :" + getDataFromJson(jsonData, "temperature") + " " : "") +
                                        (!getDataFromJson(jsonData, "pulseRate").equals("") ? "Pulse :" + getDataFromJson(jsonData, "pulseRate") : "") + " " +
                                        (!getDataFromJson(jsonData, "bloodGlucoseData").equals("") ? "BGM :" + getDataFromJson(jsonData, "bloodGlucoseData") + " " : "");
                                timelineEvents.add(new TimelineEvent(caseId, type, referDate, title, details1, details2));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }

                lv_timeline_events.setAdapter(new OverviewTimelineAdapter(this, distinctTimelines(timelineEvents)));
                lv_timeline_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        switch (timelineEvents.get(position).type()) {
                            case "doctorOverview":
                                android.util.Log.e("Detail2", timelineEvents.get(position).detail2());
//                                Toast.makeText(NativeOverviewActivity.this, timelineEvents.get(position).detail2(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NativeOverviewActivity.this, ViewPreVisitScreenActivity.class);
                                intent.putExtra("formInfo", timelineEvents.get(position).detail2());
                                startActivity(intent);
                                break;
//                            case "ECREGISTERED":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "PREGNANCY":
//                                if (isANCOA)
//                                    startFormActivity(VIEW_ANC_REGISTRATION, caseId, null, true);
//                                else {
//                                    if (!isFromEC) {
//                                        startFormActivity(AllConstants.FormNames.VIEW_ANC_REGISTRATION_EC, context.allBeneficiaries().findMother(caseId).ecCaseId(), null, true);
//                                    } else
//                                        startFormActivity(AllConstants.FormNames.VIEW_ANC_REGISTRATION_EC, caseId, null, true);
//                                }
//                                break;
//                            case "CHILD-BIRTH":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "CHILD-DELIVERYPLAN":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "FPCHANGE":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "ANCVISIT":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "IFAPROVIDED":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "TTSHOTPROVIDED":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "PNCVISIT":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "IMMUNIZATIONSGIVEN":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            case "FPRENEW":
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
//                            default:
//                                startFormActivity(AllConstants.FormNames.VIEW_EC_REGISTRATION, caseId, null, true);
//                                break;
                        }
                    }
                });
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    static ImageView mImageView;
    static File currentfile;
    static String bindobject;
    static String entityId;

    private void dispatchTakePictureIntent(ImageView imageView, String entityid) {
        mImageView = imageView;
        entityId = entityid;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                currentfile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private Bitmap getImageBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            HashMap<String, String> details = new HashMap<String, String>();
            details.put("profilepic", currentfile.getAbsolutePath());
            saveimagereference(bindobject, entityId, details);
            ON_PHOTO_CAPTURED.notifyListeners(new CapturedPhotoInformation(entityId, currentfile.getAbsolutePath()));
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            Bitmap bitmap = BitmapFactory.decodeFile(currentfile.getPath(), options);
            mImageView.setImageBitmap(getImageBitmap(currentfile.getPath()));
        }
    }

    public List<TimelineEvent> distinctTimelines(List<TimelineEvent> timelineEvents) {
        Map<String, TimelineEvent> timelineEventsMap = new HashMap<String, TimelineEvent>();
        for (TimelineEvent timelineEvent : timelineEvents) {
            timelineEventsMap.put(timelineEvent.title(), timelineEvent);
        }
        timelineEvents.clear();
        for (Map.Entry<String, TimelineEvent> entry : timelineEventsMap.entrySet()) {
            timelineEvents.add(entry.getValue());
        }
        return timelineEvents;
    }

    public String changeDateFormat(String date) {
        if (!date.equals("")) {
            String vdate[] = date.split("-");
            return vdate[2] + "-" + vdate[1] + "-" + vdate[0];
        }
        return "";
    }

    public void takePhoto(String type) {
        ON_PHOTO_CAPTURED.addListener(photoCaptureListener);
        Intent intent = new Intent(this, CameraLaunchActivity.class);
        intent.putExtra(AllConstants.TYPE, type);
        intent.putExtra(ENTITY_ID, caseId);
        startActivity(intent);
    }


    @Override
    public void onResumption() {
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String formInfo = new FieldOverrides(context.anmLocationController().getFormInfoJSON()).getJSONString();
        switch (item.getItemId()) {
            //Overview Ec Menu
            case R.id.register_anc_through_ec:
                if (null == context.allBeneficiaries().findMotherByECCaseId(caseId)) {
                    formController.startFormActivity(ANC_REGISTRATION, caseId, formInfo);
                } else
                    Toast.makeText(NativeOverviewActivity.this, "ANC Registration is already completed", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.register_fp_through_ec:
                startFormActivity(FP_CHANGE, caseId, formInfo);
                return true;
            case R.id.register_child_through_ec:
                startFormActivity(CHILD_REGISTRATION_EC, caseId, formInfo);
                return true;
            case R.id.edit_ec:
                startFormActivity(EC_EDIT, caseId, formInfo);
                return true;
            case R.id.view_ec:
                startFormActivity(VIEW_EC_REGISTRATION, caseId, formInfo);
                return true;
            case R.id.close_ec:
                startFormActivity(EC_CLOSE, caseId, formInfo);
                return true;

            //Overview ANC Menu
            case R.id.anc_visit:
//                new OpenFormOption(getString(R.string.str_register_anc_visit_form), ANC_VISIT, new FormController(this)).doEdit(client);
                formController.startFormActivity(ANC_VISIT, caseId, formInfo);
                return true;
            case R.id.anc_visit_edit:
                if (!isPoc)
                    formController.startFormActivity(ANC_VISIT_EDIT, caseId, formInfo);
                else
                    Toast.makeText(this, "Already Poc given for this visit", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.hb_test:
                startFormActivity(HB_TEST, caseId, formInfo);
                return true;
            case R.id.view_poc_anc:
                viewPOCActivity(AllConstants.VisitTypes.ANC_VISIT, caseId);
                return true;
            case R.id.ifa:
                startFormActivity(IFA, caseId, formInfo);
                return true;
            case R.id.tt:
                startFormActivity(TT, caseId, formInfo);
                return true;
            case R.id.delivery_plan:
                startFormActivity(DELIVERY_PLAN, caseId, formInfo);
                return true;
            case R.id.pnc_registration:
                startFormActivity(DELIVERY_OUTCOME, caseId, formInfo);
                return true;
            case R.id.anc_investigations:
                startFormActivity(ANC_INVESTIGATIONS, caseId, formInfo);
                return true;
            case R.id.anc_close:
                startFormActivity(ANC_CLOSE, caseId, formInfo);
                return true;

            //Overview PNC Menu
            case R.id.pnc_visit:
                startFormActivity(PNC_VISIT, caseId, formInfo);
                return true;
            case R.id.view_poc_pnc:
                viewPOCActivity(AllConstants.VisitTypes.PNC_VISIT, caseId);
                return true;

            case R.id.postpartum_family_planning:
                startFormActivity(PNC_POSTPARTUM_FAMILY_PLANNING, caseId, formInfo);
                return true;
            case R.id.pnc_close:
                startFormActivity(PNC_CLOSE, caseId, formInfo);
                return true;

            //Overview CHILD Menu
            case R.id.child_immunizations:
                startFormActivity(CHILD_IMMUNIZATIONS, caseId, formInfo);
                return true;
            case R.id.view_poc_child:
                viewPOCActivity(AllConstants.VisitTypes.CHILD_VISIT, caseId);
                return true;
            case R.id.child_illness:
                startFormActivity(CHILD_ILLNESS, caseId, formInfo);
                return true;
            case R.id.vitamin_a:
                startFormActivity(VITAMIN_A, caseId, formInfo);
                return true;
            case R.id.child_close:
                startFormActivity(CHILD_CLOSE, caseId, formInfo);
                return true;

            default:
                return true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_overview_options:
                PopupMenu popup = new PopupMenu(this, v);
                MenuInflater inflater = popup.getMenuInflater();
                switch (visitType.toLowerCase()) {
                    case "ec":
                        inflater.inflate(R.menu.overview_ec_menu, popup.getMenu());
                        break;
                    case "anc":
                        inflater.inflate(R.menu.overview_anc_menu, popup.getMenu());
                        break;
                    case "pnc":
                        inflater.inflate(R.menu.overview_pnc_menu, popup.getMenu());
                        break;
                    case "child":
                        inflater.inflate(R.menu.overview_child_menu, popup.getMenu());
                        break;
                }
                popup.show();
                popup.setOnMenuItemClickListener(this);
                break;
            case R.id.iv_home:
                this.finish();
                break;
        }
    }


}
