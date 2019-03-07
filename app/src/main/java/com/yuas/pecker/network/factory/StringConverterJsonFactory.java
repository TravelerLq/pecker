package com.yuas.pecker.network.factory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterJsonFactory extends Converter.Factory {



    public StringConverterJsonFactory() {
    }

    public static StringConverterJsonFactory create(){
        return new StringConverterJsonFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringResponseJsonBodyConverter();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new StringRequestJsonConverter();
    }




    class StringResponseJsonBodyConverter implements Converter<ResponseBody, String> {
        public StringResponseJsonBodyConverter() {
        }

        @Override
        public String convert(ResponseBody value) throws IOException {
            String reult = value.string();
            return reult;
        }
    }


    class StringRequestJsonConverter implements  Converter<String,RequestBody>{
        public StringRequestJsonConverter() {
        }

        @Override
        public RequestBody convert(String value) throws IOException {

            return  RequestBody.create(MediaType.parse("application/json"),value);
        }
    }
}
