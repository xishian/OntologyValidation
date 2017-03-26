package edu.swu.ontologyvalidation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.swu.ontologyvalidation.R;
import edu.swu.ontologyvalidation.entity.CountItem;


/**
 * Created by Tuister on 2016/9/11.
 */
public class CountListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<CountItem> mItems;
	private Context mContext;

	public CountListAdapter(Context context, List<CountItem> items)
	{
		mContext = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItems = items;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		if(position>mItems.size()-1)
//		{
//			Button btnShowGraph = new Button(mContext);
//			btnShowGraph.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					//Toast.makeText(mContext,"这里将会显示一个对话框",Toast.LENGTH_SHORT).show();
//
//				}
//			});
//			btnShowGraph.setText("Show Graph");
//			return btnShowGraph;
//		}else {
			CountItem item = mItems.get(position);
			View v = mInflater.inflate(R.layout.list_item_count,parent,false);
			TextView tvLabel = (TextView)v.findViewById(R.id.tv_item_count_label);
			TextView tvInfo = (TextView)v.findViewById(R.id.tv_item_count_info);
			tvLabel.setText(item.getLabel());
			//这里原来为tvInfo.setText(item.getCountInfo());竟然没有报错
			tvInfo.setText(item.getCountInfo()+"");
			return v;
	}//end of getView
}
