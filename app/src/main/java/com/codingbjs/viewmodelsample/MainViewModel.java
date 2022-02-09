package com.codingbjs.viewmodelsample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    private AppDatabase db;

    ExecutorService executorService;

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class, "todo_db").build();
        executorService = Executors.newFixedThreadPool(4);
    }

    public LiveData<List<Todo>> getAll(){
        return db.todoDao().getAll();
    }

    public void insert (Todo todo) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.todoDao().insert(todo);
            }
        });
    }


}
