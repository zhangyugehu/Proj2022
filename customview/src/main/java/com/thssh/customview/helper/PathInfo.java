package com.thssh.customview.helper;

import android.graphics.Path;
import android.graphics.PointF;

import java.util.LinkedList;

public class PathInfo implements IPaintAbility {

    private static final String MOVE_TO = "m";
    private static final String LINE_TO = "l";
    private static final String SPLIT_POINT = ",";
    private static final String SPLIT = ";";

    private final StringBuilder info;

    public PathInfo() {
        this("");
    }

    public PathInfo(String stringify) {
        info = new StringBuilder(stringify);
    }

    @Override
    public void moveTo(float x, float y) {
        info.append(MOVE_TO).append(x).append(SPLIT_POINT).append(y).append(SPLIT);
    }

    @Override
    public void lineTo(float x, float y) {
        info.append(LINE_TO).append(x).append(SPLIT_POINT).append(y).append(SPLIT);
    }

    @Override
    public void reset() {
        info.delete(0, info.length());
    }

    public Path toPath() {
        Path path = new Path();
        if (info.length() > 3) {
            String sInfo = info.substring(0, info.length() - 1);
            String[] pInfos = sInfo.split(SPLIT);
            for (String pInfo : pInfos) {
                if (pInfo.startsWith(MOVE_TO)) {
                    PointF p = toPoint(pInfo.substring(1));
                    path.moveTo(p.x, p.y);
                } else if (pInfo.startsWith(LINE_TO)) {
                    PointF p = toPoint(pInfo.substring(1));
                    path.lineTo(p.x, p.y);
                } else {
                    throw new IllegalStateException("NotSupported " + pInfo);
                }
            }
        }
        return path;
    }

    private PointF toPoint(String stringifyPoint) {
        String[] point = stringifyPoint.split(SPLIT_POINT);
        return new PointF(Float.parseFloat(point[0]), Float.parseFloat(point[1]));
    }

    public String stringify() {
        return info.toString();
    }
}
