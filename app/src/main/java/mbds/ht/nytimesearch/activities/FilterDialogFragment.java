package mbds.ht.nytimesearch.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;

import mbds.ht.nytimesearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterDialogFragment extends DialogFragment {
    private Button btOk;
    private Spinner mSort;

    public FilterDialogFragment() {
        // Required empty public constructor
    }


    public static FilterDialogFragment newInstance(int title) {
        FilterDialogFragment frag = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;


    }
    public interface EditCustomDialogListener {
        void onFinishEditDialog(String inputText);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        // mSort = (EditText) view.findViewById(R.id.txtSort);
        mSort = (Spinner) view.findViewById(R.id.txt_spinow);
        btOk = (Button) view.findViewById(R.id.btnSearch);
        // Fetch arguments from bundle and set title
       // String title = getArguments().getString("title", "Enter Name");
       // getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        //   mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCustomDialogListener listener = (EditCustomDialogListener) getActivity();

                String textSearch= String.valueOf(mSort.getSelectedItem());


                listener.onFinishEditDialog(textSearch);
                // Close the dialog and return back to the parent activity
                dismiss();
                //   return true;
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container);
    }

}