package com.example.benfi.hospital.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benfi.hospital.R;
import com.example.benfi.hospital.models.questions.CompositeQuestion;
import com.example.benfi.hospital.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class MultipleChoiceCompositeQuestionFragment extends Fragment {
    private View mView;
    private CompositeQuestion mQuestion;
    private ArrayList<CheckBox> mCheckBoxes;
    private int mNumberOfCheckboxesSelected = 0;
    private HashMap<String, ArrayList<String>> mSelectedChoices = new HashMap<>();
    private String mSelectedExitModeChoice;
    private MultipleChoiceCompositeQuestionFragmentListener mListener;

    public MultipleChoiceCompositeQuestionFragment() {
        // Required empty public constructor
    }

    public static MultipleChoiceCompositeQuestionFragment newInstance(int questionNumber, CompositeQuestion question) {
        MultipleChoiceCompositeQuestionFragment fragment = new MultipleChoiceCompositeQuestionFragment();
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
            if (getArguments().getSerializable("question") instanceof CompositeQuestion) {
                mQuestion = (CompositeQuestion) getArguments().getSerializable("question");
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            switch (mQuestion.getCompositePossibleAnswers().size()) {
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
                        mListener.onMultipleChoiceCompositeQuestionFragmentConfirmButton(v, mSelectedChoices, observations.getText().toString(), mSelectedExitModeChoice);
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
        if (context instanceof MultipleChoiceCompositeQuestionFragmentListener) {
            mListener = (MultipleChoiceCompositeQuestionFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MultipleChoiceCompositeQuestionFragmentListener");
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

        for (int i = 0; i < mQuestion.getCompositePossibleAnswers().size(); i++) {
            mCheckBoxes.get(i).setText(mQuestion.getCompositePossibleAnswer(i).getId());
            this.setCompositeOnCheckedChangeListener(mCheckBoxes.get(i), mQuestion, i);
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

    private void setCompositeOnCheckedChangeListener(CheckBox checkBox, final CompositeQuestion question, final int index) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    String questionName = buttonView.getText().toString();
                    if (isChecked) {
                        showCompositeAnswers(buttonView, question.getCompositePossibleAnswer(index).getPossibleAnswerNames());
                    } else {
                        for (String selectedChoice : mSelectedChoices.keySet()) {
                            if (selectedChoice.equals(questionName)) {
                                mSelectedChoices.remove(selectedChoice);
                                mNumberOfCheckboxesSelected--;
                                toggleCheckboxesOnPossibleAnswers();
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void showCompositeAnswers(final CompoundButton v, final ArrayList<String> answerChoices) {
        final CheckBox parentCheckbox = (CheckBox) v;
        final String selectedChoice = parentCheckbox.getText().toString();

        final ArrayList<String> userChoices = new ArrayList<>();

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_choices);

        LinearLayout mainLinearLayout = bottomSheetDialog.findViewById(R.id.id_layout_bottom_sheet_choices);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 0, 0, 20);

        for (int i = 0; i < answerChoices.size(); i++) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setLayoutParams(params);
            checkBox.setText(answerChoices.get(i));
            checkBox.setPadding(20,0,0,0);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        userChoices.add(buttonView.getText().toString());
                    } else {
                        userChoices.remove(buttonView.getText().toString());
                    }
                }
            });
            mainLinearLayout.addView(checkBox, i + 1);
        }

        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                parentCheckbox.setChecked(false);
            }
        });
        bottomSheetDialog.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedChoices.put(selectedChoice, userChoices);
                mNumberOfCheckboxesSelected++;
                toggleCheckboxesOnPossibleAnswers();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    public interface MultipleChoiceCompositeQuestionFragmentListener {
        void onMultipleChoiceCompositeQuestionFragmentConfirmButton(View v, HashMap<String, ArrayList<String>> selectedChoices, String observations, String exitMode);
    }
}