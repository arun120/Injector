
package com.example.multiplier;



import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


public class ServiceFloating extends Service {

    public static  int ID_NOTIFICATION = 2018;

    private WindowManager windowManager;
    private ImageView chatHead;
    private PopupWindow pwindo;

    boolean mHasDoubleClicked = false;
    long lastPressTime;
    private Boolean _enable = true;



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();



        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);

        chatHead.setImageResource(R.drawable.wolf);



        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(chatHead, params);
        Log.i("abc","open");

        try {
            chatHead.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            // Get current time in nano seconds.
                            long pressTime = System.currentTimeMillis();


                            // If double click...
                            if (pressTime - lastPressTime <= 300) {
                                //createNotification();
                                ServiceFloating.this.stopSelf();
                                mHasDoubleClicked = true;
                            }
                            else {     // If not double click....
                                mHasDoubleClicked = false;
                            }
                            lastPressTime = pressTime;
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(chatHead, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

        chatHead.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                initiatePopupWindow(chatHead);
                _enable = false;
                //				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //				getApplicationContext().startActivity(intent);
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void initiatePopupWindow(View anchor) {
        //try {
        final Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ListPopupWindow popup = new ListPopupWindow(this);
        popup.setAnchorView(anchor);
        popup.setWidth((int) (display.getWidth() / (1.5)));


        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupview=layoutInflater.inflate(R.layout.activity_input,null);

        final PopupWindow activity =new PopupWindow(popupview,LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        activity.setWidth((int) (display.getWidth() / (2)));
       // activity.setHeight((int) display.getHeight());

        Log.i("Setup", "Done");
        activity.showAsDropDown(anchor, 50, -30);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.dismiss();
            }
        }, 2500);


        Button abc=(Button) popupview.findViewById(R.id.abc);

        try {
            Runtime.getRuntime().exec("adb shell input keyevent 4");
        }catch(Exception bc)
        {
            bc.printStackTrace();
        }
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //ShellExecuter s=new ShellExecuter();
                //String a=s.Executer("adb shell input keyevent 33");
                //Toast.makeText(getApplicationContext(),a,Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Instrumentation inst = new Instrumentation();
                        inst.sendCharacterSync(KeyEvent.KEYCODE_A);
                       // inst.sendStringSync("Mutiplier");


                        Log.i("sent", "a");
                        try {
                            //Runtime.getRuntime().exec("adb shell input keyevent 33");
                        }catch(Exception bc)
                        {
                            bc.printStackTrace();
                        }

                    }
                }).start();
            }


        });

/*new Thread(new Runnable() {
    @Override
    public void run() {
        Instrumentation inst=new Instrumentation();
        //inst.sendCharacterSync(KeyEvent.KEYCODE_A);
        inst.sendPointerSync(MotionEvent.obtain(
                android.os.SystemClock.uptimeMillis(),
                android.os.SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN, 336, 654, 0));
        inst.sendStringSync("sfa");
        Log.i("sent", "a");


    }
}).start();*/






        //ArrayAdapter<String> arrayAdapter =
        //new ArrayAdapter<String>(this,R.layout.list_item, myArray);
          /*  popup.setAdapter(new CustomAdapter(getApplicationContext(), R.layout.row, listCity));
            popup.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id3) {
                    //Log.w("tag", "package : "+apps.get(position).pname.toString());
                    Intent i;
                    PackageManager manager = getPackageManager();
                    try {
                        i = manager.getLaunchIntentForPackage(apps.get(position).pname.toString());
                        if (i == null)
                            throw new PackageManager.NameNotFoundException();
                        i.addCategory(Intent.CATEGORY_LAUNCHER);
                        startActivity(i);
                    } catch (PackageManager.NameNotFoundException e) {

                    }
                }
            });
            popup.show();

        } catch (Exception e) {
            e.printStackTrace();*/
        // }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }

}