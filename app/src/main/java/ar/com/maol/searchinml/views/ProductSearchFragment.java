package ar.com.maol.searchinml.views;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Objects;

import ar.com.maol.searchinml.R;
import ar.com.maol.searchinml.adapters.ProductSearchResultsAdapter;
import ar.com.maol.searchinml.models.Result;
import ar.com.maol.searchinml.models.ResultsResponse;
import ar.com.maol.searchinml.util.Constants;
import ar.com.maol.searchinml.viewmodels.ProductSearchViewModel;

public class ProductSearchFragment  extends Fragment implements ProductSearchResultsAdapter.ItemClickListener {
    private ProductSearchViewModel viewModel;
    private ProductSearchResultsAdapter adapter;

    private TextInputEditText keywordEditText;
    private ImageButton searchImageButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProductSearchResultsAdapter();
        adapter.setClickListener(this);

        viewModel = ViewModelProviders.of(this).get(ProductSearchViewModel.class);
        viewModel.init();
        viewModel.getResultsResponseLiveData().observe(this, new Observer<ResultsResponse>() {
            @Override
            public void onChanged(ResultsResponse resultsResponse) {
                if (resultsResponse != null && resultsResponse.getResults() != null && resultsResponse.getResults().size() > 0) {
                    searchImageButton.setVisibility(View.GONE);
                    adapter.setResults(resultsResponse.getResults());
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.ups_ocurrio_algo_fallo_volve_a_intentarlo), Toast.LENGTH_LONG ).show();
                    searchImageButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productsearch, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_productsearch_searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        keywordEditText = view.findViewById(R.id.fragment_productsearch_keyword);
        searchImageButton = view.findViewById(R.id.fragment_productsearch_search);

        keywordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    if (searchImageButton.getVisibility() != View.GONE) {
                        searchImageButton.setVisibility(View.GONE);
                    }
                }else{
                    if (searchImageButton.getVisibility() != View.VISIBLE) {
                        searchImageButton.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchImageButton.setVisibility(View.GONE);
        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImageButton.setVisibility(View.GONE);
                performSearch();
            }
        });

        keywordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchImageButton.setVisibility(View.GONE);
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public void performSearch() {
        String keyword = keywordEditText.getEditableText().toString();
        viewModel.searchVolumes(keyword);
    }

    @Override
    public void onItemClick(Result result) {
        Toast.makeText(getContext(), "Result: " + result.getTitle(), Toast.LENGTH_LONG ).show();

        Gson gson = new Gson();
        Bundle bundle = new Bundle();
        String jsonResul = gson.toJson(result);
        bundle.putString(Constants.ARG_RESULT, jsonResul);
        Navigation.findNavController(requireActivity(),R.id.activity_main_navhostfragment).navigate(R.id.detailProductFragment, bundle);
    }
}