package test.decoding.audio;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RunnableFuture;

public class TestActivity extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("decoding-test");
    }

    private List<String> assets = Arrays.asList("stereo_44.m4a",
                                                "stereo_48.m4a",
                                                "stereo_44.mp3",
                                                "stereo_48.mp3");

    private List<String> files = new ArrayList<String>();

    private int current = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        extractCompressedFiles();

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                current = 0;
                nativeContextPtr = decodeWithOpenSL(files.get(0));
            }
        });

        text = (TextView) findViewById(R.id.sample_text);
    }

    private TextView text;

    private long nativeContextPtr = 0;

    public native long decodeWithOpenSL(String file);
    public native void destroyNativeContext(long nativePtr);


    // this is called from native code when decoding is over

    public void onFileDecoded(boolean succ, double dur) {

        final boolean success = succ;
        final double duration = dur;

        runOnUiThread(new Runnable () {

            @Override
            public void run() {

                String testFile = new File(files.get(current)).getName();

                if (success) {
                    String strDuration = new DecimalFormat("#.###").format(duration);
                    text.append("\n" + testFile + " opensl decoding time: "+strDuration);
                }
                else {
                    text.append("\n" + testFile + " opensl decoding ERROR!");
                }

                destroyNativeContext(nativeContextPtr);

                if (current < files.size()-1) {
                    current++;
                    nativeContextPtr = decodeWithOpenSL(files.get(current));
                }
                else {
                    findViewById(R.id.button_start).setEnabled(true);
                }

            }
        });
    }

    private void extractCompressedFiles() {

        for (String asset : assets) {
            String file = extractFromAssets(asset, this);
            if (file != null)
                files.add(file);
        }

    }

    private static String extractFromAssets(String assetName, Context ctx) {

        AssetManager am = ctx.getAssets();
        InputStream source = null;
        File destFile = null;
        OutputStream destination = null;

        try {
            source = am.open(assetName);
            destFile = new File(ctx.getFilesDir(),assetName);
            destination = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int read;
            while((read = source.read(buffer)) != -1){
                destination.write(buffer, 0, read);
            }

            return destFile.getCanonicalPath();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException e) {}
            }
            if (destination != null) {
                try {
                    destination.close();
                } catch (IOException e) {}
            }
        }
    }

}
