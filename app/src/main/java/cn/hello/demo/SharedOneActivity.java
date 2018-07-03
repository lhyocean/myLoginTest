package cn.hello.demo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hello.jnutil.R;

public class SharedOneActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    private ImageView imgView;
    boolean isFirst=false;
    private LinearLayout mLinearLayout;
    private int mSoftKeyBordHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_one);
        imgView = (ImageView) findViewById(R.id.iv);
        mLinearLayout= (LinearLayout) findViewById(R.id.ll_edit_view);
        mSoftKeyBordHeight = getResources().getDisplayMetrics().heightPixels/3;

        this.getWindow().getDecorView().getRootView().getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void toShareActivity(View v) {
        Intent intent = new Intent(SharedOneActivity.this, ShareingActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                SharedOneActivity.this,
                imgView,
                "imageShare").toBundle());
    }

    int count=0;


    @Override
    public void onGlobalLayout() {


        int d20= (int) getResources().getDimension(R.dimen.dimen_20);
        int d40= (int) getResources().getDimension(R.dimen.dimen_40);
        int d60= (int) getResources().getDimension(R.dimen.dimen_60);
        int d80= (int) getResources().getDimension(R.dimen.dimen_80);

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator  objectAnimatorUp=ObjectAnimator.ofFloat(mLinearLayout,"translationY",0,-d20,-d40,-d60,-d80);
        objectAnimatorUp.setDuration(300);
        ObjectAnimator  objectAnimatorDown=ObjectAnimator.ofFloat(mLinearLayout,"translationY",-d80,-d60,-d40,-d20,0);
        objectAnimatorDown.setDuration(300);


        boolean f=isKeyboardShown(this.getWindow().getDecorView().getRootView());
        if (f){

            Log.e(TAG, "onGlobalLayout:   -----up------" +(imgView.getVisibility()==View.GONE)
                    +"----" +(imgView.getVisibility()==View.VISIBLE)+"--"
                    +(imgView.getVisibility()==View.INVISIBLE));
            if (imgView.getVisibility()==View.GONE){
                return;
            }

            Animation animation = AnimationUtils.loadAnimation(SharedOneActivity.this, R.anim.sca_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    imgView.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            if (imgView.getVisibility()==View.VISIBLE) {
                imgView.startAnimation(animation);
                set.play(objectAnimatorUp);
                set.start();
            }


        }else {

            Log.e(TAG, "onGlobalLayout:   -----down---"+(imgView.getVisibility()==View.GONE)
                    +"----" +(imgView.getVisibility()==View.VISIBLE)+"--"+(imgView.getVisibility()==View.INVISIBLE));

                if (imgView.getVisibility()!=View.GONE){
                    return;
                }

                Animation animation = AnimationUtils.loadAnimation(SharedOneActivity.this, R.anim.sca_in);
                imgView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        imgView.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                set.play(objectAnimatorDown);
                set.start();


        }
    }

    public   String TAG=SharedOneActivity.this.getClass().getSimpleName();

    /**
     *
     * @param rootView
     * @return
     */
    private boolean isKeyboardShown(View rootView) {

        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;  //  如果有华为等类似虚拟按键，
        Log.e(TAG, "isKeyboardShown: "+heightDiff );
        // 得到的heightDiff 初始值就不是0，而是虚拟按键的高度
        return heightDiff > mSoftKeyBordHeight;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
