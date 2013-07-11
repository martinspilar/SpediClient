package com.fei.prepravnispolecnost.design;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fei.prepravnispolecnost.R;
import com.fei.prepravnispolecnost.model.OrderDTO;
import com.fei.prepravnispolecnost.model.OrderStatus;
import com.fei.prepravnispolecnost.model.TrackDTO;
import com.fei.prepravnispolecnost.model.TrackType;

public class OrdersArrayAdapter extends ArrayAdapter<OrderDTO> {

	private final Context context;
	private final TrackDTO track;

	public OrdersArrayAdapter(Context context, TrackDTO track) {
		super(context, R.layout.row_layout, track.getOrderses());
		this.track = track;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		
		List<OrderDTO> orders = track.getOrderses();
		OrderDTO order = orders.get(position);
		
		int trackType = track.getTrackType().getId();
		int orderStatus = order.getOrderStatus().getId();
		
		switch(trackType){
		case TrackType.GATHER:
			textView.setText(order.getSenderString());
			if(orderStatus==OrderStatus.ON_ROUTE){
				imageView.setImageResource(R.drawable.no);
			}else{
				imageView.setImageResource(R.drawable.ok);
			}
			break;
		case TrackType.DELIVER:
			textView.setText(order.getReceiverString());
			if(orderStatus==OrderStatus.DELIVERED){
				imageView.setImageResource(R.drawable.ok);
			}else{
				imageView.setImageResource(R.drawable.no);
			}
			break;
		case TrackType.MOVE:
			textView.setText(order.getDepoEndString());
			if(orderStatus==OrderStatus.AT_DEPO){
				imageView.setImageResource(R.drawable.no);
			}else{
				imageView.setImageResource(R.drawable.ok);
			}
			break;
		}
				
		return rowView;
	}
}
