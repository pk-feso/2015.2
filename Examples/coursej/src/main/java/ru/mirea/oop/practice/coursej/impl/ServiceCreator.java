package ru.mirea.oop.practice.coursej.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import retrofit.Retrofit;
import ru.mirea.oop.practice.coursej.api.vk.ArrayDeserializer;
import ru.mirea.oop.practice.coursej.api.vk.entities.Audio;
import ru.mirea.oop.practice.coursej.api.vk.entities.Document;

public final class ServiceCreator {
    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Document[].class, new ArrayDeserializer<>(Document.class, Document[]::new))
            .registerTypeAdapter(Audio[].class, new ArrayDeserializer<>(Audio.class, Audio[]::new))
            .setPrettyPrinting().create();

    private ServiceCreator() {
    }

    public static <S> S createService(OkHttpClient ok, Class<S> serviceClass, String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(CustomConverterFactory.create(gson))
                .client(ok);
        Retrofit adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
