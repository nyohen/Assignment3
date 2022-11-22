package com.example.assignment3;
import java.util.ArrayList;
import android.app.Application;

public class Manager extends Application
{
    FileSystem fileSystem = new FileSystem();
    QuestionFragmentStyle manager = new QuestionFragmentStyle();
    boolean isTrue_ = true, isFalse_ = false;

    ArrayList<Integer> questionBackgroundColour = new ArrayList<Integer>()
    {
        {
            add(R.color.yellow);
            add(R.color.gray);
            add(R.color.green);
            add(R.color.royalBlue);
            add(R.color.purple);
            add(R.color.red);
        }
    };

   ArrayList<Question> questionBank = new ArrayList<Question>()
   {
       {
           add(new Question(R.string.question1,isFalse_));
           add(new Question(R.string.question2,isFalse_));
           add(new Question(R.string.question3,
                   isTrue_));
           add(new Question(R.string.question4,
                   isTrue_));
           add(new Question(R.string.question5,
                   isFalse_));
           add(new Question(R.string.question6,isTrue_));
           add(new Question(R.string.question7,isTrue_));
           add(new Question(R.string.question8,
                   isFalse_));
           add(new Question(R.string.question9,isFalse_));
           add(new Question(R.string.question10,
                   isTrue_));
       }
   };
}
