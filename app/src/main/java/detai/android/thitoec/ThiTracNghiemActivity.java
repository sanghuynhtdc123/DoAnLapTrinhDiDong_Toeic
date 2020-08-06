package detai.android.thitoec;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import detai.android.CommonUtils.Constants;
import detai.android.CommonUtils.Question;
import detai.android.DataAccess.KeysListQuestion;
import detai.android.DataAccess.ThiTracNghiemDbContext;

public class ThiTracNghiemActivity extends AppCompatActivity {


    Button btnTruoc;
    Button btnSau;
    Button btnKetThuc;

    TextView tvThoiGian;
    TextView tvSoCau;
    TextView tvCauHoi;
    TextView tvDapAnA;
    TextView tvDapAnB;
    TextView tvDapAnC;
    TextView tvDapAnD;
    LinearLayout layoutA;
    LinearLayout layoutB;
    LinearLayout layoutC;
    LinearLayout layoutD;

    int hh;
    int mm;
    int ss;
    int time_interval;
    int bg_dapan;
    int bg_dapan_chon;
    int soCauToiDa;
    int soCau;
    ArrayList<Question> dsCauHoi;
    ThiTracNghiemDbContext db;
    Question cauHoi;

    View.OnClickListener chonDapAn;
    View.OnClickListener xuLyCauHoi;
    View.OnClickListener ketThucPhanThi;

