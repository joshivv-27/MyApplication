package com.example.abcd.myapplication.FABDemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.abcd.myapplication.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class FABActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        /*
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        */

        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);

        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.fab1:
                Toast.makeText(this, "clicked Message.", Toast.LENGTH_LONG).show();
//                Log.d("", "Fab 1");
                break;

            case R.id.fab2:
                Toast.makeText(this, "clicked Done.", Toast.LENGTH_LONG).show();
//                Log.d("", "Fab 2");
                break;
        }
    }

    private void animateFAB() {

        /*if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setVisibility(View.GONE);
            fab2.setVisibility(View.GONE);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            isFabOpen = true;

        }
*/
    }
}
