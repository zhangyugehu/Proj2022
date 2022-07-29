package com.tsh.chart;

public interface PercentData {
    float getPercent();

    class Impl implements PercentData {
        public float percent;

        public Impl(float percent) {
            this.percent = percent;
        }

        @Override
        public float getPercent() {
            return percent;
        }
    }
}
