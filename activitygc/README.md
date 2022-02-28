# Android 回收

## Android11 前

> 系统内存不足时，直接在内核层查杀(回收)进程，并不会考虑回收哪个Activity；
> 进程内存不足时，如果此进程 Activity Task数 >= 3 且 使用内存超过3/4，会对 不可见 Task进行回收，每次回收 1个 Task，回收时机为每次gc；
> 

## 从Android11开始，内存只要达到阈值，就会对所有可回收Activity按照z序依次回收

```java
void releaseSomeActivities(String reason) {
    // Examine all activities currently running in the process.
    // Candidate activities that can be destroyed.
    ArrayList<ActivityRecord> candidates = null;
    if (DEBUG_RELEASE) Slog.d(TAG_RELEASE, "Trying to release some activities in " + this);
    for (int i = 0; i < mActivities.size(); i++) {
        final ActivityRecord r = mActivities.get(i);
        // First, if we find an activity that is in the process of being destroyed,
        // then we just aren't going to do anything for now; we want things to settle
        // down before we try to prune more activities.
        if (r.finishing || r.isState(DESTROYING, DESTROYED)) {
            if (DEBUG_RELEASE) Slog.d(TAG_RELEASE, "Abort release; already destroying: " + r);
            return;
        }
        // Don't consider any activities that are currently not in a state where they
        // can be destroyed.
        if (r.mVisibleRequested || !r.stopped || !r.hasSavedState() || !r.isDestroyable()
                || r.isState(STARTED, RESUMED, PAUSING, PAUSED, STOPPING)) {
            if (DEBUG_RELEASE) Slog.d(TAG_RELEASE, "Not releasing in-use activity: " + r);
            continue;
        }

        if (r.getParent() != null) {
            if (candidates == null) {
                candidates = new ArrayList<>();
            }
            candidates.add(r);
        }
    }

    if (candidates != null) {
        // Sort based on z-order in hierarchy.
        candidates.sort(WindowContainer::compareTo);
        // Release some older activities
        int maxRelease = Math.max(candidates.size(), 1);
        do {
            final ActivityRecord r = candidates.remove(0);
            if (DEBUG_RELEASE) Slog.v(TAG_RELEASE, "Destroying " + r
                    + " in state " + r.getState() + " for reason " + reason);
            r.destroyImmediately(reason);
            --maxRelease;
        } while (maxRelease > 0);
    }
}
```

```shell
[START]

D/Proj2022: MainActivity@249594678 onCreate 
D/Proj2022: MainActivity@249594678 onStart 
D/Proj2022: MainActivity@249594678 onResume 

start transition

D/Proj2022: MainActivity@249594678 onPause 
D/Proj2022: TransitionActivity@222898322 onCreate 
D/Proj2022: TransitionActivity@222898322 onStart 
D/Proj2022: TransitionActivity@222898322 onResume 
D/Proj2022: MainActivity@249594678 onStop 

start transiton

D/Proj2022: TransitionActivity@222898322 onPause 
D/Proj2022: TransitionActivity@258734935 onCreate 
D/Proj2022: TransitionActivity@258734935 onStart 
D/Proj2022: TransitionActivity@258734935 onResume 
D/Proj2022: TransitionActivity@222898322 onStop 

start singleinstance

D/Proj2022: TransitionActivity@258734935 onPause 
D/Proj2022: SingleInstanceActivity@107529199 onCreate 
D/Proj2022: SingleInstanceActivity@107529199 onStart 
D/Proj2022: SingleInstanceActivity@107529199 onResume 
D/Proj2022: TransitionActivity@258734935 onStop 

start transtion

D/Proj2022: SingleInstanceActivity@107529199 onPause 
D/Proj2022: TransitionActivity@115570620 onCreate 
D/Proj2022: TransitionActivity@115570620 onStart 
D/Proj2022: TransitionActivity@115570620 onResume 
D/Proj2022: SingleInstanceActivity@107529199 onStop 

start allocmem

D/Proj2022: TransitionActivity@115570620 onPause 
D/Proj2022: MemAllocActivity@39322246 onCreate 
D/Proj2022: MemAllocActivity@39322246 onStart 
D/Proj2022: MemAllocActivity@39322246 onResume 
D/Proj2022: TransitionActivity@115570620 onStop 

alloc * N

D/Proj2022: SingleInstanceActivity@107529199 onDestroy 
D/Proj2022: MainActivity@249594678 onDestroy 
D/Proj2022: TransitionActivity@222898322 onDestroy 
D/Proj2022: TransitionActivity@258734935 onDestroy 
D/Proj2022: TransitionActivity@115570620 onDestroy 


[oom]recreate
D/Proj2022: TransitionActivity@17589156 onCreate 
D/Proj2022: TransitionActivity@17589156 onStart 
D/Proj2022: TransitionActivity@17589156 onResume 

back

D/Proj2022: TransitionActivity@17589156 onPause 
D/Proj2022: TransitionActivity@117161152 onCreate 
D/Proj2022: TransitionActivity@117161152 onStart 
D/Proj2022: TransitionActivity@117161152 onResume 
D/Proj2022: TransitionActivity@17589156 onStop 
D/Proj2022: TransitionActivity@17589156 onDestroy 

back

D/Proj2022: TransitionActivity@117161152 onPause 
D/Proj2022: TransitionActivity@10505782 onCreate 
D/Proj2022: TransitionActivity@10505782 onStart 
D/Proj2022: TransitionActivity@10505782 onResume 
D/Proj2022: TransitionActivity@117161152 onStop 
D/Proj2022: TransitionActivity@117161152 onDestroy 

back

D/Proj2022: TransitionActivity@10505782 onPause 
D/Proj2022: MainActivity@41248115 onCreate 
D/Proj2022: MainActivity@41248115 onStart 
D/Proj2022: MainActivity@41248115 onResume 
D/Proj2022: TransitionActivity@10505782 onStop 
D/Proj2022: TransitionActivity@10505782 onDestroy 

back

D/Proj2022: SingleInstanceActivity@258787568 onCreate 
D/Proj2022: SingleInstanceActivity@258787568 onStart 
D/Proj2022: SingleInstanceActivity@258787568 onResume 
D/Proj2022: MainActivity@41248115 onPause 
D/Proj2022: MainActivity@41248115 onStop 

back

D/Proj2022: SingleInstanceActivity@258787568 onPause 
D/Proj2022: MainActivity@41248115 onDestroy 
D/Proj2022: SingleInstanceActivity@258787568 onStop 
D/Proj2022: SingleInstanceActivity@258787568 onDestroy 

[END]
```
