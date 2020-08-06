package detai.android.DataAccess;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import detai.android.CommonUtils.Constants;
import detai.android.CommonUtils.Question;


public class KeysListQuestion extends ThiTracNghiemDbContext {

    public KeysListQuestion(Context context) {
        super(context);
    }

    @Override
    public boolean Insert(Object ob) {

        if (ob instanceof Question)
        {
            return getWritableDatabase().insert(Constants.TBL_CAUHOI,
                    Constants.COL_CAUHOI_NOIDUNG,((Question)ob).ExportToContentValues()) > 0;
        }
        return false;
    }

    @Override
    public boolean Update(Object ob) {

        if(ob instanceof Question)
        {
            String whereClause = Constants.COL_CAUHOI_ID + " = ?";
            String[] whereArgs = new String[]{ ((Question)ob).getId()+"" };
            int rs = getWritableDatabase().update(Constants.COL_CAUHOI_NOIDUNG,
                    ((Question)ob).ExportToContentValues(),
                    whereClause,
                    whereArgs);

            return rs > 0;
        }
        return false;
    }

    @Override
    public boolean Delete(Integer id, boolean isDeleteAll) {

        if(isDeleteAll)
        {
            int rs = getWritableDatabase().delete(Constants.TBL_CAUHOI,
                    null,
                    null);

            return rs > 0;
        }

        String whereClause = Constants.COL_CAUHOI_ID + " = ?";
        String[] whereArgs = new String[]{ id+"" };
        int rs = getWritableDatabase().delete(Constants.COL_CAUHOI_NOIDUNG,
                whereClause,
                whereArgs);

        return rs > 0;
    }

    @Override
    public ArrayList<Object> GetAll()
    {
        Cursor cursor =getReadableDatabase().query(Constants.TBL_CAUHOI,
                null,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Object> arrayList = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Question obj = new Question();
            obj.ImportQuestion(cursor);
            arrayList.add(obj);
        }

        return arrayList;
    }
}
