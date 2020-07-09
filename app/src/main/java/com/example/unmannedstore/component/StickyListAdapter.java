package com.example.unmannedstore.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.unmannedstore.R;

import java.util.ArrayList;

public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

	private ArrayList<String> list;
	private Context mContext;
	private static LayoutInflater inflater=null;


	public void init(Context context, ArrayList<String> list) {
		this.list = list;
		this.mContext = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return "get()position";//list.get(position)
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

/*	@Override
	public int getViewTypeCount() {
		return 2;
	}*/

	/*@Override
	public int getItemViewType(int position) {
		if (list.get(position).hashCode() == 1) {
			return 1;
		} else {
			return 2;
		}
	}
*/


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int flag  = getItemViewType(position);
		View vi = convertView;
		if(convertView == null) {
			vi = inflater.inflate(R.layout.item_ly, null);
		}

		TextView tvDate = (TextView) vi.findViewById(R.id.bill_list_item_space_a);
		tvDate.setText("" + list.get(position));
		tvDate.setTextSize(15);
		return vi;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView == null) {
			vi = inflater.inflate(R.layout.header_ly, null);
		}

		TextView tvDate = (TextView) vi.findViewById(R.id.bill_list_balance);
		//调用后端函数代替余额
		int balance = 999;
		tvDate.setText("\n"+"\b\b\b"+balance+"￥\n");
		//get(position)
		tvDate.setTextSize(15);
		return vi;
	}


	@Override
	public long getHeaderId(int position) {
		Long id = 0l;
		if (list.get(position).contains("$$")) {
			id = Long.valueOf(position);
		}

		return id;
	}
}
