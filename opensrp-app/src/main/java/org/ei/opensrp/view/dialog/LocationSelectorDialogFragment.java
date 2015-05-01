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
import android.widget.Toast;

import org.ei.opensrp.R;
import org.ei.opensrp.domain.form.FieldOverrides;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;
import org.json.JSONObject;

import atv.holder.SelectableItemHolder;
import atv.model.TreeNode;
import atv.view.AndroidTreeView;
import sun.reflect.generics.tree.Tree;

import static org.ei.opensrp.AllConstants.FormNames.EC_REGISTRATION;

public class LocationSelectorDialogFragment extends DialogFragment {
    private final SecuredNativeSmartRegisterActivity parentActivity;
    private final DialogOption[] options;
    private final DialogOptionModel dialogOptionModel;
    public static String savestate ;
    AndroidTreeView tView;
//    private final Object tag;

    private LocationSelectorDialogFragment(SecuredNativeSmartRegisterActivity activity,
                                           DialogOptionModel dialogOptionModel
                                           ) {
        this.parentActivity = activity;
        this.options = dialogOptionModel.getDialogOptions();
        this.dialogOptionModel = dialogOptionModel;
//        this.tag = tag;
    }

    public static LocationSelectorDialogFragment newInstance(
            SecuredNativeSmartRegisterActivity activity,
            DialogOptionModel dialogOptionModel) {
        return new LocationSelectorDialogFragment(activity, dialogOptionModel);
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

        //creating the tree/////////////////////////
        TreeNode child1 = createNode("district","mysore");

        TreeNode child2 = createNode("phcName", "Bherya");

        TreeNode child3 = createNode("subCenter", "munjanahalli");

        TreeNode child4 = createNode("village", "chikkabherya");

        TreeNode child5 = createNode("village", "kavalu_hosur");

        TreeNode child6 = createNode("village", "munjanahalli");

        addChildToParentNode(root,new TreeNode[]{child1});
        addChildToParentNode(child1,new TreeNode[]{child2});
        addChildToParentNode(child2,new TreeNode[]{child3});
        addChildToParentNode(child3,new TreeNode[]{child4,child5,child6});
        //creating the tree/////////////////////////


         tView = new AndroidTreeView(getActivity(), root);

        tView.setSelectionModeEnabled(false);

        if(savestate != null){
            tView.restoreState(savestate);
        }

//        tView.getSelected().get(1).
        dialogView.addView(tView.getView());
        return dialogView;
    }
    public TreeNode createNode(String locationlevel, String locationname){
        TreeNode node = new TreeNode(locationname,locationlevel).setViewHolder(new SelectableItemHolder(getActivity(),locationlevel+": "));
        node.setSelectable(false);
        addselectlistener(node);
        return node;
    }
    public void addChildToParentNode(TreeNode parent,TreeNode [] nodes){
        for (int i = 0;i<nodes.length;i++){
            parent.addChild(nodes[i]);
        }
    }

    public void addselectlistener (TreeNode node){
        node.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                if(node.isLeaf()){
                    JSONObject locationjson = new JSONObject();
                    TreeNode traversingnode = node;
                    while(!traversingnode.isRoot()){
                        try {
                            locationjson.put(traversingnode.getlocationlevel(), traversingnode.getName());
                        }catch(Exception e){

                        }
                        traversingnode = traversingnode .getParent();
                    }
                    FieldOverrides fieldOverrides = new FieldOverrides(locationjson.toString());
//                    Toast.makeText(getActivity(),fieldOverrides.getJSONString(),Toast.LENGTH_LONG).show();
                    parentActivity.startFormActivity(EC_REGISTRATION, null, fieldOverrides.getJSONString());
//                    tView.
                    savestate = tView.getSaveState();
                    dismiss();
                }
            }
        });

    }

}
