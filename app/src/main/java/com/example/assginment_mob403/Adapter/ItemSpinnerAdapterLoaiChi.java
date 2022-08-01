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

public class ItemSpinnerAdapterLoaiChi extends BaseAdapter {
    private List<LoaiChi> loaiChiList;
    private Context context;

    public ItemSpinnerAdapterLoaiChi(Context context) {
        this.context = context;
    }

    public void setLoaiChiList(List<LoaiChi> loaiChiList) {
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
            return null;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner, viewGroup, false);
            viewHolder.tvName = view.findViewById(R.id.tv_name_loaichi);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiChi loaiChi = loaiChiList.get(i);
        viewHolder.tvName.setText(loaiChi.getName_loaichi());
        return view;
    }

    public class ViewHolder {
        TextView tvName;
    }
}
