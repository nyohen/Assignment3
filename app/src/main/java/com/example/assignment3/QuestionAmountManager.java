package com.example.assignment3;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

public class QuestionAmountManager extends DialogFragment
{
    interface DialogClickListener {
        void btn_OkayClickedState(int val);
        void btn_CancelClickedState();
    }

    private int questionsAmount;
    private static final String myArgs = "";
    public static final String context = "";
    public DialogClickListener stateManager;
    public QuestionAmountManager() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { questionsAmount = getArguments().getInt(myArgs); }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.question_amount_manager,
                container, false);

        TextView textView;
        textView = view.findViewById(R.id.txt_Inform);
        textView.setText(getResources().getString(R.string.txt_EnterNumOfQuestions)
                + " " + questionsAmount);

        EditText editText;
        editText = view.findViewById(R.id.txt_EnterNum);

        Button okBtn;
        okBtn = view.findViewById(R.id.btn_Okay);

        Button cancelBtn;
        cancelBtn = view.findViewById(R.id.btn_Cancel);

        okBtn.setOnClickListener(new View.OnClickListener()
        {
            final int defaultVal = 1, defaultVal_ = 0;
            int value;

            @Override
            public void onClick(View view)
            {
                if(!editText.getText().toString().isEmpty())
                {
                    try{
                        value = Integer.parseInt(editText.getText().toString());
                        if(value <= defaultVal_ || value > questionsAmount ) value = defaultVal;
                    }
                    catch (Exception i) { value = defaultVal; }
                } else value = defaultVal;

                stateManager.btn_OkayClickedState(value);
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(temp -> {
            stateManager.btn_CancelClickedState();
            dismiss();
        });
        return view;
    }

    public static QuestionAmountManager newInstance(int mychildArg)
    {
        QuestionAmountManager fragment = new QuestionAmountManager();
        Bundle args = new Bundle();
        args.putInt(myArgs, mychildArg);
        fragment.setArguments(args);
        return fragment;
    }
}