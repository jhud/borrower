package disconnectionist.www.borrower;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void borrowButton(View btn) {
        Intent intent = new Intent(this, ScannerActivity.class);
        Bundle b = new Bundle();
        b.putString("action", "borrow"); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    public void returnButton(View btn) {
        Intent intent = new Intent(this, ScannerActivity.class);
        Bundle b = new Bundle();
        b.putString("action", "return"); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
