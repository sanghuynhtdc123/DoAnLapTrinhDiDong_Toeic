package detai.android.thitoec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import detai.android.CommonUtils.Commons;

public class TracNghiemToeic extends AppCompatActivity {

    Button btnStart;
    Button btnRule;
    Button btnCore;
    Button btnExit;

    EditText etTenThiSinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracnghiem_toeic_layout);

        try {
            addControls();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addEvents();
    }

    private void addEvents() {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Commons.tenThiSinh = etTenThiSinh.getText().toString();

                if (Commons.tenThiSinh.trim().isEmpty()) {
                    Toast.makeText(TracNghiemToeic.this,
                            "Vui lòng nhập tên thí sinh để bắt đầu phần thi",
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                Intent intent = new Intent(TracNghiemToeic.this, ThiTracNghiemActivity.class);
                startActivity(intent);
            }
        });

        btnRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TracNghiemToeic.this, RuleToeicActivity.class);
                startActivity(intent);
            }
        });

        btnCore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TracNghiemToeic.this, ScoreToeicActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() throws IOException {

        btnStart = (Button) findViewById(R.id.btnStart);
        btnRule = (Button) findViewById(R.id.btnRule);
        btnCore = (Button) findViewById(R.id.btnCore);
        btnExit = (Button) findViewById(R.id.btnExit);

        etTenThiSinh = (EditText) findViewById(R.id.teTenThiSinh);

        Commons.CopyDatabase(this);
    }
}
