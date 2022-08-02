package com.example.assginment_mob403.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.assginment_mob403.Model.KhoanChi;
import com.example.assginment_mob403.R;

import java.util.List;

public class ItemListViewAdapterKhoanChi extends BaseAdapter {
    List<KhoanChi> khoanChiList;
    Context context;

    public ItemListViewAdapterKhoanChi(Context context) {
        this.context = context;
    }

    public void setKhoanChiList(List<KhoanChi> khoanChiList) {
        this.khoanChiList = khoanChiList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (khoanChiList.isEmpty()) {
            return 0;
        }
        return khoanChiList.size();
    }

    @Override
    public Object getItem(int i) {
        if (khoanChiList.isEmpty()) {
            return null;
        }
        return khoanChiList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_khoanchi, viewGroup, false);
            viewHolder.tvName = view.findViewById(R.id.tv_name);
            viewHolder.tvNameLoaiChi = view.findViewById(R.id.tv_name_loaichi);
            viewHolder.tvMoney = view.findViewById(R.id.tv_money);
            viewHolder.tvNote = view.findViewById(R.id.tv_note);
            viewHolder.tvDateAdd = view.findViewById(R.id.tv_date_add);
            viewHolder.iBtnDelete = view.findViewById(R.id.iBtn_delete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhoanChi khoanChi = khoanChiList.get(i);
        viewHolder.tvName.setText(khoanChi.getName_khoanchi());
        viewHolder.tvNameLoaiChi.setText(String.valueOf(khoanChi.getId_loaichi()));
        viewHolder.tvMoney.setText(String.valueOf(khoanChi.getMoney_khoanchi()));
        viewHolder.tvNote.setText(khoanChi.getNote_khoanchi());
        viewHolder.tvDateAdd.setText(khoanChi.getDate_add_khoanchi());
        viewHolder.iBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    public class ViewHolder {
        TextView tvName, tvNameLoaiChi, tvMoney, tvNote, tvDateAdd;
        ImageButton iBtnDelete;
    }
}
