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
import com.example.assginment_mob403.InterfaceAPI.LoaiChiAPI;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerKhoanThu;
import com.example.assginment_mob403.Model.KhoanThu;
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

public class ItemListViewAdapterKhoanThu extends BaseAdapter {
    List<KhoanThu> KhoanThuList;
    Context context;
    private String name = "";
    public static ItemClickListenerKhoanThu itemDeleteClickListener;

    public ItemListViewAdapterKhoanThu(Context context) {
        this.context = context;
    }

    public void setOnItemDeleteClickListener(ItemClickListenerKhoanThu itemDeleteClickListener) {
        ItemListViewAdapterKhoanThu.itemDeleteClickListener = itemDeleteClickListener;
    }

    public void setKhoanThuList(List<KhoanThu> KhoanThuList) {
        this.KhoanThuList = KhoanThuList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (KhoanThuList.isEmpty()) {
            return 0;
        }
        return KhoanThuList.size();
    }

    @Override
    public Object getItem(int i) {
        if (KhoanThuList.isEmpty()) {
            return null;
        }
        return KhoanThuList.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_khoanthu, viewGroup, false);
            viewHolder.tvName = view.findViewById(R.id.tv_name);
            viewHolder.tvNameLoaiChi = view.findViewById(R.id.tv_name_loaichi);
            viewHolder.tvMoney = view.findViewById(R.id.tv_money);
            viewHolder.tvNote = view.findViewById(R.id.tv_note);
            viewHolder.tvDateAdd = view.findViewById(R.id.tv_date_add);
            viewHolder.iBtnDelete = view.findViewById(R.id.iBtn_delete);
            viewHolder.l_KhoanThu = view.findViewById(R.id.l_khoanthu);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhoanThu KhoanThu = KhoanThuList.get(i);
        viewHolder.tvName.setText(KhoanThu.getName_khoanthu());
        viewHolder.tvMoney.setText(String.valueOf(KhoanThu.getMoney_khoanthu()));
        viewHolder.tvNote.setText(KhoanThu.getNote_khoanthu());
        viewHolder.tvDateAdd.setText(KhoanThu.getDate_add_khoanthu());
        getNameLoaiChi(KhoanThu.getId_user(), KhoanThu.getId_khoanthu(), viewHolder.tvNameLoaiChi);
        viewHolder.iBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemDeleteClickListener != null) {
                    itemDeleteClickListener.onItemClick(KhoanThu.getId_khoanthu());
                }
            }
        });
        viewHolder.l_KhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                Fragment_detail_KhoanThu fragment_detail_KhoanThu = new Fragment_detail_KhoanThu();
//                bundle.putInt("bId_KhoanThu", KhoanThu.getId_KhoanThu());
//                bundle.putInt("bId_user", KhoanThu.getId_user());
//                bundle.putString("bName", KhoanThu.getName_KhoanThu());
//                bundle.putInt("bMoney", KhoanThu.getMoney_KhoanThu());
//                bundle.putString("bNote", KhoanThu.getNote_KhoanThu());
//                bundle.putString("bDateAdd", KhoanThu.getDate_add_KhoanThu());
//                bundle.putString("bNameLoaiChi", name);
//                fragment_detail_KhoanThu.setArguments(bundle);
//                ((MainActivityThuChi) context).loadFragment(fragment_detail_KhoanThu, "Khoản chi chi tiết");
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
        ConstraintLayout l_KhoanThu;
    }
}
