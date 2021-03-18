package com.coderlong.jvm3;

import java.util.HashSet;
import java.util.Set;

/**
 * VM Args: -XX:PermSize=6m -XX:MaxPermSize=6m(JDK6有效)
 *          -xx:MaxMetaspaceSize(JDK7+设置上面的参数或者设置此参数都无效，因为运行时常量池数据移入堆内存，需要设置-Xmx才有效果)
 * @author LongQiong
 * @date 2021/2/25
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        //使用Set保持着常量池引用，避免Full GC回收常量池行为
        Set<String> set = new HashSet<>();
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
