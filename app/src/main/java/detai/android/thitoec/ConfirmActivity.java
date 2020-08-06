package detai.android.thitoec;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import detai.android.CommonUtils.Commons;
import detai.android.CommonUtils.Constants;
import detai.android.CommonUtils.UserInfo;
import detai.android.DataAccess.KeysListUserInfo;
import detai.android.DataAccess.ThiTracNghiemDbContext;

public class ConfirmActivity extends AppCompatActivity {


    EditText etTenThiSinh;
    EditText etSoCauDung;
    EditText etSoDiemDatDuoc;
    Button btnSave;
    Button btnBack;
    ThiTracNghiemDbContext db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        addControls();
        addEvents();
        InitDisplay();
    }

    private void InitDisplay() {
        db = new KeysListUserInfo(this);
        Intent intent = getIntent();

        String soCauDung = intent.getStringExtra(Constants.SO_CAU_DUNG);
        double soDiemDatDuoc = intent.getDoubleExtra(Constants.SO_DIEM_DAT_DUOC,0);

        etTenThiSinh.setText(Commons.tenThiSinh);
        etSoCauDung.setText(soCauDung);
        etSoDiemDatDuoc.setText(soDiemDatDuoc + "");
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserInfo user = new UserInfo();
                user.setTenThiSinh(etTenThiSinh.getText().toString());
                user.setSoDiemDatDuoc(etSoCauDung.getText().toString());
                user.setSoDiemDatDuoc(etSoDiemDatDuoc.getText().toString());

                db.Insert(user);

                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {

        etTenThiSinh = (EditText) findViewById(R.id.etTenThiSinh);
        etSoCauDung = (EditText) findViewById(R.id.etSoCauDung);
        etSoDiemDatDuoc = (EditText) findViewById(R.id.etSoDiemDatDuoc);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnBack = (Button) findViewById(R.id.btnBack);

    }
}
