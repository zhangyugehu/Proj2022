package com.tsh.chart;

public class Mapping {
    float srcMin;
    float srcMax;
    float destMin;
    float destMax;

    public void setSrc(float min, float max) {
        this.srcMin = min;
        this.srcMax = max;
    }

    public void setDest(float min, float max) {
        this.destMin = min;
        this.destMax = max;
    }

    public float mapValue(float srcValue) {
        return destMin + ((destMax - destMin) / (srcMax - srcMin)) * (srcValue - srcMin);
    }

    public float remapValue(float destValue) {
        return srcMin + ((srcMax - srcMin) / (destMax - destMin)) * (destValue - destMin);
    }
}
