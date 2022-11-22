package com.example.assignment3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        QuestionAmountManager.DialogClickListener {
    int track, userScore, questionsAmount, questionsAmount_;
    AlertDialog.Builder create, prompter;
    ArrayList<QuestionFragment> bankArr;
    ProgressBar progressBar;
    Button btn_SelectTrue, btn_SelectFalse;
    FileSystem fileSystem;
    FragmentManager manageFrag = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int defaultVal = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_SelectTrue = findViewById(R.id.btn_True);
        btn_SelectTrue.setOnClickListener(this);

        btn_SelectFalse = findViewById(R.id.btn_False);
        btn_SelectFalse.setOnClickListener(this);

        progressBar = findViewById(R.id.simpleProgressBar);
        fileSystem = ((Manager) getApplication()).fileSystem;
        questionsAmount = ((Manager) getApplication()).questionBank.size();
        create = new AlertDialog.Builder(this);
        prompter = new AlertDialog.Builder(this);

        if (savedInstanceState != null) {
            track = savedInstanceState.getInt("");
            userScore = savedInstanceState.getInt("");
            questionsAmount_ = savedInstanceState.getInt("");
            bankArr = ((Manager) getApplication()).manager.questionFragManager;

            if (questionsAmount_ > track) {
                manageFrag.beginTransaction().replace(R.id.fragContainerView,
                        bankArr.get(track)).commit();
                progressBar.setMax(questionsAmount_);
                progressBar.setProgress(track);
            } else {
                triviaPrompter();
            }
        } else {
            track = defaultVal;
            userScore = defaultVal;
            int defaultNumberOfQuestions = ((Manager) getApplication()).questionBank.size();
            bankArr = createQuiz(defaultNumberOfQuestions);
            questionsAmount_ = defaultNumberOfQuestions;

            manageFrag.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            manageFrag.beginTransaction()
                    .add(R.id.fragContainerView, bankArr.get(defaultVal)).commit();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int identify = view.getId();

        switch (identify) {
            case R.id.btn_False:
                if (!bankArr.get(track).answer) {
                    track++;
                    userScore++;
                    Toast.makeText(this,
                            getResources().getString
                                    (R.string.isCorrect), Toast.LENGTH_SHORT).show();
                } else {
                    track++;
                    Toast.makeText(this,
                            getResources().getString
                                    (R.string.isWrong), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_True:
                if (bankArr.get(track).answer) {
                    track++;
                    userScore++;
                    Toast.makeText(this, getResources().getString(R.string.isCorrect), Toast.LENGTH_SHORT).show();
                } else {
                    track++;
                    Toast.makeText(this, getResources().getString(R.string.isWrong), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        progressBar.setProgress(track);

        if (bankArr.size() > track) {
            manageFrag.beginTransaction().replace(R.id.fragContainerView,
                    bankArr.get(track)).commit();
        } else {
            triviaPrompter();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("", track);
        outState.putInt("", userScore);
        outState.putInt("", questionsAmount_);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int identify = item.getItemId();
        super.onOptionsItemSelected(item);

        switch (identify) {
            case R.id.action_Average:
                resultPrompter();
                break;
            case R.id.action_selectQuestions:
                QuestionAmountManager newNumberOfQuestions =
                        QuestionAmountManager.newInstance(questionsAmount);
                newNumberOfQuestions.stateManager = this;
                newNumberOfQuestions.show(manageFrag, QuestionAmountManager.context);
                break;
            case R.id.action_Reset:
                fileSystem.deleteRecords(MainActivity.this);
                Toast.makeText(this, getResources().getString(R.string.reset),
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userScore = savedInstanceState.getInt("");
        track = savedInstanceState.getInt("");
        questionsAmount_ = savedInstanceState.getInt("");
    }

    @Override
    public void btn_OkayClickedState(int val) {
        int defaultVal = 0;

        if (val != -1) {
            track = defaultVal;
            userScore = defaultVal;
            questionsAmount_ = val;
            bankArr = createQuiz(val);
            manageFrag.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            manageFrag.beginTransaction().replace(R.id.fragContainerView,
                    bankArr.get(defaultVal)).commit();
        } else {
            prompter.setMessage(getResources().getString(R.string.txt_InvalidNumber)
                    + questionsAmount).setCancelable(true).show();
        }
    }

    @Override
    public void btn_CancelClickedState() {
        return;
    }

    public ArrayList<QuestionFragment> createQuiz(int numberOfQuestions) {
        int val;
        progressBar.setMax(numberOfQuestions);
        progressBar.setProgress(0);
        Random random = new Random();

        int qBankAmount = ((Manager) getApplication()).questionBank.size();
        int qBankColour = ((Manager) getApplication()).questionBackgroundColour.size();

        ArrayList<Integer> questionNumbers = new ArrayList<>(numberOfQuestions);
        ArrayList<Question> questions = ((Manager) getApplication()).questionBank;
        ArrayList<Integer> colors = ((Manager) getApplication()).questionBackgroundColour;
        ArrayList<Integer> qScope = new ArrayList<>(qBankAmount);

        for (int a = 0; a < qBankAmount; a++) {
            qScope.add(a);
        }
        for (int a = 0; a < numberOfQuestions; a++) {
            val = random.nextInt(qScope.size());
            questionNumbers.add(qScope.get(val));
            qScope.remove(val);
        }

        ((Manager) getApplication()).manager.questionFragManager =
                new ArrayList<>(numberOfQuestions);

        for (int a = 0; a < numberOfQuestions; a++) {
            QuestionFragment question_fragment =
                    QuestionFragment.createInstance(
                            questions.get(questionNumbers.get(a)).q,
                            colors.get(random.nextInt(qBankColour)),
                            questions.get(questionNumbers.get(a)).a);
            ((Manager) getApplication()).manager.questionFragManager.add(question_fragment);
        }
        return ((Manager) getApplication()).manager.questionFragManager;
    }

    public void resultPrompter() {
        int defaultVal = 0, defaultVal_ = 1;

        ArrayList<Integer> result = fileSystem.readRecords(MainActivity.this);
        prompter.setMessage(getResources().getString(R.string.txt_Result) +
                result.get(defaultVal) + getResources().getString(R.string.txt_slash) +
                result.get(defaultVal_)).setCancelable(true).show();
    }

    public void triviaPrompter() {
        final int defaultVal = 0, defaultVal_ = 1;

        create.setMessage(getResources().getString(R.string.txt_Score) + " "
                        + userScore + " " + getResources().getString(R.string.txt_OutOf) + " " + questionsAmount_)
                .setCancelable(false)
                .setPositiveButton(getResources()
                                .getString(R.string.optionSave)
                        , (dialogInterface, i) -> {
                            ArrayList<Integer> result = fileSystem.readRecords
                                    (MainActivity.this);
                            int updatedIsCorrect = result.get(defaultVal) + userScore;
                            int updatedIsTotalQ = result.get(defaultVal_) + questionsAmount_;

                            fileSystem.saveRecords(MainActivity.this,
                                    updatedIsCorrect, updatedIsTotalQ);
                            track = defaultVal;
                            userScore = defaultVal;

                            bankArr = createQuiz(questionsAmount_);
                            manageFrag.beginTransaction().setTransition(FragmentTransaction.
                                    TRANSIT_FRAGMENT_OPEN);
                            manageFrag.beginTransaction().replace(R.id.fragContainerView,
                                    bankArr.get(defaultVal)).commit();
                        })
                .setNegativeButton(getResources()
                                .getString(R.string.optionIgnore)
                        , (dialogInterface, i) -> {
                            track = defaultVal;
                            userScore = defaultVal;
                            bankArr = createQuiz(questionsAmount_);
                            manageFrag.beginTransaction().setTransition
                                    (FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            manageFrag.beginTransaction().replace
                                    (R.id.fragContainerView, bankArr.get(defaultVal)).commit();
                        }).show();
    }
}