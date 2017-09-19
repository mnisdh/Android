package android.daehoshin.com.coustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daeho on 2017. 9. 18..
 */

public class CustomView extends View {
    /**
     * 코드로 생성할때 호출하는 생성자
     * @param context
     */
    public CustomView(Context context){
        super(context);
    }

    /**
     * xml 에서 태그로 사용할 때 시스템에서 호출해주는 생성자
     * @param context
     * @param attrs
     */
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 컨트롤이 그려질때 동작하는 메소드에 사각형을 그리는 코드 추가
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 물감 - 겉모양을 결정하는 도구
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(2);

        // 종이 - 무엇을 그릴지 결정하는 도구
        canvas.drawRect(100, 100, 200, 200, paint);
    }
}
