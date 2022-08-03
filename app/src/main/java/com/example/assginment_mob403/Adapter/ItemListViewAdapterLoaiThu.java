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
import com.example.assginment_mob403.Fragment.Thu.Fragment_detail_loaithu;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerLoaiThu;
import com.example.assginment_mob403.Model.LoaiThu;
import com.example.assginment_mob403.R;

import java.util.List;

public class ItemListViewAdapterLoaiThu extends BaseAdapter {
    private Context context;
    private List<LoaiThu> LoaiThuList;

    public static ItemClickListenerLoaiThu itemDeleteClickListener;

    public ItemListViewAdapterLoaiThu(Context context) {
        this.context = context;

    }

    public void setOnItemDeleteClickListener(ItemClickListenerLoaiThu itemDeleteClickListener) {
        ItemListViewAdapterLoaiThu.itemDeleteClickListener = itemDeleteClickListener;
    }

    public void setList(List<LoaiThu> LoaiThuList) {
        this.LoaiThuList = LoaiThuList;
        notifyDataSetChanged();
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
            return 0;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_loaithu, viewGroup, false);
            viewHolder.tvName = view.findViewById(R.id.tv_name);
            viewHolder.iBtnDelete = view.findViewById(R.id.iBtn_delete);
            viewHolder.lItemLoaiThu = view.findViewById(R.id.l_loaithu);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiThu LoaiThu = LoaiThuList.get(i);
        viewHolder.tvName.setText(LoaiThu.getName_loaithu().toString());
        viewHolder.iBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemDeleteClickListener != null) {
                    itemDeleteClickListener.onItemClick(LoaiThu.getId_loaithu());
                }
            }
        });
        viewHolder.lItemLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment_detail_loaithu fragment_detail_LoaiThu = new Fragment_detail_loaithu();
                bundle.putInt("bid_LoaiThu", LoaiThu.getId_loaithu());
                bundle.putInt("bid_user", LoaiThu.getId_user());
                bundle.putString("bname_LoaiThu", LoaiThu.getName_loaithu());
                fragment_detail_LoaiThu.setArguments(bundle);
                ((MainActivityThuChi) context).loadFragment(fragment_detail_LoaiThu, "Loại thu chi tiết");
            }
        });
        return view;
    }

    public class ViewHolder {
        TextView tvName;
        ConstraintLayout lItemLoaiThu;
        ImageButton iBtnDelete;
    }
}
