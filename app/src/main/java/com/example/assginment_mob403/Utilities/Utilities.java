package com.example.assginment_mob403.Utilities;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Utilities {
    public String NotSpecialCharacter = "Vui lòng không nhập kí tự đặc biệt";
    public String PhoneInvalid = "Số điện thoại phải là số";
    public String PhoneLength = "Số điện thoại phải bao gồm từ 9 số đến 12 số";
    public String PhoneRequire = "Vui lòng nhập số điện thoại";
    public String PhoneType = "Sai định dạng của số điện thoại";
    public String FirstNameLength = "Họ có độ dài từ 2 đến 20 ký tự";
    public String FirstNameRequire = "Vui lòng nhập họ";
    public String LastNameLength = "Tên phải từ 2 đến 20 ký tự";
    public String LastNameRequire = "Vui lòng nhập tên";
    public String PasswordLength = "Mật khẩu phải từ 4 đến 20 ký tự";
    public String PasswordRequire = "Vui lòng nhập mật khẩu";
    public String PasswordCompare = "Mật khẩu và mật khẩu nhập lại khác nhau";
    public String RePasswordCompare = "Mật khẩu mới nhập lại không trùng nhau";
    public String PasswordOldNotMatchPasswordNew = "Mật khẩu cũ và mật khẩu mới không thể trùng nhau";
    public String ConfirmPasswordRequire = "Vui lòng nhập xác nhận mật khẩu";
    public String EmailInvalid = "Sai định dạng email";
    public String EmailRequire = "Vui lòng nhập email";
    public String LoaiChiRequire = "Vui lòng nhập loại chi";
    public String LoaiChiLength = "Tên loại chi có độ dài từ 2 đến 20 ký tự";
    public String KhoanchiNameRequire = "Vui lòng nhập tên khoản chi";
    public String KhoanchiSpinnerSelectRequire = "Vui lòng chọn loại chi";
    public String KhoanchiNameLength = "Tên khoản chi có độ dài từ 2 đến 20 ký tự";
    public String KhoanchiMoneyRequire = "Vui lòng nhập số tiền khoản chi";
    public String KhoanchiMoneyInvalid = "Số khoản chi tiền phải là số";
    public String KhoanchiSelectDate = "Vui lòng chọn ngày";
    public String LoaiThuRequire = "Vui lòng nhập loại thu";
    public String LoaiThuLength = "Tên loại thu có độ dài từ 2 đến 20 ký tự";
    public String KhoanThuNameRequire = "Vui lòng nhập tên khoản thu";
    public String KhoanThuSpinnerSelectRequire = "Vui lòng chọn loại thu";
    public String KhoanThuNameLength = "Tên khoản thu có độ dài từ 2 đến 20 ký tự";
    public String KhoanThuMoneyRequire = "Vui lòng nhập số tiền khoản chi";
    public String KhoanThuMoneyInvalid = "Số khoản thu tiền phải là số";
    public String KhoanThuSelectDate = "Vui lòng chọn ngày";

    public Pattern NSCPattern = Pattern.compile("[A-Za-z0-9AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđ ]+");


    public void removeErrorText(TextInputLayout til, TextInputEditText tit) {
        tit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til.setError("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
