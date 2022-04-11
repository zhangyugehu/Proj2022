package com.thssh.commonlib.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentGetter {
    interface Getter {
        NamedFragment get();
    }

    final List<Getter> getters;
    final Random r;

    public FragmentGetter(int size) {
        getters = new ArrayList<>();
        r = new Random();
        for (int i = 0; i < size; i++) {
            getters.add(() -> NamedFragment.newInstance("F" + r.nextInt(size), r.nextInt()));
        }
    }

    public NamedFragment getFragment(int position) {
        return getters.get(position).get();
    }

    public int getSize() {
        return getters.size();
    }
}
