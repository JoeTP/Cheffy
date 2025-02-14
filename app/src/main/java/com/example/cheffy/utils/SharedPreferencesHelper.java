import android.content.Context;
import android.content.SharedPreferences;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "MyAppPreferences";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Save a String value to SharedPreferences
    public Completable saveString(String key, String value) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }).subscribeOn(Schedulers.io());
    }

    // Retrieve a String value from SharedPreferences
    public Single<String> getString(String key, String defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getString(key, defaultValue))
                .subscribeOn(Schedulers.io());
    }

    // Save an Integer value to SharedPreferences
    public Completable saveInt(String key, int value) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.apply();
        }).subscribeOn(Schedulers.io());
    }

    // Retrieve an Integer value from SharedPreferences
    public Single<Integer> getInt(String key, int defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getInt(key, defaultValue))
                .subscribeOn(Schedulers.io());
    }

    // Save a Boolean value to SharedPreferences
    public Completable saveBoolean(String key, boolean value) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }).subscribeOn(Schedulers.io());
    }

    // Retrieve a Boolean value from SharedPreferences
    public Single<Boolean> getBoolean(String key, boolean defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getBoolean(key, defaultValue))
                .subscribeOn(Schedulers.io());
    }

    // Clear a specific key from SharedPreferences
    public Completable clearKey(String key) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }).subscribeOn(Schedulers.io());
    }

    // Clear all data from SharedPreferences
    public Completable clearAll() {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }).subscribeOn(Schedulers.io());
    }
}