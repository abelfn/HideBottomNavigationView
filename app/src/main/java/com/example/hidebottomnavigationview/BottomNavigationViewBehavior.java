package com.example.hidebottomnavigationview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//To use this class with other layout change BottomNavigationView to any layout name,ex:- LinearLayout
public final class BottomNavigationViewBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {
    private View bottomBar;
    private View bottomToolbar;
    private boolean isInitialized;
    private View topToolbar;

    public BottomNavigationViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, BottomNavigationView bottomNavigationView, View view) {

        if (!(view instanceof AppBarLayout)) {
            return false;
        }
        this.bottomBar = bottomNavigationView;
        View findViewById = view.findViewById(R.id.toolbar);

        this.topToolbar = findViewById;
        View findViewById2 = bottomNavigationView.findViewById(R.id.nav_view);

        this.bottomToolbar = findViewById2;
        this.isInitialized = true;
        return true;
    }

    public boolean onDependentViewChanged( CoordinatorLayout coordinatorLayout,  BottomNavigationView bottomNavigationView,  View view) {

        View view2 = this.bottomToolbar;

        int height = view2.getHeight();
        View view3 = this.topToolbar;

        if (view3.getHeight() == 0) {
            return true;
        }
        int i = (-view.getTop()) * height;
        View view4 = this.topToolbar;

        bottomNavigationView.setTranslationY((float) Math.min(i / view4.getHeight(), height));
        return true;
    }

    public final void setExpanded(boolean z) {
        if (this.isInitialized && z) {
            View view = this.bottomBar;

            view.setTranslationY(0.0f);
        }
    }
}
