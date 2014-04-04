package org.ei.drishti.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.Person;

/**
 * Created by thotego on 30/03/14.
 */
public class SmartECRegisterClientsProvider extends WrappedSmartRegisterClientsProvider
        implements View.OnClickListener {


    public SmartECRegisterClientsProvider(Context context, Person[] people) {
        super(context, people);
    }

    @Override
    public View getView(Person person, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;
        BaseViewHolder holder;
        if (convertView == null || isFooterView(convertView)) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_ec_client, null);
            holder = BaseViewHolder.getViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            itemView = (ViewGroup) convertView;
            holder = (BaseViewHolder) itemView.getTag();
        }

        holder.txtNameView.setText(person.name);
        holder.txtHusbandNameView.setText(person.husbandName);
        holder.txtVillageNameView.setText(person.villageName);
        holder.txtAgeView.setText("(" + person.age + ")");
        holder.txtEcNumberView.setText(String.valueOf(person.ecNumber));

        return itemView;
    }

    // This is HACK, to identify the footer view while recycling the list items.
    private boolean isFooterView(View convertView) {
        return convertView instanceof RelativeLayout;
    }

    @Override
    public void onClick(View view) {

    }


}
