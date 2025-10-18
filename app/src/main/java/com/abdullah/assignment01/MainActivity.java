package com.abdullah.assignment01;

import static android.widget.Toast.*;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton menuButton;
    ImageButton[] button2n3 = new ImageButton[2];
    LinearLayout sciLayout;
    LinearLayout extraSpace;
    boolean isBasic;
    boolean isScientific;
    LinearLayout landNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // getting ids
        menuButton = findViewById(R.id.menuButton);
        button2n3[0] = findViewById(R.id.menuButton2);
        button2n3[1] = findViewById(R.id.menuButton3);
        landNormal = findViewById(R.id.normalLayoutLand);
        sciLayout = findViewById(R.id.sciLayout);
        isBasic = true;
        isScientific = false;

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu obj = new PopupMenu(MainActivity.this, menuButton);
                obj.getMenuInflater().inflate(R.menu.menu_items, obj.getMenu());
                obj.show();

                obj.setOnMenuItemClickListener(items -> {
                    int id = items.getItemId();
                    if (id == R.id.basicOption && !isBasic) {
                        extraSpace = findViewById(R.id.extraSpace);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) extraSpace.getLayoutParams();
                        LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();

                        Toast.makeText(MainActivity.this, "Basic", LENGTH_SHORT).show();

                        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 0.2f);
                        ValueAnimator animate = ValueAnimator.ofFloat(1.0f, 0.0f);
                        animator.setDuration(300);
                        animate.setDuration(300);
                        animator.addUpdateListener(animation -> {
                            params.weight = (Float) animator.getAnimatedValue();
                            extraSpace.setLayoutParams(params);
                        });

                        animate.addUpdateListener(animation -> {
                            sciParams.weight = (Float) animate.getAnimatedValue();
                            sciLayout.setLayoutParams(sciParams);
                        });
                        animator.start();
                        animate.start();
                        isBasic = true;
                        isScientific = false;
                    } else if (id == R.id.sciOption && !isScientific) {
                        extraSpace = findViewById(R.id.extraSpace);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) extraSpace.getLayoutParams();
                        LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();

                        Toast.makeText(MainActivity.this, "Basic", LENGTH_SHORT).show();

                        ValueAnimator animator = ValueAnimator.ofFloat(0.2f, 0.0f);
                        ValueAnimator animate = ValueAnimator.ofFloat(0.0f, 1.0f);
                        animator.setDuration(300);
                        animate.setDuration(300);
                        animator.addUpdateListener(animation -> {
                            params.weight = (Float) animator.getAnimatedValue();
                            extraSpace.setLayoutParams(params);
                        });
                        animate.addUpdateListener(animation -> {
                            sciParams.weight = (Float) animate.getAnimatedValue();
                            sciLayout.setLayoutParams(sciParams);
                        });
                        animator.start();
                        animate.start();
                        isBasic = false;
                        isScientific = true;
                    }
                    return true;
                });
            }
        });

        for(ImageButton menuButton2 : button2n3) {
            menuButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu obj = new PopupMenu(MainActivity.this, menuButton2);
                    obj.getMenuInflater().inflate(R.menu.menu_items, obj.getMenu());
                    obj.show();

                    obj.setOnMenuItemClickListener(items -> {
                        int id = items.getItemId();
                        if (id == R.id.basicOption && !isBasic) {
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) landNormal.getLayoutParams();
                            LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();

                            Toast.makeText(MainActivity.this, "Basic", LENGTH_SHORT).show();

                            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
                            ValueAnimator animate = ValueAnimator.ofFloat(0.0f, 1.0f);
                            animator.setDuration(350);
                            animate.setDuration(350);
                            animator.addUpdateListener(animation -> {
                                sciParams.weight = (Float) animator.getAnimatedValue();
                                sciLayout.setLayoutParams(sciParams);
                            });

                            animate.addUpdateListener(animation -> {
                                params.weight = (Float) animate.getAnimatedValue();
                                landNormal.setLayoutParams(params);
                            });
                            animator.start();
                            animate.start();
                            isBasic = true;
                            isScientific = false;
                        } else if (id == R.id.sciOption && !isScientific) {
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) landNormal.getLayoutParams();
                            LinearLayout.LayoutParams sciParams = (LinearLayout.LayoutParams) sciLayout.getLayoutParams();

                            Toast.makeText(MainActivity.this, "Basic", LENGTH_SHORT).show();

                            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
                            ValueAnimator animate = ValueAnimator.ofFloat(1.0f, 0.0f);
                            animator.setDuration(300);
                            animate.setDuration(300);
                            animator.addUpdateListener(animation -> {
                                sciParams.weight = (Float) animator.getAnimatedValue();
                                sciLayout.setLayoutParams(sciParams);
                            });

                            animate.addUpdateListener(animation -> {
                                params.weight = (Float) animate.getAnimatedValue();
                                landNormal.setLayoutParams(params);
                            });
                            animator.start();
                            animate.start();
                            isBasic = false;
                            isScientific = true;
                        }
                        return true;
                    });
                }
            });
        }

    }
}