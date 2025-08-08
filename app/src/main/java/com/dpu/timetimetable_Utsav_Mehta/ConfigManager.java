package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final String TAG = "ConfigManager";
    private static ConfigManager instance;
    private Properties config;
    private Context context;

    private ConfigManager(Context context) {
        this.context = context.getApplicationContext();
        loadConfig();
    }

    public static synchronized ConfigManager getInstance(Context context) {
        if (instance == null) {
            instance = new ConfigManager(context);
        }
        return instance;
    }

    private void loadConfig() {
        try {
            config = new Properties();
            InputStream inputStream = context.getAssets().open("config.properties");
            config.load(inputStream);
            inputStream.close();
            Log.d(TAG, "Configuration loaded successfully");
        } catch (IOException e) {
            Log.e(TAG, "Error loading configuration: " + e.getMessage());
            config = new Properties();
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error loading configuration: " + e.getMessage());
            config = new Properties();
        }
    }

    public String getMongoDBUri() {
        return config.getProperty("MONGODB_URI", "");
    }

    public String getDatabaseName() {
        return config.getProperty("DB_NAME", "timetable_db");
    }

    public String getLecturesCollection() {
        return config.getProperty("COLLECTION_LECTURES", "lectures");
    }

    public String getTeachersCollection() {
        return config.getProperty("COLLECTION_TEACHERS", "teachers");
    }

    public String getApiBaseUrl() {
        return config.getProperty("API_BASE_URL", "");
    }

    public int getApiTimeout() {
        return Integer.parseInt(config.getProperty("API_TIMEOUT", "30000"));
    }

    public String getEncryptionKey() {
        return config.getProperty("ENCRYPTION_KEY", "");
    }

    public boolean isDebugMode() {
        return Boolean.parseBoolean(config.getProperty("DEBUG_MODE", "true"));
    }

    public String getLogLevel() {
        return config.getProperty("LOG_LEVEL", "DEBUG");
    }

    public boolean isMongoDBConfigured() {
        String uri = getMongoDBUri();
        return !uri.isEmpty() && !uri.equals("your_mongodb_connection_string_here");
    }
}
