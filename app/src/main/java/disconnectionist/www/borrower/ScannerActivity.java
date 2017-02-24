package disconnectionist.www.borrower;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannerActivity extends Activity {

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    private SurfaceView cameraView;
    private TextView usernameTextView;
    private TextView itemNameTextView;

    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        TextView actionText = (TextView)findViewById(R.id.actionName);

        if (action == "borrow") {
            actionText.setText("is borrowing");
        }
        else {
            actionText.setText("is returning");
        }

        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        usernameTextView = (TextView)findViewById(R.id.username);
        itemNameTextView = (TextView)findViewById(R.id.itemname);

        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(400, 400)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    final String data = barcodes.valueAt(0).displayValue;

                    if (data.toLowerCase().startsWith("u")) {
                        usernameTextView.post(new Runnable() {    // Use the post method of the TextView
                            public void run() {
                                usernameTextView.setText(data.substring(1));
                            }
                        });
                        beep();
                    }
                    else if (data.toLowerCase().startsWith("i")) {
                        itemNameTextView.post(new Runnable() {    // Use the post method of the TextView
                            public void run() {
                                itemNameTextView.setText(data.substring(1));
                            }
                        });
                        beep();
                    }
                }
            }
        });
    }

    private void beep() {
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,250);
    }
}
