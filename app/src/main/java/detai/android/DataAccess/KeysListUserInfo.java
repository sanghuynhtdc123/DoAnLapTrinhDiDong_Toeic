package detai.android.DataAccess;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import detai.android.CommonUtils.Constants;
import detai.android.CommonUtils.UserInfo;


public class KeysListUserInfo extends ThiTracNghiemDbContext {

    public KeysListUserInfo(Context context) {
        super(context);
    }

    @Override
    public boolean Insert(Object ob) {

        if(ob instanceof UserInfo)
        {
            return getWritableDatabase().insert(Constants.TBL_THISINH,
                    Constants.COL_THISINH_TENTHISINH,((UserInfo)ob).ExportToContentValues()) > 0;
        }

        return false;
    }

    @Override
    public boolean Update(Object ob) {
        return false;
    }

    @Override
    public boolean Delete(Integer id, boolean isDeleteAll) {

        if(isDeleteAll)
        {
            int rs = getWritableDatabase().delete(Constants.TBL_THISINH,
                    null,
                    null);

            return rs > 0;
        }
        String whereClause = Constants.COL_THISINH_ID + " = ?";
        String[] whereArgs = new String[]{ id+"" };
        int rs = getWritableDatabase().delete(Constants.TBL_THISINH,
                whereClause,
                whereArgs);

        return rs > 0;
    }

    @Override
    public ArrayList<Object> GetAll()
    {
        Cursor cursor =getReadableDatabase().query(Constants.TBL_THISINH,
                null,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Object> arrayList = new ArrayList<>();
        while (cursor.moveToNext())
        {
            UserInfo obj = new UserInfo();
            obj.ImportUserInfo(cursor);
            arrayList.add(obj);
        }

        return arrayList;
    }
}
