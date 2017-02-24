package disconnectionist.www.borrower;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jameshudson on 24/2/17.
 */

public class BorrowListModel implements Serializable {
    final String fileName = "data.txt";

    public Map<String, BorrowItem> items = new HashMap<>();

    public void save(Context context) {
        try {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(items);

        os.close();
        fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(Context context) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            items = (Map<String, BorrowItem>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toHTML() {
        String out = "<table style=\"width:100%\">";

        try {
        for (Map.Entry<String, BorrowItem> entry : items.entrySet()) {
            BorrowItem val = entry.getValue();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
            String date = sdf.format(val.date);

            out += "<tr>";
            out += "<td>" + date + "</td><td>" + val.user +"</td><td>" + val.item + "</td>";
            out += "</tr>";
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out + "</table>";
    }
}
