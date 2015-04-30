package org.ei.opensrp.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.ei.opensrp.R;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;

import atv.holder.SelectableItemHolder;
import atv.model.TreeNode;
import atv.view.AndroidTreeView;

public class LocationSelectorDialogFragment extends DialogFragment {
    private final SecuredNativeSmartRegisterActivity parentActivity;
    private final DialogOption[] options;
    private final DialogOptionModel dialogOptionModel;
    private final Object tag;

    private LocationSelectorDialogFragment(SecuredNativeSmartRegisterActivity activity,
                                           DialogOptionModel dialogOptionModel,
                                           Object tag) {
        this.parentActivity = activity;
        this.options = dialogOptionModel.getDialogOptions();
        this.dialogOptionModel = dialogOptionModel;
        this.tag = tag;
    }

    public static LocationSelectorDialogFragment newInstance(
            SecuredNativeSmartRegisterActivity activity,
            DialogOptionModel dialogOptionModel, Object tag) {
        return new LocationSelectorDialogFragment(activity, dialogOptionModel, tag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup dialogView = new LinearLayout(getActivity());
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("MyParentNode");
        parent.setSelectable(true);
        parent.setSelected(true);
        TreeNode child0 = new TreeNode("File1").setViewHolder(new SelectableItemHolder(getActivity()));
        child0.setSelectable(true);
        TreeNode child1 = new TreeNode("File2").setViewHolder(new SelectableItemHolder(getActivity()));
        child1.setSelectable(true);

        parent.addChildren(child0, child1);
        root.addChild(parent);
        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);

        tView.setSelectionModeEnabled(true);
//        tView.getSelected().get(1).
        dialogView.addView(tView.getView());
        return dialogView;
    }
}
