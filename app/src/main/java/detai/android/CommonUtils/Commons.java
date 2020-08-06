package detai.android.CommonUtils;
import android.app.Activity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import detai.android.DataAccess.KeysListQuestion;
import detai.android.DataAccess.ThiTracNghiemDbContext;
import detai.android.thitoec.TracNghiemToeic;

public class Commons {

    public static String tenThiSinh = "Huỳnh Tấn Sáng";

    public  static void ShowMessage(Activity activity, String message)
    {
        Toast.makeText(activity, message, Toast.LENGTH_LONG);
    }

    public static void CopyDatabase(TracNghiemToeic activity) throws IOException {

        ThiTracNghiemDbContext db = new KeysListQuestion(activity);
        db.Delete(0,true);
        InputStream fis = activity.getAssets().open(Constants.DATABASE_NAME_DATA);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        int idx = 1;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line != null && !line.isEmpty()) {
                String[] list = line.split("\t");
                if(list != null && list.length >= 7){
                    db.Insert(new Question(
                            idx++,
                            list[0],
                            list[1],
                            list[2],
                            list[3],
                            list[4],
                            Integer.parseInt(list[5]),
                            Integer.parseInt(list[6])
                    ));
                }
            }
        }
        br.close();
    }

}
