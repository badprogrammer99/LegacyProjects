package com.example.benfi.hospital.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benfi.hospital.R;
import com.example.benfi.hospital.models.questions.GenericQuestion;
import com.example.benfi.hospital.utils.AppUtils;

import java.util.ArrayList;

public class SingleChoiceGenericQuestionFragment extends Fragment implements OnClickListener {
    private View mView;
    private GenericQuestion mQuestion;
    private ArrayList<RadioButton> mRadioButtons = new ArrayList<>();
    private String mSelectedChoice;
    private String mSelectedExitModeChoice;
    private SingleChoiceGenericQuestionFragmentInteractionListener mListener;

    public SingleChoiceGenericQuestionFragment() {
        // Required empty public constructor
    }

    public static SingleChoiceGenericQuestionFragment newInstance(int questionNumber, GenericQuestion question) {
        SingleChoiceGenericQuestionFragment fragment = new SingleChoiceGenericQuestionFragment();
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
                case 2:
                    mView = inflater.inflate(R.layout.fragment_boolean_question, container, false);
                    break;
                case 3:
                    mView = inflater.inflate(R.layout.fragment_three_single_choice_question, container, false);
                    break;
                case 4:
                    mView = inflater.inflate(R.layout.fragment_four_single_choice_question, container, false);
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
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if (mSelectedChoice != null && mSelectedExitModeChoice != null) {
                        EditText observations = getActivity().findViewById(R.id.observacoes);
                        mListener.onSingleChoiceGenericQuestionFragmentConfirmButton(v, mSelectedChoice, observations.getText().toString(), mSelectedExitModeChoice);
                    } else {
                        Toast.makeText(AppUtils.getAppContext(), "Tem que seleccionar todas as opções", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        this.setRadioButtons();

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SingleChoiceGenericQuestionFragmentInteractionListener) {
            mListener = (SingleChoiceGenericQuestionFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SingleChoiceGenericQuestionFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        for (RadioButton radioButton : mRadioButtons) {
            if (radioButton.isChecked() && !radioButton.equals((RadioButton) v)) {
                radioButton.setChecked(false);
            }
        }
        mSelectedChoice = ((RadioButton) v).getText().toString();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setRadioButtons() {
        ViewGroup mainView = (ViewGroup) mView;

        int viewChildCount = mainView.getChildCount();

        for (int i = 0; i < viewChildCount; i++) {
            View childView = mainView.getChildAt(i);

            if (childView instanceof RadioButton) {
                mRadioButtons.add((RadioButton) childView);
            }
        }

        for (int i = 0; i < mQuestion.getPossibleAnswerNames().size(); i++) {
            mRadioButtons.get(i).setText(mQuestion.getPossibleAnswerName(i));
            mRadioButtons.get(i).setOnClickListener(this);
        }
    }

    public interface SingleChoiceGenericQuestionFragmentInteractionListener {
        void onSingleChoiceGenericQuestionFragmentConfirmButton(View v, String choiceName, String observations, String exitMode);
    }
}