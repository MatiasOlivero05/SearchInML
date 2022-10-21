package ar.com.maol.searchinml.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ar.com.maol.searchinml.R;
import ar.com.maol.searchinml.models.Result;
import ar.com.maol.searchinml.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailProductFragment extends Fragment {

    private String mJsonResult;
    private Result mResult;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mJsonResult = getArguments().getString(Constants.ARG_RESULT);
            Gson gson = new Gson();
            Type listType = new TypeToken<Result>(){}.getType();
            mResult = gson.fromJson(mJsonResult, listType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_detail_product, container, false);
        TextView tx_title = view.findViewById(R.id.tx_title);
        tx_title.setText(mResult.getTitle());
        return view;
    }
}