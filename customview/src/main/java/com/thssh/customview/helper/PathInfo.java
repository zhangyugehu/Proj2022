package com.thssh.customview.helper;

import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hutianhang
 */
public class PathInfo implements IPaintAbility {

    public static class Info {
        public Info(Path path, int color) {
            this.path = path;
            this.color = color;
        }

        public Path path;
        public int color;

    }

    private static final String MOVE_TO = "m";
    private static final String COLOR = "c";
    private static final String LINE_TO = "l";
    private static final String SPLIT_POINT = ",";
    private static final String SPLIT = ";";

    private final StringBuilder info;
    public final LinkedList<Info> infos;
    public int color;

    public PathInfo() {
        this("");
    }

    public PathInfo(String stringify) {
        info = new StringBuilder(stringify);
        infos = toPathInfo();
    }

    @Override
    public void moveTo(float x, float y) {
        info.append(MOVE_TO).append(x).append(SPLIT_POINT).append(y).append(COLOR).append(color).append(SPLIT);
        infos.add(new Info(new Path(), color));
        infos.getLast().path.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        info.append(LINE_TO).append(x).append(SPLIT_POINT).append(y).append(SPLIT);
        infos.getLast().path.lineTo(x, y);
    }

    @Override
    public void reset() {
        info.delete(0, info.length());
        for (Info value : infos) {
            value.path.reset();
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    private LinkedList<Info> toPathInfo() {
        LinkedList<Info> infos = new LinkedList<>();
        if (info.length() > 3) {
            String sInfo = info.substring(0, info.length() - 1);
            String[] pInfos = sInfo.split(SPLIT);
            for (String pInfo : pInfos) {
                if (pInfo.startsWith(MOVE_TO)) {
                    int cIdx = pInfo.lastIndexOf(COLOR);
                    String[] point = pInfo.substring(1, cIdx).split(SPLIT_POINT);
                    PointF p = new PointF(Float.parseFloat(point[0]), Float.parseFloat(point[1]));
                    int color = Integer.parseInt(pInfo.substring(cIdx + 1));
                    infos.add(new Info(new Path(), color));
                    infos.getLast().path.moveTo(p.x, p.y);
                } else if (pInfo.startsWith(LINE_TO)) {
                    PointF p = toPoint(pInfo.substring(1));
                    infos.getLast().path.lineTo(p.x, p.y);
                } else {
                    throw new IllegalStateException("NotSupported " + pInfo);
                }
            }
        }
        return infos;
    }

    private PointF toPoint(String stringifyPoint) {
        String[] point = stringifyPoint.split(SPLIT_POINT);
        return new PointF(Float.parseFloat(point[0]), Float.parseFloat(point[1]));
    }

    public String stringify() {
        return info.toString();
    }
}
