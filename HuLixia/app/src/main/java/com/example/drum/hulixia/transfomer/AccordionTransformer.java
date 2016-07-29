package com.example.drum.hulixia.transfomer;

import android.view.View;

/**
 * Created by hulixia on 2016/7/7.
 */
public class AccordionTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setScaleX(position < 0 ? 1f + position : 1f - position);
    }

}