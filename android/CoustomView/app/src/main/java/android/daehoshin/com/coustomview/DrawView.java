package android.daehoshin.com.coustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 18..
 */

public class DrawView extends View {
    private PathTool currentPath;
    private Paint paint;

    List<PathTool> pts = new ArrayList<>();

    // 소스코드에서만 사용하기 때문에 생성자 파라미터는 context만 필요
    public DrawView(Context context) {
        super(context);
        paint = new Paint();

        init();
    }

    private void init(){
        setColor(Color.CYAN, 1);

        paint.setColor(currentPath.getColor());
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setColor(int color, int size){
        PathTool pt = new PathTool(color, size);
        pts.add(pt);
        currentPath = pt;
    }

    public void setSize(int size){
        setColor(currentPath.getColor(), size);
    }

    // 화면을 그려주는 onDraw 오버라이드
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(PathTool pt : pts) {
            paint.setColor(pt.getColor());
            paint.setStrokeWidth(pt.getSize());
            canvas.drawPath(pt, paint);
        }

        //for(int i = 0; i < coordX.size(); i++){
        //    // 화면에 터치가 되면 - 연속해서 그림을 그려준다
        //    canvas.drawCircle(coordX.get(i), coordY.get(i), drawSize, p);
        //}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 패스를 해당 좌표로 이동한다
                currentPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                // 해당 좌표를 그려준다
                currentPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        invalidate(); // <- 다른 언어에서는 대부분 그림을 그려주는 함수를 호출하는 메서드는
                      //    기존 그림을 유지하는데 안드로이드는 지운다

        // 리턴이 false일 경우는 touch 이벤트를 연속해서 발생시키지 않는다
        // 즉 드래그를 하면 onTouchEvent가 재 호출되지 않는다
        return true;
    }

}
