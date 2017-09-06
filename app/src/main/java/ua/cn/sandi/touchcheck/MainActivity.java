package ua.cn.sandi.touchcheck;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mikni on 28.08.2017.
 */

public class MainActivity extends Activity implements View.OnTouchListener {


    TextView tv;
    ImageView iv;

    float x;
    float y;
    float sx;
    float sy;
    String sDown;
    String sMove;
    String sUp;

    StatBitmap sb;

    int ivw;
    int ivh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        tv = (TextView) findViewById(R.id.text_cords);
        iv = (ImageView) findViewById(R.id.view_touch);
        iv.setOnTouchListener(this);


        iv.post(new Runnable()
        {
            public void run()
            {
                ivw = iv.getWidth();
                ivh = iv.getHeight();
                sb = new StatBitmap();
                sb.setIv(iv);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        StringBuilder sb1 = new StringBuilder();

        // Выведем в TextView информацию о нажатом пункте меню
        sb1.append("Item Menu");
        sb1.append("\r\n groupId: " + String.valueOf(item.getGroupId()));
        sb1.append("\r\n itemId: " + String.valueOf(item.getItemId()));

        Toast.makeText(this,sb1.toString(), Toast.LENGTH_LONG);

        return super.onOptionsItemSelected(item);
    }

        @Override
        public boolean onTouch (View v, MotionEvent event){

            x = event.getX();
            y = event.getY();

         //   final StatBitmap sb = new StatBitmap();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    sDown = "Tdown: x=" + x + ", y=" + y;
                    sMove = "";
                    sUp = "";
                    sx = x;
                    sy = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    sMove = "Tmove: x=" + x + ", y=" + y;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    sUp = "Tup: x=" + x + ", y=" + y;

                    sb.setwS(sx);
                    sb.sethS(sy);
                    sb.setwF(x);
                    sb.sethF(y);
                    sb.setP(Color.RED);

                    sb.drawmyline();

                    break;
            }

            tv.setText(sDown + "\n" + sMove + "\n" + sUp);

            return true;
        }

    private class StatBitmap {

        float hS;
        float wF;
        float hF;
        float wS;

        ImageView iv;
        Bitmap b;
        Canvas c;
        Paint p;

        private void setwS(float wS) {
            this.wS = wS;
        }
        private void sethS(float hS) {
            this.hS = hS;
        }
        private void setwF(float wF) {
            this.wF = wF;
        }
        private void sethF(float hF) {
            this.hF = hF;
        }
        public void setP(int pcolor) {
            p.setColor(pcolor);
        }
        public Paint getP() {
            return p;
        }

        private void setIv(ImageView iv) {
            this.iv = iv;
            b = Bitmap.createBitmap(this.iv.getWidth(), this.iv.getHeight(), Bitmap.Config.ARGB_8888);
            c = new Canvas(b);
            iv.setImageBitmap(b);
        }

        StatBitmap() {
            // init
            p = new Paint();
            p.setColor(Color.GREEN);
        }

        public void drawmyline() {
            c.drawLine(wS, hS, wF, hF, p);
        }
    }
}




