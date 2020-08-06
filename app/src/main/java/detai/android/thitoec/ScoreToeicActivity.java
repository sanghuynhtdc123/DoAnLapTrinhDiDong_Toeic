package detai.android.thitoec;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import detai.android.Adapter.UserInfoAdapter;
import detai.android.CommonUtils.UserInfo;
import detai.android.DataAccess.KeysListUserInfo;
import detai.android.DataAccess.ThiTracNghiemDbContext;

public class ScoreToeicActivity extends AppCompatActivity {

    ListView lvUserInfo;
    UserInfoAdapter userInfoAdapter;
    ArrayList<UserInfo> arrayUserInfo;
    Button btnBack;
    int currentPosition;
    ThiTracNghiemDbContext db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_toeic);
        addControls();
        addEvents();
        InitDisplay();
    }

    private void InitDisplay() {

        arrayUserInfo = new ArrayList<>();
        db = new KeysListUserInfo(this);
        for (Object obj: db.GetAll()) {
            arrayUserInfo.add((UserInfo)obj);
        }
        userInfoAdapter = new UserInfoAdapter(ScoreToeicActivity.this,
                                                R.layout.useritem,
                                                arrayUserInfo);
        lvUserInfo.setAdapter(userInfoAdapter);
    }

    private void addControls() {

        lvUserInfo = (ListView)findViewById(R.id.lvUserInfo);
        btnBack = (Button) findViewById(R.id.btnBack);

    }

    private void addEvents() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvUserInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                currentPosition = i;

                return false;
            }
        });

        registerForContextMenu(lvUserInfo);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenucoregame, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.mnDelete)
        {
            UserInfo userInfo = arrayUserInfo.get(currentPosition);
            if(userInfo != null)
            {
                arrayUserInfo.remove(userInfo);
                db.Delete(userInfo.getId(),false);
                userInfoAdapter.notifyDataSetChanged();
            }

        }

        return super.onContextItemSelected(item);
    }
}
