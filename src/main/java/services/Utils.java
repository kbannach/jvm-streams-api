package services;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public enum Utils {
   ;

   public static boolean compareNullSafe(Object left, Object right) {
      if (left == null) {
         return right == null;
      } else {
         return left.equals(right);
      }
   }

   public static Object getFieldValue(Field field, Object obj) {
      try {
         Method getter = getGetter(field);
         if (getter == null) {
            return field.get(obj);
         } else {
            return getter.invoke(obj);
         }
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
         throw new RuntimeException(e);
      }
   }

   // Source: http://stackoverflow.com/a/2638662/2757140
   private static Method getGetter(Field field) {
      try {
         Class< ? > type = field.getDeclaringClass();
         return new PropertyDescriptor(field.getName(), type).getReadMethod();
      } catch (IntrospectionException e) {
         return null;
      }
   }
}
