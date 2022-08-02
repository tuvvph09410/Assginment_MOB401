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
import com.example.assginment_mob403.Fragment.Chi.Fragment_detail_khoanchi;
import com.example.assginment_mob403.InterfaceAPI.LoaiChiAPI;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerKhoanChi;
import com.example.assginment_mob403.Model.KhoanChi;
import com.example.assginment_mob403.Model.LoaiChi;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseSelectLoaiChi;
import com.example.assginment_mob403.URLServer.PathURLServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemListViewAdapterKhoanChi extends BaseAdapter {
    List<KhoanChi> khoanChiList;
    Context context;
    private String name = "";
    public static ItemClickListenerKhoanChi itemDeleteClickListener;

    public ItemListViewAdapterKhoanChi(Context context) {
        this.context = context;
    }

    public void setOnItemDeleteClickListener(ItemClickListenerKhoanChi itemDeleteClickListener) {
        ItemListViewAdapterKhoanChi.itemDeleteClickListener = itemDeleteClickListener;
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
            viewHolder.l_Khoanchi = view.findViewById(R.id.l_khoanchi);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhoanChi khoanChi = khoanChiList.get(i);
        viewHolder.tvName.setText(khoanChi.getName_khoanchi());
        viewHolder.tvMoney.setText(String.valueOf(khoanChi.getMoney_khoanchi()));
        viewHolder.tvNote.setText(khoanChi.getNote_khoanchi());
        viewHolder.tvDateAdd.setText(khoanChi.getDate_add_khoanchi());
        getNameLoaiChi(khoanChi.getId_user(), khoanChi.getId_loaichi(), viewHolder.tvNameLoaiChi);
        viewHolder.iBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemDeleteClickListener != null) {
                    itemDeleteClickListener.onItemClick(khoanChi.getId_khoanchi());
                }
            }
        });
        viewHolder.l_Khoanchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment_detail_khoanchi fragment_detail_khoanchi = new Fragment_detail_khoanchi();
                bundle.putInt("bId_khoanchi", khoanChi.getId_khoanchi());
                bundle.putInt("bId_user", khoanChi.getId_user());
                bundle.putString("bName", khoanChi.getName_khoanchi());
                bundle.putInt("bMoney", khoanChi.getMoney_khoanchi());
                bundle.putString("bNote", khoanChi.getNote_khoanchi());
                bundle.putString("bDateAdd", khoanChi.getDate_add_khoanchi());
                bundle.putString("bNameLoaiChi", name);
                fragment_detail_khoanchi.setArguments(bundle);
                ((MainActivityThuChi) context).loadFragment(fragment_detail_khoanchi, "Khoản chi chi tiết");
            }
        });
        return view;
    }


    private void getNameLoaiChi(int id_user, int id_loaichi, TextView tvName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiChiAPI loaiChiAPI = retrofit.create(LoaiChiAPI.class);
        Call<ServerResponseSelectLoaiChi> call = loaiChiAPI.getSelectedLoaiChiByIdUserAndIdLoaiChi(id_user, id_loaichi);
        call.enqueue(new Callback<ServerResponseSelectLoaiChi>() {
            @Override
            public void onResponse(Call<ServerResponseSelectLoaiChi> call, Response<ServerResponseSelectLoaiChi> response) {
                ServerResponseSelectLoaiChi responseSelectLoaiChi = response.body();
                List<LoaiChi> loaiChiList = new ArrayList<>(Arrays.asList(responseSelectLoaiChi.getLoaichi()));
                for (int i = 0; i < loaiChiList.size(); i++) {
                    LoaiChi loaiChi = loaiChiList.get(i);
                    name = loaiChi.getName_loaichi();
                }
                tvName.setText(name);
            }

            @Override
            public void onFailure(Call<ServerResponseSelectLoaiChi> call, Throwable t) {

            }
        });
    }

    public class ViewHolder {
        TextView tvName, tvNameLoaiChi, tvMoney, tvNote, tvDateAdd;
        ImageButton iBtnDelete;
        ConstraintLayout l_Khoanchi;
    }
}
