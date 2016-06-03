/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ei.opensrp.mcare.household.tutorial;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import org.ei.opensrp.mcare.LoginActivity;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.repository.AllSharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class ImageAdapter extends BaseAdapter {
    public Context context;
	private LayoutInflater mInflater;
	private static final int[] ids = {R.mipmap.tutoria1, R.mipmap.tutorial2, R.mipmap.tutorial3};
	private static final int[] bengaliids = {R.mipmap.tutorial1bangla, R.mipmap.tutorial2bangla, R.mipmap.tutorialbangla3};

	public ImageAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    this.context = context;
    }

	@Override
	public int getCount() {
		return ids.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		AllSharedPreferences allSharedPreferences = new AllSharedPreferences(getDefaultSharedPreferences(org.ei.opensrp.Context.getInstance().applicationContext()));

		String preferredLocale = allSharedPreferences.fetchLanguagePreference();
		if (LoginActivity.ENGLISH_LOCALE.equals(preferredLocale)) {
			((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position]);
		}else if (LoginActivity.BENGALI_LOCALE.equals(preferredLocale)){
			((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(bengaliids[position]);

		}
        Button dismiss = ((Button) convertView.findViewById(R.id.dismiss));
        if(position == 2){
            dismiss.setVisibility(View.VISIBLE);
            dismiss.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((Activity) (context)).finish();
				}
			});
        }else{
            dismiss.setVisibility(View.INVISIBLE);


        }
        return convertView;
	}

}
