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
import com.example.assginment_mob403.Fragment.Thu.Fragment_detail_khoanthu;
import com.example.assginment_mob403.InterfaceAPI.LoaiThuAPI;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerKhoanThu;
import com.example.assginment_mob403.Model.KhoanThu;
import com.example.assginment_mob403.Model.LoaiThu;
import com.example.assginment_mob403.Model.LoaiThu;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseSelectLoaiThu;
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
            viewHolder.tvNameLoaiThu = view.findViewById(R.id.tv_name_loaithu);
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
        getNameLoaiThu(KhoanThu.getId_user(), KhoanThu.getId_khoanthu(), viewHolder.tvNameLoaiThu);
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
                Fragment_detail_khoanthu fragment_detail_KhoanThu = new Fragment_detail_khoanthu();
                bundle.putInt("bId_KhoanThu", KhoanThu.getId_khoanthu());
                bundle.putInt("bId_user", KhoanThu.getId_user());
                bundle.putString("bName", KhoanThu.getName_khoanthu());
                bundle.putInt("bMoney", KhoanThu.getMoney_khoanthu());
                bundle.putString("bNote", KhoanThu.getNote_khoanthu());
                bundle.putString("bDateAdd", KhoanThu.getDate_add_khoanthu());
                bundle.putString("bNameLoaiThu", name);
                fragment_detail_KhoanThu.setArguments(bundle);
                ((MainActivityThuChi) context).loadFragment(fragment_detail_KhoanThu, "Khoản thu chi tiết");
            }
        });
        return view;
    }


    private void getNameLoaiThu(int id_user, int id_LoaiThu, TextView tvName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiThuAPI LoaiThuAPI = retrofit.create(LoaiThuAPI.class);
        Call<ServerResponseSelectLoaiThu> call = LoaiThuAPI.getSelectedLoaiThuByIdUserAndIdLoaiThu(id_user, id_LoaiThu);
        call.enqueue(new Callback<ServerResponseSelectLoaiThu>() {
            @Override
            public void onResponse(Call<ServerResponseSelectLoaiThu> call, Response<ServerResponseSelectLoaiThu> response) {
                ServerResponseSelectLoaiThu responseSelectLoaiThu = response.body();
                List<LoaiThu> LoaiThuList = new ArrayList<>(Arrays.asList(responseSelectLoaiThu.getLoaiThu()));
                for (int i = 0; i < LoaiThuList.size(); i++) {
                    LoaiThu LoaiThu = LoaiThuList.get(i);
                    name = LoaiThu.getName_loaithu();
                }
                tvName.setText(name);
            }

            @Override
            public void onFailure(Call<ServerResponseSelectLoaiThu> call, Throwable t) {

            }
        });
    }

    public class ViewHolder {
        TextView tvName, tvNameLoaiThu, tvMoney, tvNote, tvDateAdd;
        ImageButton iBtnDelete;
        ConstraintLayout l_KhoanThu;
    }
}
