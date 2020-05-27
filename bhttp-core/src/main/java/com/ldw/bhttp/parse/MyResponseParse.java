package com.ldw.bhttp.parse;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @date 2020/5/23 11:35
 */
public class MyResponseParse extends AbstractParse<MyResponse> {


    public <D> D onParse(String response, Class<D> tClass,Class<?> responseClass) {
        //            MyResponse myResponse = (MyResponse) new Gson().fromJson(response.body().string(), tClass);
        Type type = new TypeToken<MyResponse<D>>(){}.getType();
        MyResponse<D> d =  new Gson().fromJson(response,type);
        return (D) d;
        //MyResponse myResponse = new Gson().fromJson(response,MyResponse.class);
        //System.out.println(tClass.getClass().toString()+"XXXXXXXXXXXX");
       // String s = new Gson().toJson(myResponse.data);
       // System.out.println(s+"XXXXXXXXXXXXXXXXXXXXXXMyResponseParse");
      //  D d = (D) convert(response, tClass);

    }

    @Override
    public <T, D> T onParse(String response, Class<D> tClass) {
        try {
            Type type = new TypeToken<MyResponse<D>>(){}.getType();
            MyResponse<D> d =  new Gson().fromJson(response,type);
            return (T) d;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage()+"XXXXXXXX");
        }
        return null;
    }

    @Override
    public Class<MyResponse> getTClass() {
        return MyResponse.class;
    }
}
