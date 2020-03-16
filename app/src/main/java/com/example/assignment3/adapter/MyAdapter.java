package com.example.assignment3.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment3.R;
import com.example.assignment3.record.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: LONG, QINGSHENG
 * @ID: 16387388
 * The adapter of the ListView
 */
public class MyAdapter extends BaseAdapter {

    private List<Record> recordList;
    private Context context;
    private int layoutId;
    private LayoutInflater inflater;

    class ViewHolder{
        TextView titleView;
        TextView bodyView;
        TextView timeView;
    }

    public MyAdapter(Context context, List<Record> recordList, int layoutId) {
        this.recordList = recordList;
        this.context = context;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        this.recordList.remove(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.record_item, viewGroup, false);
            holder  = new ViewHolder();
            holder.titleView = convertView.findViewById(R.id.list_item_title);
            holder.bodyView = convertView.findViewById(R.id.list_item_body);
            holder.timeView = convertView.findViewById(R.id.list_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Record record = recordList.get(position);
        String tile = record.getTitleName();
        holder.titleView.setText((position+1)+"."+(tile.length()>7?tile.substring(0,7)+"...":tile));
        String content = record.getTextBody();
        holder.bodyView.setText(content.length()>13?content.substring(0,12)+"...":content);
        String createTime = record.getCreateTime();
        holder.timeView.setText(createTime);
        return convertView;
    }
}