    CountDownTimer thoiGian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thi_trac_nghiem);

        addControls();
        addEvents();
        khoiTaoGiaTriBanDau();
    }

    private void formatThoiGian() {

        ss--;
        if (ss < 0) {
            ss = 59;
            mm--;
        }

        if (mm < 0) {
            hh--;
            mm = 59;
        }

        tvThoiGian.setText(
                (hh < 10 ? "0" + hh : hh) + ":" +
                (mm < 10 ? "0" + mm : mm) + ":" +
                (ss < 10 ? "0" + ss : ss));
    }

    private void addEvents() {

        hh = 01;
        mm = 00;
        ss = 00;
        time_interval =((hh * 60) + mm) * 60 * 1000 + 2000;
        thoiGian = new CountDownTimer(time_interval, 1000) {
            @Override
            public void onTick(long l) {

                formatThoiGian();
            }

            @Override
            public void onFinish() {
                tinhKetQuaPhanThi();
            }
        };

        thoiGian.start();

        chonDapAn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int daDaChon = 0;

                if(view.getId() == R.id.tvDapAnA) {
                    daDaChon = 1;

                } else if(view.getId() == R.id.tvDapAnB) {
                    daDaChon = 2;

                } else if(view.getId() == R.id.tvDapAnC) {
                    daDaChon = 3;

                } else if(view.getId() == R.id.tvDapAnD) {
                    daDaChon = 4;
                }

                cauHoi.setResultSelect(daDaChon);
                layBackground(daDaChon);
            }
        };

        xuLyCauHoi = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.btnTruoc) {
                    cauHoiTruocDo();

                } else if (view.getId() == R.id.btnSau) {
                    cauHoiTiepTheo();
                }
            }
        };

        ketThucPhanThi = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.btnKetThuc) {
                    KetThucPhanThi();
                }
            }
        };

        tvDapAnA.setOnClickListener(chonDapAn);
        tvDapAnB.setOnClickListener(chonDapAn);
        tvDapAnC.setOnClickListener(chonDapAn);
        tvDapAnD.setOnClickListener(chonDapAn);

        btnTruoc.setOnClickListener(xuLyCauHoi);
        btnSau.setOnClickListener(xuLyCauHoi);

        btnKetThuc.setOnClickListener(ketThucPhanThi);
    }

    private void addControls() {

        btnTruoc = (Button) findViewById(R.id.btnTruoc);
        btnSau = (Button) findViewById(R.id.btnSau);
        btnKetThuc = (Button) findViewById(R.id.btnKetThuc);

        tvThoiGian = (TextView) findViewById(R.id.tvThoiGian);
        tvSoCau = (TextView)findViewById(R.id.tvSoCau);
        tvCauHoi = (TextView)findViewById(R.id.tvCauHoi);
        tvDapAnA = (TextView)findViewById(R.id.tvDapAnA);
        tvDapAnB = (TextView)findViewById(R.id.tvDapAnB);
        tvDapAnC = (TextView)findViewById(R.id.tvDapAnC);
        tvDapAnD = (TextView)findViewById(R.id.tvDapAnD);

        layoutA = (LinearLayout) findViewById(R.id.layoutA);
        layoutB = (LinearLayout) findViewById(R.id.layoutB);
        layoutC = (LinearLayout) findViewById(R.id.layoutC);
        layoutD = (LinearLayout) findViewById(R.id.layoutD);

    }

    private void khoiTaoGiaTriBanDau() {

        soCauToiDa = 100;
        soCau = 0;
        bg_dapan = R.drawable.bgdapan;
        bg_dapan_chon = R.drawable.bgdapanchon;
        db = new KeysListQuestion(ThiTracNghiemActivity.this);

        khoiTaoCauHoi();
    }

    private void khoiTaoCauHoi() {

        dsCauHoi = new ArrayList<>();
        int n = 1;
        for (Object ob : db.GetAll()) {

            if (n > soCauToiDa) {
                break;
            }

            Question question = (Question)ob;

            dsCauHoi.add(question);
            n++;
        }

        if(dsCauHoi.size() < soCauToiDa)
        {
            Toast.makeText(
                    ThiTracNghiemActivity.this,
                    "Không đủ số lương câu hỏi để bắt đầu phần thi!"
                    ,Toast.LENGTH_LONG).show();

            finish();
        }

        cauHoiTiepTheo();
    }

    private void layCauHoi() {

        // Nếu kiểm tra số câu hỏi là không hợp lệ thì không xử lý
        if (!kiemTraHopLeCauHoi()) {
            return;
        }

        tvSoCau.setText(dinhDangSoCau());

        cauHoi = dsCauHoi.get(soCau - 1);
        if(cauHoi == null) {
            return;
        }

        tvCauHoi.setText("Câu hỏi: " + cauHoi.getQuestion());
        tvDapAnA.setText("(A). " + cauHoi.getResult1());
        tvDapAnB.setText("(B). " + cauHoi.getResult2());
        tvDapAnC.setText("(C). " + cauHoi.getResult3());
        tvDapAnD.setText("(D). " + cauHoi.getResult4());

        // lay background dap an chon truoc do
        layBackground(cauHoi.getResultSelect());
    }

    private void layBackground(int dapAn) {

        layoutA.setBackgroundResource(bg_dapan);
        layoutB.setBackgroundResource(bg_dapan);
        layoutC.setBackgroundResource(bg_dapan);
        layoutD.setBackgroundResource(bg_dapan);

        if (dapAn == 1) {
            layoutA.setBackgroundResource(bg_dapan_chon);
        } else if (dapAn == 2) {
            layoutB.setBackgroundResource(bg_dapan_chon);
        } else if (dapAn == 3) {
            layoutC.setBackgroundResource(bg_dapan_chon);
        } else if (dapAn == 4) {
            layoutD.setBackgroundResource(bg_dapan_chon);
        }
    }

    private String dinhDangSoCau() {
        return  soCau + "/" + soCauToiDa;
    }

    private boolean kiemTraHopLeCauHoi() {

        if (soCau < 1) {
            soCau = 1;
            return false;
        }

        if (soCau > soCauToiDa) {
            soCau = soCauToiDa;
            return false;
        }

        return true;
    }

    private boolean kiemTraHopLeKhiKetThuc() {

        for (Question cauHoi: dsCauHoi) {
            if (cauHoi.getResultSelect() <= 0) {
                return false;
            }
        }

        return true;
    }

    private void cauHoiTiepTheo () {

        soCau++;

        layCauHoi();
    }

    private void cauHoiTruocDo () {

        soCau--;

        layCauHoi();
    }

    private void KetThucPhanThi() {

        boolean hopLe = kiemTraHopLeKhiKetThuc();

        String contentMsg = hopLe ? "Bạn có chắc chắn muốn kết thúc phần thi của mình không?" :
                "Vẫn còn một số câu chưa chọn. \nBạn có chắc chắn muốn kết thúc phần thi của mình không?";

        AlertDialog.Builder message = new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage(contentMsg)
                .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tinhKetQuaPhanThi();
                    }
                })
                .setPositiveButton("Thi tiếp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        message.show();

    }

    private void tinhKetQuaPhanThi() {

        int soCauDung = 0;
        double diemSo = 0;

        for (Question cauHoi: dsCauHoi ) {

            if (cauHoi.getResultSelect() == cauHoi.getResult()) {
                soCauDung++;
            }
        }

        diemSo = (double) (soCauDung * 10)/soCauToiDa;

        Intent intent = new Intent(ThiTracNghiemActivity.this,ConfirmActivity.class);
        intent.putExtra(Constants.SO_CAU_DUNG , (soCauDung + "/" + soCauToiDa));
        intent.putExtra(Constants.SO_DIEM_DAT_DUOC , diemSo);

        startActivity(intent);

        finish();
    }
}
