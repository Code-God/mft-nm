package com.meifute.core.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wzeng
 * @date 2020/8/4
 * @Description
 */
public class MftClass {

    public  static <T> T createInstance(String className) throws ClassNotFoundException, MftException {
        Class<T> clz = (Class<T>) Class.forName(className);
        Constructor<?>[] constructors= clz.getDeclaredConstructors();
        for(Constructor cons:constructors){
            if(cons.getParameterCount() ==0){
                try {
                    return (T) cons.newInstance(new Object[0]);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                   throw new MftException(e.getMessage(),e);
                }
            }
        }
        throw new MftException("Not found constructor with none parameters");
    }

    /**
     * 判断第二个参数的对象，是否可以向第一个参数类型赋值
     * 比如，子类型对象可以向父类型或接口类型赋值
     * 可自动装箱类型和基本类型之间可以相互赋值。
     * @param argumentType
     * @param inputParamType
     * @return
     */
    public static boolean isAssignableFromSecond(Class<?> argumentType, Class<?> inputParamType) throws MftException {
        if(argumentType == inputParamType){
           return true;
        } else{
            if(argumentType.isAssignableFrom(inputParamType)){
                return true;
            } else{
                if(MftType.isBoxedType(inputParamType) || inputParamType.isPrimitive()){
                    Class<?> changedType=MftType.boxableClassSwitch(inputParamType);
                    if(changedType == argumentType){
                        return true;
                    } else{
                       return false;
                    }
                } else{
                    return false;
                }
            }
        }
    }

    /**
     * 依据参数确定构造函数，并使用该构造函数生成一个新的对象。
     * @param className
     * @param exactlyMatch true 表示 输入的数据和对应的构造函数参数的类型必须精确一致， false:输入参数能给构造函数对应接口赋值即可。
     * @param parameters
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
   public static <T> T createInstance(String className,boolean exactlyMatch,Object... parameters) throws ClassNotFoundException, NoSuchMethodException, MftException {
       Class<T> clz = (Class<T>) Class.forName(className);
       Class<?>[] paramsTypes = new Class[parameters.length];

       Class<?>cz=int.class;

       for(int i=0;i<parameters.length;i++){
           paramsTypes[i] = parameters[i].getClass();
       }
       Constructor constructor =null;
       try {
           constructor = clz.getDeclaredConstructor(paramsTypes);
       }catch(NoSuchMethodException e) {
           if(exactlyMatch){
               throw e;
           }
           Constructor<T>[] constructors = (Constructor<T>[]) clz.getDeclaredConstructors();
           for(Constructor<T> c:constructors){
               if(c.getParameterCount()==parameters.length){
                   boolean match = true;
                   for(int i=0;i<parameters.length;i++){
                       Class<?> constructorParamType=c.getParameterTypes()[i];
                       Class<?> inputParamType=paramsTypes[i];
                       boolean isAssignableFrom = isAssignableFromSecond(constructorParamType,inputParamType);
                       if(isAssignableFrom){
                          continue;
                       } else{
                           match =false;
                           break;
                       }
                   }
                   if(match){
                       constructor = c;
                   }
               }
           }
       }

       if(constructor==null){
           throw new NoSuchMethodException();
       }

       try {
           return (T) constructor.newInstance(parameters);
       } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
           throw new MftException(e.getMessage(),e);
       }
   }

   public static List<Field> getAllFields(Class clz){
       List<Field> allField=new LinkedList<>();
       Field[] declaredFields= clz.getDeclaredFields();
       for(Field field: declaredFields){
           allField.add(field);
       }

       Class superClz=clz.getSuperclass();
       if(superClz!=null){
           allField.addAll(getAllFields(superClz));
       }
       return allField;
   }

   public static <T> T getFieldValue(Object object,Field field) throws IllegalAccessException {
       Objects.requireNonNull(field);
       Objects.requireNonNull(object);
       field.setAccessible(true);
       return (T)field.get(object);
   }

   public static void setFieldValue(Object object,Field field,Object value) throws IllegalAccessException {
       Objects.requireNonNull(field);
       Objects.requireNonNull(object);
       field.setAccessible(true);
       field.set(object,value);
   }

    /**
     *
     * @param clz
     * @param fieldName
     * @param fieldType 该field 的类型，可为空， 如果设置的话，将严格比对类型， 基本类型将无法匹配。
     * @return
     */
   public static Optional<Field> getFieldByFieldName(Class clz,String fieldName,Class fieldType){
       Objects.requireNonNull(clz);
       Objects.requireNonNull(fieldName);
       Field[] declaredFields= clz.getDeclaredFields();
       for(Field field: declaredFields){
           if(field.getName().equals(fieldName)){
               if(fieldType!=null) {
                   if (fieldType.equals(field.getType())) {
                       return Optional.of(field);
                   } else{
                        continue;
                   }
               } else{
                   return Optional.of(field);
               }
           }
       }

       Class superClz=clz.getSuperclass();
       if(superClz!=null){
           return getFieldByFieldName(superClz,fieldName,fieldType);
       } else{
           return Optional.empty();
       }

   }
}
