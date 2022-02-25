package com.thssh.base;

public class ClassDemo implements IClass {
    {
        System.out.println("[SimpleClass init");
    }
    static {
        System.out.println("[SimpleClass static init");
    }
    private final String memberFiled = "memberFiled";

    @Override
    public void introduce() {
        System.out.println("SimpleClass. " + memberFiled);
        new InnerClass().introduce();
        new StaticInnerClass().introduce();
    }

    public class InnerClass implements IClass {
        {
            System.out.println("[InnerClass init");
        }

        @Override
        public void introduce() {
            System.out.println("InnerClass. " + memberFiled);
        }
    }

    static class StaticInnerClass implements IClass {
        {
            System.out.println("[StaticInnerClass init");
        }
        static {
            System.out.println("[StaticInnerClass static init");
        }

        @Override
        public void introduce() {
            System.out.println("StaticInnerClass. "
                    // Non-static field 'memberFiled' cannot be referenced from a static context
                    // + memberFiled
            );
        }
    }

    public static void main(String[] args) {
        String filed = "filed";
        class FunctionClass implements IClass {
            {
                System.out.println("[FunctionClass init");
            }

            @Override
            public void introduce() {
                System.out.println("FunctionClass. "
                        // Non-static field 'memberFiled' cannot be referenced from a static context
                        // + memberFiled
                        + filed
                );
            }
        }

        new ClassDemo().introduce();
        new FunctionClass().introduce();
        new IClass() {

            @Override
            public void introduce() {
                System.out.println("anyones class. " + filed);
            }
        };
    }
}
