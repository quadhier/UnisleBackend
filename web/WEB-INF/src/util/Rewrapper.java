package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rewrapper {
    private Rewrapper(){}

    public static Map wrap(Object original,String selectValues)
            throws Exception{
        int numOfFields = original.getClass().getDeclaredFields().length;
        if(selectValues.length()!=numOfFields)
            return null;
        char[] selectField = selectValues.toCharArray();
        for(char c:selectField)
            if(c!='1' && c!='0')
                return null;

        Map<String,Object> result = new HashMap<String,Object>();
        Field[] fields = original.getClass().getDeclaredFields();
        for(int i=0;i<numOfFields;i++){
            if (selectField[i] == '1'){
                String fieldName = fields[i].getName();
                String fieldGetter = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                Method method = original.getClass().getMethod(fieldGetter);
                result.put(fieldName,method.invoke(original));
            }
        }

        return result;
    }

    public static List wrapList(List originalList,Class originalClass,String selectValues)
            throws Exception{
        if(originalList==null || originalList.isEmpty())
            return null;

        int numOfFields = originalClass.getDeclaredFields().length;
        if(selectValues.length()!=numOfFields)
            return null;

        char[] selectField = selectValues.toCharArray();
        for(char c:selectField)
            if(c!='1' && c!='0')
                return null;
        Field[] fields = originalClass.getDeclaredFields();
        Map<String,Object> map = null;
        List resultList = new ArrayList();
        for(Object o : originalList){
            map = new HashMap<String,Object>();
            for(int i=0;i<numOfFields;i++){
                if (selectField[i] == '1'){
                    String fieldName = fields[i].getName();
                    String fieldGetter = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                    Method method = originalClass.getMethod(fieldGetter);

                    System.out.println(fieldName);
                    System.out.println(o);
                    System.out.println(method.invoke(o));
                    System.out.println(map);
                    map.put(fieldName, method.invoke(o));
                }
            }
            resultList.add(map);
        }

        return resultList;
    }
}