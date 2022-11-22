package com.example.assignment3;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment
{
    public boolean answer;
    private int q, qColor;
    private static final String myArgs = "firstArgument", myArgs2 = "secondArgument",
            myArgs3 = "thirdArgument";

    public QuestionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            q = getArguments().getInt(myArgs);
            qColor = getArguments().getInt(myArgs2);
            answer = getArguments().getBoolean(myArgs3);
        }
    }

    public static QuestionFragment createInstance(int q, int qColor, boolean isTrueorFalse)
    {
        QuestionFragment qFrag = new QuestionFragment();
        Bundle args = new Bundle();

        args.putInt(myArgs, q);
        args.putInt(myArgs2, qColor);
        args.putBoolean(myArgs3, isTrueorFalse);
        qFrag.setArguments(args);
        return qFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.question_fragment,
                container, false);

        TextView textView = view.findViewById(R.id.txt_questionBank);
        textView.setText(q);

        view.setBackgroundColor(getResources().getColor(qColor));
        return view;
    }
}