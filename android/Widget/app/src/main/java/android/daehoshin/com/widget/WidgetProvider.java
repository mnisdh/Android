package android.daehoshin.com.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;

/**
 * Created by daeho on 2017. 11. 24..
 */

public class WidgetProvider extends AppWidgetProvider {
    public static final int INTERVAL = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        if(AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)){
            AppWidgetManager wManager = AppWidgetManager.getInstance(context);
            Bundle extras = intent.getExtras();

            if(extras != null){
                int appWidgetIds[] = wManager.getAppWidgetIds(new ComponentName(context, getClass()));

                for(int appWidgetId : appWidgetIds) updateWidget(context, wManager, appWidgetId);
            }

            long next = System.currentTimeMillis() + INTERVAL;
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent target = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, target, 0);
            alarmManager.set(AlarmManager.RTC, next, pendingIntent);
        }
    }

    /**
     * 위젯 갱신주기에 따라 호출된다
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//                    for (int widgetId : appWidgetIds) {
//                        updateWidget(context, appWidgetManager, widgetId);
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        // 위젯 레이아웃 가져오기

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        remoteViews.setTextViewText(R.id.tvTime, sdf.format(System.currentTimeMillis()));

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private void createWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        // 위젯 레이아웃 가져오기
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        remoteViews.setTextViewText(R.id.tvTime, sdf.format(System.currentTimeMillis()));

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}
