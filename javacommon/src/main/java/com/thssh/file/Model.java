package com.thssh.file;

import java.util.List;

/**
 * Created by hutianhang on 2022/4/2
 */
public class Model {
    public List<Item> data;
    static class Item{

        public String rid;
        public String tid;
        public String raw;

        @Override
        public String toString() {
            return "Item{" +
                    "rid='" + rid + '\'' +
                    ", tid='" + tid + '\'' +
                    ", raw='" + raw + '\'' +
                    '}';
        }
    }
}
