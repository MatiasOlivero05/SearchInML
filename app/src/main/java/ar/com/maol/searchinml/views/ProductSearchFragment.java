package ar.com.maol.searchinml.views;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import ar.com.maol.searchinml.R;
import ar.com.maol.searchinml.adapters.ProductSearchResultsAdapter;
import ar.com.maol.searchinml.models.Result;
import ar.com.maol.searchinml.models.ResultsResponse;
import ar.com.maol.searchinml.util.Constants;
import ar.com.maol.searchinml.util.Util;
import ar.com.maol.searchinml.viewmodels.ProductSearchViewModel;

public class ProductSearchFragment  extends Fragment implements ProductSearchResultsAdapter.ItemClickListener {
    private ProductSearchViewModel viewModel;
    private ProductSearchResultsAdapter adapter;

    AlertDialog alertDialogLoading;
    private TextInputEditText keywordEditText;
    private ImageButton searchImageButton;
    String sLastCharSequence;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProductSearchResultsAdapter(requireContext());
        adapter.setClickListener(this);

        viewModel = ViewModelProviders.of(this).get(ProductSearchViewModel.class);
        viewModel.init();
        viewModel.getResultsResponseLiveData().observe(this, new Observer<ResultsResponse>() {
            @Override
            public void onChanged(ResultsResponse resultsResponse) {

                if (alertDialogLoading != null && alertDialogLoading.isShowing()) {
                    alertDialogLoading.dismiss();
                }
                if (resultsResponse != null && resultsResponse.getResults() != null && resultsResponse.getResults().size() > 0) {
                    searchImageButton.setVisibility(View.GONE);
                    adapter.setResults(resultsResponse.getResults());

                }else{
                    searchImageButton.setVisibility(View.VISIBLE);
                    AlertDialog.Builder alertOK = Util.getAlertDialogOk(requireContext(), requireActivity().getResources().getString(R.string.informacion), requireActivity().getResources().getString(R.string.ocurrio_un_error_y_no_hemos_podido_realizar_la_busqueda_solicitada_por_favor_vuelva_a_intentarlo));
                    alertOK.show();
                }
                try {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                } catch (Exception e) {
                    e.printStackTrace();
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

        alertDialogLoading = Util.getAlertDialogLoading(requireContext()).create();
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
                    if (sLastCharSequence == null || !charSequence.toString().equals(sLastCharSequence)) {
                        sLastCharSequence = charSequence.toString();
                        if (searchImageButton.getVisibility() != View.VISIBLE) {
                            searchImageButton.setVisibility(View.VISIBLE);
                        }
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
                if (alertDialogLoading != null && !alertDialogLoading.isShowing()) {
                    alertDialogLoading.show();
                }
                adapter.setResults(new ArrayList<>());
                performSearch();
            }
        });

        keywordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchImageButton.setVisibility(View.GONE);
                    if (alertDialogLoading != null && !alertDialogLoading.isShowing()) {
                        alertDialogLoading.show();
                    }
                    adapter.setResults(new ArrayList<>());
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
        Gson gson = new Gson();
        Bundle bundle = new Bundle();
        String jsonResul = gson.toJson(result);
        bundle.putString(Constants.ARG_RESULT, jsonResul);
        Navigation.findNavController(requireActivity(),R.id.activity_main_navhostfragment).navigate(R.id.detailProductFragment, bundle);
    }
}