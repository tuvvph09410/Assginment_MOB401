package com.example.assginment_mob403.Fragment.Thu;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.R;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Fragment_loaithu extends Fragment {
    SearchView searchView;
    ListView listView;
    FloatingActionButton floatAddLoaithu;
    private int data_idUser;
    Dialog dialog;
    private Utilities utilities;
    TextInputLayout edlName;
    TextInputEditText edName;
    Button btnAdd, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loaithu, container, false);

        initGetArguments();

        initViewByID(view);

        init();

        clickListenerSearchView();

        fabAddClickListener();

        return view;
    }

    private void init() {
        utilities = new Utilities();
    }

    private void fabAddClickListener() {
        floatAddLoaithu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_loaithu);
                dialog.show();

                edlName = dialog.findViewById(R.id.edl_name_loaithu);
                edName = dialog.findViewById(R.id.ed_name_loaithu);
                btnAdd = dialog.findViewById(R.id.btn_add);
                btnCancel = dialog.findViewById(R.id.btn_cancel);

                removeErrorTextChange();
                clickListenerButtonDialog();
            }
        });
    }

    private void clickListenerButtonDialog() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlName, edName);
    }

    private void clickListenerSearchView() {
        searchView.setQueryHint("Tìm kiếm theo tên loại thu");
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            data_idUser = bundle.getInt("dataId_user");
        }
    }

    private void initViewByID(View view) {
        searchView = view.findViewById(R.id.search);
        listView = view.findViewById(R.id.list_view);
        floatAddLoaithu = view.findViewById(R.id.fab_add);
    }
}