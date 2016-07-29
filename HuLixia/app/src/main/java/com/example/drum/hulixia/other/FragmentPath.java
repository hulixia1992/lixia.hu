package com.example.drum.hulixia.other;

import android.graphics.Point;

/**
 * Created by hulixia on 2016/7/8.
 * 封装路径
 */
public class FragmentPath {
    //记录当前path片段的命令
    public PathType pathType;
    // 数据占用长度，同样是Line to,V、H与L后面携带的数据长度不同，这里需要记录
    public int dataLen;
    public Point p1;
    public Point p2;
    public Point p3;
}
