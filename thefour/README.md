# Android 四大组件


## Activity

### A -> B -> A

```shell
>> start A
Proj2022: FirstActivity@71059060 onCreate 
Proj2022: FirstActivity@71059060 onStart 
Proj2022: FirstActivity@71059060 onResume 
        
>> start B
Proj2022: FirstActivity@71059060 onPause 
Proj2022: SecondActivity@210453493 onCreate 
Proj2022: SecondActivity@210453493 onStart 
Proj2022: SecondActivity@210453493 onResume <<<
Proj2022: FirstActivity@71059060 onStop <<<

>> back A
Proj2022: SecondActivity@210453493 onPause 
Proj2022: FirstActivity@71059060 onRestart 
Proj2022: FirstActivity@71059060 onStart 
Proj2022: FirstActivity@71059060 onResume <<<
Proj2022: SecondActivity@210453493 onStop <<<
Proj2022: SecondActivity@210453493 onDestroy  <<<

```
