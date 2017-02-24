package disconnectionist.www.borrower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends Activity {

    BorrowListModel itemList;

    private void refreshList() {
        WebView listView = (WebView) findViewById(R.id.listView);
        listView.loadData(itemList.toHTML(), "text/html", null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = new BorrowListModel();

        itemList.load(getApplicationContext());

        refreshList();
    }

    public void borrowButton(View btn) {
        Intent intent = new Intent(this, ScannerActivity.class);
        Bundle b = new Bundle();
        b.putString("action", "borrow"); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivityForResult(intent, 333);
    }

    public void returnButton(View btn) {
        Intent intent = new Intent(this, ScannerActivity.class);
        Bundle b = new Bundle();
        b.putString("action", "return"); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivityForResult(intent, 333);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String itemname;
            String username;
            String verb;
            if (data.hasExtra("verb")) {
                username = data.getExtras().getString("username");
                verb = data.getExtras().getString("verb");
                itemname = data.getExtras().getString("itemname");
                Toast.makeText(this, username + " " + verb + " " + itemname,
                        Toast.LENGTH_LONG).show();


                if (verb.equalsIgnoreCase("borrow")) {
                    BorrowItem item = new BorrowItem();
                    item.item = itemname;
                    item.user = username;
                    item.date = new Date();

                    itemList.items.put(itemname, item);
                }
                else {
                    itemList.items.remove(itemname);
                }

                itemList.save(getApplicationContext());

                refreshList();
            }
        }
    }
}
