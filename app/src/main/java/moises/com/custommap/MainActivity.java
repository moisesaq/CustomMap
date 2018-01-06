package moises.com.custommap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moises.com.custommap.demo.PolylineDemoActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.fab) protected FloatingActionButton fab;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
        showMainFragment();
    }

    private void setUp() {
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(view ->
                PolylineDemoActivity.start(this));
    }

    private void showMainFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, MainFragment.newInstance())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
