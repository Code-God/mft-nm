package com.meifute.core.lang;

/**
 * @author wzeng
 * @date 2020/8/5
 * @Description
 */
public class MftType {

    /**
     * 对自动装箱的类类型进行转化， 如果不是自动装箱的类型，返回原输入值。
     * @param autoBoxType
     * @return   如果是装箱类型则返回基本类型，如果是基本类型则返回装箱类型。
     */
    public static Class<?> boxableClassSwitch(Class<?> autoBoxType) throws MftException {

        if(autoBoxType.isPrimitive()){
            return primitiveToBoxedType(autoBoxType);
        } else if(isBoxedType(autoBoxType)){
            return boxedTypeToPrimitive(autoBoxType);
        } else{
            return autoBoxType;
        }
    }

    /**
     * 基本类类型转换为装箱类型
     * @param primitiveType 基本类类型，如果不是，则抛出{@link MftException}
     * @return
     */
    public static Class<?> primitiveToBoxedType(Class<?> primitiveType) throws MftException {
        if(primitiveType==boolean.class){
            return Boolean.class;
        } else if(primitiveType == byte.class){
            return Byte.class;
        }else if(primitiveType == char.class){
            return Character.class;
        }else if(primitiveType == double.class){
            return Double.class;
        }else if(primitiveType == float.class){
            return Float.class;
        }else if(primitiveType == int.class){
            return Integer.class;
        }else if(primitiveType == long.class){
            return Long.class;
        }else if(primitiveType == short.class){
            return Short.class;
        }else if(primitiveType == void.class){
            return Void.class;
        }
        throw new MftException(primitiveType.getName()+" is not primitive");
    }

    /**
     * 判断是否为装箱类型
     * @param clz
     * @return true 表示装箱类型
     */
    public static boolean isBoxedType(Class<?> clz){
        if(clz == Boolean.class){
            return true;
        } else if(clz == Byte.class){
            return true;
        }else if(clz == Character.class){
            return true;
        } else if(clz == Double.class){
            return true;
        }else if(clz == Float.class){
            return true;
        } else if(clz == Integer.class){
            return true;
        }else if(clz == Long.class){
            return true;
        } else if(clz == Short.class) {
            return true;
        } else if(clz == Void.class){
            return true;
        } else{
            return false;
        }
    }

    /**
     * 装箱类型转换为基本类型
     * @param autoBoxType
     * @return
     */
    public static Class<?> boxedTypeToPrimitive(Class<?> autoBoxType) throws MftException {
        if(autoBoxType == Boolean.class){
           return boolean.class;
       } else if(autoBoxType == Byte.class){
           return byte.class;
       }else if(autoBoxType == Character.class){
           return char.class;
       } else if(autoBoxType == Double.class){
           return double.class;
       }else if(autoBoxType == Float.class){
           return float.class;
       } else if(autoBoxType == Integer.class){
           return int.class;
       }else if(autoBoxType == Long.class){
           return long.class;
       } else if(autoBoxType == Short.class) {
            return short.class;
        } else if(autoBoxType == Void.class){
           return void.class;
       }
        throw new MftException(autoBoxType.getName()+" is not boxed type");
    }

}
