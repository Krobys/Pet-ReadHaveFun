package com.akrivonos.a2chparser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akrivonos.a2chparser.pojomodels.Thread;
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText boardNameET;
    private EditText threadNameET;
    private Button findBoardB;
    private Button findThreadB;
    private TextView threadsText;
    private Observer<List<Thread>> observer = new Observer<List<Thread>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Thread> threads) {
            for (Thread thread : threads){
                threadsText.append(thread.getComment());
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardNameET = findViewById(R.id.edit_Text_Board_Name);
        threadNameET = findViewById(R.id.editText_find_thread);
        findBoardB = findViewById(R.id.button_load_threads);
        findBoardB.setOnClickListener(this);
        findThreadB = findViewById(R.id.button_find_thread);
        threadsText = findViewById(R.id.threadsTextView);

        RetrofitSearchDvach.getInstance().setObserverBeerNames(observer);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_load_threads:
                String boardName = boardNameET.getText().toString();
                RetrofitSearchDvach.getInstance().getThreadsForBoard(boardName);
                break;
            case R.id.button_find_thread:
                break;
        }
    }
}
