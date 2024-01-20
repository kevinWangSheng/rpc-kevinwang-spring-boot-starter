package cn.kevinwang.rpc.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wang
 * @create 2024-01-20-18:04
 */
public class ClassLoaderUtil {
    private static Set<Class> primitiveSet = new HashSet<Class>();

    static {
        primitiveSet.add(Integer.class);
        primitiveSet.add(Long.class);
        primitiveSet.add(Float.class);
        primitiveSet.add(Byte.class);
        primitiveSet.add(Short.class);
        primitiveSet.add(Double.class);
        primitiveSet.add(Character.class);
        primitiveSet.add(Boolean.class);
    }

    public static Class forName(String className)
            throws ClassNotFoundException {
        return forName(className, true);
    }

    /**
     * 根据类名加载Class
     *
     * @param className
     *            类名
     * @param initialize
     *            是否初始化
     * @return Class
     * @throws ClassNotFoundException
     *             找不到类
     */
    public static Class forName(String className, boolean initialize)
            throws ClassNotFoundException {
        return Class.forName(className, initialize, getCurrentClassLoader());
    }

    public static ClassLoader getCurrentClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoaderUtil.class.getClassLoader();
        }
        return cl == null ? ClassLoader.getSystemClassLoader() : cl;
    }
}
