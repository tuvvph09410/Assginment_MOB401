package com.example.assginment_mob403.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.assginment_mob403.Activity.MainActivityThuChi;
import com.example.assginment_mob403.Fragment.Chi.Fragment_detail_loaichi;
import com.example.assginment_mob403.InterfaceListener.ItemClickListener;
import com.example.assginment_mob403.Model.LoaiChi;
import com.example.assginment_mob403.R;

import java.util.List;

public class ItemListViewAdapterLoaiChi extends BaseAdapter {
    private Context context;
    private List<LoaiChi> loaiChiList;

    public static ItemClickListener itemDeleteClickListener;

    public ItemListViewAdapterLoaiChi(Context context) {
        this.context = context;

    }

    public void setOnItemDeleteClickListener(ItemClickListener itemDeleteClickListener) {
        ItemListViewAdapterLoaiChi.itemDeleteClickListener = itemDeleteClickListener;
    }

    public void setList(List<LoaiChi> loaiChiList) {
        this.loaiChiList = loaiChiList;
        notifyDataSetChanged();
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
            viewHolder.iBtnDelete = view.findViewById(R.id.iBtn_delete);
            viewHolder.lItemLoaichi = view.findViewById(R.id.l_loaichi);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiChi loaiChi = loaiChiList.get(i);
        viewHolder.tvName.setText(loaiChi.getName_loaichi().toString());
        viewHolder.iBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemDeleteClickListener != null) {
                    itemDeleteClickListener.onItemClick(loaiChi.getId_loaichi());
                }
            }
        });
        viewHolder.lItemLoaichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment_detail_loaichi fragment_detail_loaichi = new Fragment_detail_loaichi();
                bundle.putInt("bid_loaichi", loaiChi.getId_loaichi());
                bundle.putInt("bid_user", loaiChi.getId_user());
                bundle.putString("bname_loaichi", loaiChi.getName_loaichi());
                fragment_detail_loaichi.setArguments(bundle);
                ((MainActivityThuChi) context).loadFragment(fragment_detail_loaichi, "Loại chi chi tiết");
            }
        });
        return view;
    }

    public class ViewHolder {
        TextView tvName;
        ConstraintLayout lItemLoaichi;
        ImageButton iBtnDelete;
    }
}
