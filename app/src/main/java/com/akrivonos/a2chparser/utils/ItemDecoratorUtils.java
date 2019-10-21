package com.akrivonos.a2chparser.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoratorUtils {

    public static RecyclerView.ItemDecoration createItemDecorationOffsets(final DecorationDirection direction, final int countDecoration) {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                switch (direction) {
                    case TOP:
                        outRect.top = countDecoration;
                        break;
                    case LEFT:
                        outRect.left = countDecoration;
                        break;
                    case RIGHT:
                        outRect.right = countDecoration;
                        break;
                    case BOTTOM:
                        outRect.bottom = countDecoration;
                        break;
                }
            }
        };
    }

    public enum DecorationDirection {
        TOP, BOTTOM, RIGHT, LEFT
    }
}
