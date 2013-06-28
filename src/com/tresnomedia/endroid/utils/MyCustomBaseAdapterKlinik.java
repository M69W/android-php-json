package com.tresnomedia.endroid.utils;

import java.util.ArrayList;

import com.tresnomedia.endroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomBaseAdapterKlinik extends BaseAdapter {
	private static ArrayList<SearchResults> searchArrayList;
	private Integer[] imgid = {
			R.drawable.sd, //7
			R.drawable.smp, //8
			R.drawable.sma //9
			};
	
	private LayoutInflater mInflater;
	
	public MyCustomBaseAdapterKlinik(Context context, ArrayList<SearchResults> results) {
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return searchArrayList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_row, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.title);
			holder.imgPhoto = (ImageView) convertView.findViewById(R.id.list_image);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtName.setText(searchArrayList.get(position).getName());
		holder.imgPhoto.setImageResource(imgid[searchArrayList.get(position).getImageNumber() - 1]);
		
		return convertView;
	}
		
	static class ViewHolder {
		TextView txtName;
		ImageView imgPhoto;
	}	
}
