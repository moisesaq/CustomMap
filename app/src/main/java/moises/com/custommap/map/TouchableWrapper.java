package moises.com.custommap.map;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;


public class TouchableWrapper extends FrameLayout {

    private OnTouchCustomMapListener onTouchSupportMapListener;
    public TouchableWrapper(Context context) {
        super(context);
    }

    public void setOnTouchSupportMapListener(OnTouchCustomMapListener listener){
        this.onTouchSupportMapListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if(onTouchSupportMapListener != null)
                    onTouchSupportMapListener.onTouchDownMap();
                break;

            case MotionEvent.ACTION_UP:
                if(onTouchSupportMapListener != null)
                    onTouchSupportMapListener.onTouchUpMap();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public interface OnTouchCustomMapListener {
        void onTouchDownMap();
        void onTouchUpMap();
    }
}
