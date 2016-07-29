package com.example.drum.hulixia.other;

import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hulixia on 2016/7/8.
 * 工具类
 */
public class SVGUtil {
    private static volatile SVGUtil svgUtil;
    private Set<String> svgCommandSet;
    private String[] command = {"M", "L", "H", "V", "C", "S", "Q", "T", "A",
            "Z"};

    private SVGUtil() {
        svgCommandSet = new HashSet<String>();
        for (String cmd : command) {
            svgCommandSet.add(cmd);
        }
    }

    public static SVGUtil getInstance() {
        if (svgUtil == null) {
            synchronized (SVGUtil.class) {
                if (svgUtil == null) {
                    svgUtil = new SVGUtil();
                }
            }
        }
        return svgUtil;
    }

    // 提取SVG数据
    public ArrayList<String> extractSvgData(String svgData) {
        //以下为了将命令字母两边添加空格
        //保存已经替换过的字母
        Set<String> hasReplaceSet = new HashSet<String>();
        //正则表达式，用于匹配path里面的字母
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(svgData);
        //遍历匹配正则表达式的字符串
        while (matcher.find()) {
            //s为匹配的字符串
            String s = matcher.group();
            //如果该字符串没有替换，则在改字符串两边加空格
            if (!hasReplaceSet.contains(s)) {
                svgData = svgData.replace(s, " " + s + " ");
                hasReplaceSet.add(s);
            }
        }
        //---end--命令字母两边添加字母结束---
        //将","替换为" ",并强制转为大写字母
        svgData = svgData.replace(",", " ").trim().toUpperCase();
        //以" "为分割符分割字符串
        String[] ss = svgData.split(" ");
        //将最终分割成的字符串数组转为List
        ArrayList<String> data = new ArrayList<String>();
        for (String s : ss) {
            //只有当前的字符串不是空格，才将该字符串加入到List中
            //相当于实现了自动删除多余的空格
            if (s != null && !"".equals(s)) {
                data.add(s);
            }
        }
        return data;
    }
    //根据ArrayList保存的数据，将path数据转为Android中的Path对象
//widthFactor,宽度放缩倍数
//heightFactor,高度放缩倍数
    public Path parsePath(ArrayList<String> svgDataList, float widthFactor,
                          float heightFactor) {
        //new一个需要返回的Path对象
        Path path = new Path();
        //解析字符串偏移位置
        int startIndex = 0;
        //上一次绘制的终点，默认为左上角
        Point lastPoint = new Point(0, 0);
        //提取下一条FragmentPath对象
        FragmentPath fp = nextFrag(svgDataList, startIndex, lastPoint);
        //如果下一条FragmentPath不为null，则循环
        while (fp != null) {
            //根据命令类型，执行Path的不同方法，主要，所有的坐标需要乘以放缩倍数
            switch (fp.pathType) {
                case MOVE: {
                    path.moveTo(fp.p1.x * widthFactor, fp.p1.y * heightFactor);
                    lastPoint = fp.p1;
                    break;
                }
                case LINE_TO: {
                    path.lineTo(fp.p1.x * widthFactor, fp.p1.y * heightFactor);
                    lastPoint = fp.p1;
                    break;
                }
                case CURVE_TO: {
                    path.cubicTo(fp.p1.x * widthFactor, fp.p1.y * heightFactor,
                            fp.p2.x * widthFactor, fp.p2.y * heightFactor, fp.p3.x
                                    * widthFactor, fp.p3.y * heightFactor);
                    lastPoint = fp.p3;
                    break;
                }
                case QUAD_TO: {
                    path.quadTo(fp.p1.x * widthFactor, fp.p1.y * heightFactor,
                            fp.p2.x * widthFactor, fp.p2.y * heightFactor);
                    lastPoint = fp.p2;
                    break;
                }

                case CLOSE: {
                    path.close();
                }
                default:
                    break;
            }
            //设置下一条Path的偏移量，以便提取下一条命令
            startIndex = startIndex + fp.dataLen + 1;
            fp = nextFrag(svgDataList, startIndex, lastPoint);
        }
        return path;
    }
    //根据偏移量，解析下一条命令，并将命令封装为FragmentPath对象
    private FragmentPath nextFrag(ArrayList<String> svgData, int startIndex,
                                  Point lastPoint) {
        if (svgData == null)
            return null;
        int svgDataSize = svgData.size();
        if (startIndex >= svgDataSize)
            return null;
        // 当前的path片段下标范围[startIndex,i)
        int i = startIndex + 1;
        //保存该命令的长度（指数据长度，不包括命令字母）
        int length = 0;
        FragmentPath fp = new FragmentPath();
        //计算命令的长度
        while (i < svgDataSize) {
            if (svgCommandSet.contains(svgData.get(i)))
                break;
            i++;
            length++;
        }
        //数据长度保存到FragmentPath对象中
        fp.dataLen = length;
        // 根据数据的长度，把各个数据封装到Point对象，并保存到FragmentPath中
        switch (length) {
            case 0: {
                Log.d("", svgData.get(startIndex) + " none data");
                break;
            }
            case 1: {//如果数据只有一个，那么可能是H或V命令，我们需要根据上一次的终端推算x或y坐标
                int d = (int) Float.parseFloat(svgData.get(startIndex + 1));
                if (svgData.get(startIndex).equals("H")) {
                    fp.p1 = new Point(d, lastPoint.y);

                } else {// "V"
                    fp.p1 = new Point(lastPoint.x, d);

                }

                break;
            }
            case 2: {//两个数据，只有一个Point对象（x,y）
                int x = (int) Float.parseFloat(svgData.get(startIndex + 1));
                int y = (int) Float.parseFloat(svgData.get(startIndex + 2));
                fp.p1 = new Point(x, y);

                break;
            }
            case 4: {//4个数据，则封装到两个Point对象中
                int x1 = (int) Float.parseFloat(svgData.get(startIndex + 1));
                int y1 = (int) Float.parseFloat(svgData.get(startIndex + 2));
                int x2 = (int) Float.parseFloat(svgData.get(startIndex + 3));
                int y2 = (int) Float.parseFloat(svgData.get(startIndex + 4));
                fp.p1 = new Point(x1, y1);
                fp.p2 = new Point(x2, y2);

                break;
            }
            case 6: {//6个数据，封装到3个Point对象中
                int x1 = (int) Float.parseFloat(svgData.get(startIndex + 1));
                int y1 = (int) Float.parseFloat(svgData.get(startIndex + 2));
                int x2 = (int) Float.parseFloat(svgData.get(startIndex + 3));
                int y2 = (int) Float.parseFloat(svgData.get(startIndex + 4));
                int x3 = (int) Float.parseFloat(svgData.get(startIndex + 5));
                int y3 = (int) Float.parseFloat(svgData.get(startIndex + 6));
                fp.p1 = new Point(x1, y1);
                fp.p2 = new Point(x2, y2);
                fp.p3 = new Point(x3, y3);

                break;
            }
            default:
                break;
        }
        // 设置当前路径片段的绘制类型
        switch (svgData.get(startIndex)) {
            case "M": {
                fp.pathType = PathType.MOVE;
                break;
            }
            case "H":
            case "V":
            case "L": {
                fp.pathType = PathType.LINE_TO;
                break;
            }

            case "C": {
                fp.pathType = PathType.CURVE_TO;
                break;
            }

            case "Q": {
                fp.pathType = PathType.QUAD_TO;
                break;
            }
            case "Z": {
                fp.pathType = PathType.CLOSE;
                break;
            }

        }
        return fp;
    }
}
