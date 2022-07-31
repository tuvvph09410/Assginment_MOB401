package com.example.assginment_mob403.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assginment_mob403.Model.LoaiChi;
import com.example.assginment_mob403.R;

import java.util.List;

public class ItemListViewAdapterLoaiChi extends BaseAdapter {
    private Context context;
    private List<LoaiChi> loaiChiList;

    public ItemListViewAdapterLoaiChi(Context context, List<LoaiChi> loaiChiList) {
        this.context = context;
        this.loaiChiList = loaiChiList;
    }

    @Override
    public int getCount() {
        if (loaiChiList.isEmpty()) {
            return 0;
        }
        return loaiChiList.size();
    }

    @Override
    public Object getItem(int i) {
        if (loaiChiList.isEmpty()) {
            return 0;
        }
        return loaiChiList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_loaichi, viewGroup, false);
            viewHolder.tvName = view.findViewById(R.id.tv_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiChi loaiChi = loaiChiList.get(i);
        viewHolder.tvName.setText(loaiChi.getName_loaichi().toString());
        return view;
    }

    public class ViewHolder {
        TextView tvName;
    }
}
