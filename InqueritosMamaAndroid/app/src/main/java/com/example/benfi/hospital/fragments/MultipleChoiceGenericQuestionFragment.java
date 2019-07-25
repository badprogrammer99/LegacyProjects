package com.example.benfi.hospital.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benfi.hospital.R;
import com.example.benfi.hospital.models.questions.GenericQuestion;
import com.example.benfi.hospital.utils.AppUtils;

import java.util.ArrayList;

public class MultipleChoiceGenericQuestionFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private View mView;
    private GenericQuestion mQuestion;
    private ArrayList<CheckBox> mCheckBoxes;
    private int mNumberOfCheckboxesSelected = 0;
    private ArrayList<String> mSelectedChoices = new ArrayList<>();
    private String mSelectedExitModeChoice;
    private MultipleChoiceGenericQuestionFragmentListener mListener;

    public MultipleChoiceGenericQuestionFragment() {
        // Required empty public constructor
    }

    public static MultipleChoiceGenericQuestionFragment newInstance(int questionNumber, GenericQuestion question) {
        MultipleChoiceGenericQuestionFragment fragment = new MultipleChoiceGenericQuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionNumber", questionNumber);
        args.putSerializable("question", question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().getSerializable("question") instanceof GenericQuestion) {
                mQuestion = (GenericQuestion) getArguments().getSerializable("question");
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            switch (mQuestion.getPossibleAnswerNames().size()) {
                case 3:
                    mView = inflater.inflate(R.layout.fragment_three_multiple_choice_question, container, false);
                    break;
                case 4:
                    mView = inflater.inflate(R.layout.fragment_four_multiple_choice_question, container, false);
                    break;
                default:
                    throw new RuntimeException();
            }
        }

        View questionHeader = mView.findViewById(R.id.header_question);
        TextView questionTitle = questionHeader.findViewById(R.id.titulo_pergunta);
        questionTitle.setText(getArguments().getInt("questionNumber") + ". " + mQuestion.getName());

        View questionFooter = mView.findViewById(R.id.footer_question);
        RadioGroup radioGroup = questionFooter.findViewById(R.id.radio_group_exit);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    mSelectedExitModeChoice = checkedRadioButton.getText().toString();
                }
            }
        });

        Button button = questionFooter.findViewById(R.id.confirmar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if (mSelectedChoices != null && mSelectedExitModeChoice != null) {
                        EditText observations = getActivity().findViewById(R.id.observacoes);
                        mListener.onMultipleChoiceGenericQuestionFragmentConfirmButton(v, mSelectedChoices, observations.getText().toString(), mSelectedExitModeChoice);
                    } else {
                        Toast.makeText(AppUtils.getAppContext(), "Tem que seleccionar todas as opções", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        this.setCheckBoxes();

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MultipleChoiceGenericQuestionFragmentListener) {
            mListener = (MultipleChoiceGenericQuestionFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MultipleChoiceGenericQuestionFragmentListener");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isPressed()) {
            String questionName = buttonView.getText().toString();
            if (isChecked) {
                mSelectedChoices.add(questionName);
                mNumberOfCheckboxesSelected++;
                this.toggleCheckboxesOnPossibleAnswers();
            } else {
                for (String selectedChoice : mSelectedChoices) {
                    if (selectedChoice.equals(questionName)) {
                        mSelectedChoices.remove(selectedChoice);
                        mNumberOfCheckboxesSelected--;
                        this.toggleCheckboxesOnPossibleAnswers();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setCheckBoxes() {
        mCheckBoxes = new ArrayList<>();

        ViewGroup mainView = (ViewGroup) mView;

        int viewChildCount = mainView.getChildCount();

        for (int i = 0; i < viewChildCount; i++) {
            View childView = mainView.getChildAt(i);

            if (childView instanceof CheckBox) {
                mCheckBoxes.add((CheckBox) childView);
            }
        }

        for (int i = 0; i < mQuestion.getPossibleAnswerNames().size(); i++) {
            mCheckBoxes.get(i).setText(mQuestion.getPossibleAnswerName(i));
            mCheckBoxes.get(i).setOnCheckedChangeListener(this);
        }
    }

    private void toggleCheckboxesOnPossibleAnswers() {
        if (mNumberOfCheckboxesSelected == mQuestion.getPossibleAnswers()) {
            for (CheckBox checkBox : mCheckBoxes) {
                if (!checkBox.isChecked()) {
                    checkBox.setEnabled(false);
                }
            }
        } else {
            for (CheckBox checkBox : mCheckBoxes) {
                if (!checkBox.isEnabled()) {
                    checkBox.setEnabled(true);
                }
            }
        }
    }

    public interface MultipleChoiceGenericQuestionFragmentListener {
        void onMultipleChoiceGenericQuestionFragmentConfirmButton(View v, ArrayList<String> choices, String observations, String exitMode);
    }
}