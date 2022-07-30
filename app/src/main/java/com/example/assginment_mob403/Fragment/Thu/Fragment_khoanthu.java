package com.example.assginment_mob403.Fragment.Thu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.R;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;


public class Fragment_khoanthu extends Fragment {
    ListView listView;
    SearchView searchView;
    FloatingActionButton fabAddKhoanthu;
    Button btnAdd, btnCancel;
    TextInputLayout edlName, edlMoney, edlNote, edlDateAdd;
    TextInputEditText edName, edMoney, edNote, edDateAdd;
    Spinner spinnerKhoanthu;
    private Utilities utilities;
    private int dataId_user, mDate, mMonth, mYear;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoanthu, container, false);

        initGetArguments();

        initViewByID(view);

        init();

        fabAddClickListener();

        clickListenerSearchView();

        return view;
    }

    private void initViewByID(View view) {
        listView = view.findViewById(R.id.list_view);
        searchView = view.findViewById(R.id.search);
        fabAddKhoanthu = view.findViewById(R.id.fab_add);
    }

    private void clickListenerSearchView() {
        searchView.setQueryHint("Tìm kiếm theo tên khoản thu");
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
        }

    }

    private void init() {
        utilities = new Utilities();
    }

    private void fabAddClickListener() {
        fabAddKhoanthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_khoanthu);
                dialog.show();

                btnAdd = dialog.findViewById(R.id.btn_add);
                btnCancel = dialog.findViewById(R.id.btn_cancel);
                edlName = dialog.findViewById(R.id.edl_name_khoanthu);
                edlMoney = dialog.findViewById(R.id.edl_money_khoanthu);
                edlNote = dialog.findViewById(R.id.edl_note_khoanthu);
                edlDateAdd = dialog.findViewById(R.id.edl_date_khoanthu);
                edName = dialog.findViewById(R.id.ed_name_khoanthu);
                edMoney = dialog.findViewById(R.id.ed_money_khoanthu);
                edNote = dialog.findViewById(R.id.ed_note_khoanthu);
                edDateAdd = dialog.findViewById(R.id.ed_date_khoanthu);
                spinnerKhoanthu = dialog.findViewById(R.id.spinner_khoanthu);

                removeErrorTextChange();
                edlDateAddDatePickerClickListener();
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

    private void edlDateAddDatePickerClickListener() {
        edDateAdd.setClickable(true);
        edDateAdd.setFocusable(false);
        edDateAdd.setInputType(InputType.TYPE_NULL);
        edDateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mDate = calendar.get(Calendar.DATE);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        edDateAdd.setText(date + "/" + month + "/" + year);
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });
    }

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlName, edName);
        utilities.removeErrorText(edlMoney, edMoney);
        utilities.removeErrorText(edlNote, edNote);
    }
}