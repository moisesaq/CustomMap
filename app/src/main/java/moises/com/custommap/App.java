package moises.com.custommap;

import android.app.Application;

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //initializeLeakCanary();
    }

    /*private void initializeLeakCanary(){
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }*/
}
