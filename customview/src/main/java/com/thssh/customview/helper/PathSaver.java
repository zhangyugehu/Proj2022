package com.thssh.customview.helper;

import android.graphics.Path;

/**
 * @author hutianhang
 */
public class PathSaver implements IPaintAbility {

    private final PathInfo pathInfo;
    private final IRepo<PathInfo> repo;

    public PathSaver(IRepo<PathInfo> repo) {
        this.repo = repo;
        pathInfo = repo.restore();
    }

    public PathInfo getPathInfo() {
        return pathInfo;
    }

    public void save() {
        repo.save(pathInfo);
    }

    @Override
    public void moveTo(float x, float y) {
        pathInfo.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        pathInfo.lineTo(x, y);
    }

    @Override
    public void reset() {
        pathInfo.reset();
    }

    @Override
    public void setColor(int color) {
        pathInfo.setColor(color);
    }
}
