package android.daehoshin.com.memo.draw;

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
    // 현재 사용중인 path 변수
    private PathTool currentPath;
    // 공통으로 사용될 paint 변수
    private Paint paint;

    // 컬러, 사이즈 별로 담아둘 path 리스트
    List<PathTool> pts = new ArrayList<>();

    // 소스코드에서만 사용하기 때문에 생성자 파라미터는 context만 필요
    public DrawView(Context context) {
        super(context);
        paint = new Paint();

        init();
    }

    private void init(){
        // 처음 사용될 컬러, 사이즈 설정
        setColor(Color.CYAN, 1);

        // 공통으로 적용되는 페인트 설정
        paint.setColor(currentPath.getColor());
        paint.setStyle(Paint.Style.STROKE);
    }

    public void clear(){
        int color = currentPath.getColor();
        float size = currentPath.getSize();

        pts.clear();
        setColor(color, size);

        invalidate();
    }

    /**
     * 컬러, 사이즈 변경시 path 새로 생성하여 currentPath로 설정하는 메소드
     * @param color
     * @param size
     */
    public void setColor(int color, float size){
        // PathTool 새로생성 -> path list에 추가 -> 생성된path를 currentPath로 설정
        PathTool pt = new PathTool(color, size);
        pts.add(pt);
        currentPath = pt;
    }

    /**
     * 사이즈 변경시 path 새로 생성하여 currentPath로 설정하는 메소드
     * @param size
     */
    public void setSize(int size){
        // 사이즈 변경만 하므로 기존 컬러 가져와서 setColor로 재호출하여 동일하게 path설정
        setColor(currentPath.getColor(), size);
    }

    // 화면을 그려주는 onDraw 오버라이드

    /**
     * 화면을 그려주는 onDraw 오버라이드하여 담고있는 path list를 화면에 표시해줌
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(PathTool pt : pts) {
            // pathTool에 갖고있는 컬러와 사이즈로 paint 설정
            paint.setColor(pt.getColor());
            paint.setStrokeWidth(pt.getSize());
            // 캔버스에 해당 path 출력
            canvas.drawPath(pt, paint);
        }
    }

    /**
     * 터치이벤트를 오버라이드 하여 path값을 설정하고 onDraw발생시키는 코드추가
     * @param event
     * @return
     */
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
        }
        invalidate(); // <- 다른 언어에서는 대부분 그림을 그려주는 함수를 호출하는 메서드는
                      //    기존 그림을 유지하는데 안드로이드는 지운다

        // 리턴이 false일 경우는 touch 이벤트를 연속해서 발생시키지 않는다
        // 즉 드래그를 하면 onTouchEvent가 재 호출되지 않는다
        return true;
    }

}
