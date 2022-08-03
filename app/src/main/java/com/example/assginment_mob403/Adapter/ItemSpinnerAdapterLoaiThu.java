package com.example.assginment_mob403.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assginment_mob403.Model.LoaiThu;
import com.example.assginment_mob403.R;

import java.util.List;

public class ItemSpinnerAdapterLoaiThu extends BaseAdapter {
    private List<LoaiThu> LoaiThuList;
    private Context context;

    public ItemSpinnerAdapterLoaiThu(Context context) {
        this.context = context;
    }

    public void setLoaiThuList(List<LoaiThu> LoaiThuList) {
        this.LoaiThuList = LoaiThuList;
    }

    @Override
    public int getCount() {
        if (LoaiThuList.isEmpty()) {
            return 0;
        }
        return LoaiThuList.size();
    }

    @Override
    public Object getItem(int i) {
        if (LoaiThuList.isEmpty()) {
            return null;
        }
        return LoaiThuList.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner_loaithu, viewGroup, false);
            viewHolder.tvName = view.findViewById(R.id.tv_name_loaithu);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiThu LoaiThu = LoaiThuList.get(i);
        viewHolder.tvName.setText(LoaiThu.getName_loaithu());
        return view;
    }

    public class ViewHolder {
        TextView tvName;
    }
}
