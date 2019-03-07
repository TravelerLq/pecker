package com.yuas.pecker.network.factory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterFactory extends Converter.Factory {


    public static StringConverterFactory create(){
        return  new StringConverterFactory();
    }

    public StringConverterFactory() {

    }

    //重写方法
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new StringRequestBodyConvert();
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringResponseBodyConver();
    }


    //respnse
    class StringResponseBodyConver implements Converter<ResponseBody, String> {

        @Override
        public String convert(ResponseBody value) throws IOException {

            String result = value.string();
            return result;
        }
    }


    class StringRequestBodyConvert implements Converter<String, RequestBody> {

        public StringRequestBodyConvert() {
        }

        @Override
        public RequestBody convert(String value) throws IOException {

            return RequestBody.create(MediaType.parse("text/plain"), value);

        }
    }
}
