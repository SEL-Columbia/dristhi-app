package org.ei.drishti.provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.activity.NativeECSmartRegisterActivity;
import org.ei.drishti.view.contract.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SmartECRegisterClientsProvider
        implements SmartRegisterClientsProvider, View.OnClickListener {

    private final LayoutInflater inflater;
    private final Context context;
    protected Person[] people;

    public SmartECRegisterClientsProvider(Context context, Person[] people) {
        this.people = people;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    //#TODO: This is HACK, to identify the footer view while recycling the list items.
    private boolean isFooterView(View convertView) {
        return convertView instanceof RelativeLayout;
    }

    @Override
    public void onClick(View view) {
    }

    //#TODO: Very inefficient - Try some good algorithm
    @Override
    public List<String> getAllUniqueVillageNames() {
        List<String> villages = new ArrayList<String>();
        for (int i = 0; i < people.length; i++) {
            String village = people[i].villageName;
            if (!isVillageNameAlreadyPresent(villages, village)) {
                villages.add(village);
            }
        }
        return villages;
    }

    @Override
    public List<Person> getListItems() {
        return Arrays.asList(people);
    }

    @Override
    public void sort(String sortBy) {
        Arrays.sort(people, getComparator(sortBy));
    }

    @Override
    public List<Person> filter(CharSequence cs) {
        List<Person> results;
        if (cs.length() > 0) {
            String filter = cs.toString().toLowerCase();
            List<Person> filteredPeople = new ArrayList<Person>();
            for (Person aPeople : people) {
                if (aPeople.willFilterMatches(filter)) {
                    filteredPeople.add(aPeople);
                }
            }
            results = filteredPeople;
        } else {
            results = Arrays.asList(people);
        }
        return results;
    }

    @Override
    public ViewGroup getFooterView() {
        return (ViewGroup) inflater.inflate(R.layout.smart_register_pagination, null);
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    public Context context() {
        return context;
    }

    @Override
    public void showSection(String section) {
        // do Nothing;
    }

    private boolean isVillageNameAlreadyPresent(List<String> villages, String newVillage) {
        boolean found = false;
        for (String village : villages) {
            if (village.equalsIgnoreCase(newVillage)) {
                found = true;
            }
        }
        return found;
    }

    private Comparator<? super Person> getComparator(String sortBy) {
        if (sortBy.equalsIgnoreCase(NativeECSmartRegisterActivity.SORT_BY_AGE)) {
            return Person.AGE_COMPARATOR;
        } else if (sortBy.equalsIgnoreCase(NativeECSmartRegisterActivity.SORT_BY_EC_NO)) {
            return Person.EC_NO_COMPARATOR;
        }
        return Person.NAME_COMPARATOR;
    }

    protected static class BaseViewHolder {
        TextView txtNameView;
        TextView txtHusbandNameView;
        TextView txtVillageNameView;
        TextView txtAgeView;
        TextView txtEcNumberView;

        public static BaseViewHolder getViewHolder(ViewGroup itemView) {
            BaseViewHolder viewHolder = new BaseViewHolder();
            viewHolder.txtNameView = (TextView) itemView.findViewById(R.id.txt_person_name);
            viewHolder.txtHusbandNameView = (TextView) itemView.findViewById(R.id.txt_husband_name);
            viewHolder.txtVillageNameView = (TextView) itemView.findViewById(R.id.txt_village_name);
            viewHolder.txtAgeView = (TextView) itemView.findViewById(R.id.txt_age);
            viewHolder.txtEcNumberView = (TextView) itemView.findViewById(R.id.txt_ec_number);
            return viewHolder;
        }
    }
}
