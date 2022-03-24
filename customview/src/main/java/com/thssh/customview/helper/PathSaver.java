package com.thssh.customview.helper;

import android.graphics.Path;

/**
 * @author hutianhang
 */
public class PathSaver implements IPaintAbility {

    private final Path path;
    private final PathInfo pathInfo;
    private final IRepo<PathInfo> repo;

    public PathSaver(IRepo<PathInfo> repo) {
        this.repo = repo;
        pathInfo = repo.restore();
        path = pathInfo.toPath();
    }

    public Path getPath() {
        return path;
    }

    public void save() {
        repo.save(pathInfo);
    }

    @Override
    public void moveTo(float x, float y) {
        path.moveTo(x, y);
        pathInfo.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        path.lineTo(x, y);
        pathInfo.lineTo(x, y);
    }

    @Override
    public void reset() {
        path.reset();
        pathInfo.reset();
    }
}
