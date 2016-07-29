package com.example.drum.hulixia.scope;

import com.example.drum.hulixia.view.fragment.FoodsFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by hulixia on 2016/7/12.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FoodsScope {
}
