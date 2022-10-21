package ar.com.maol.searchinml.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button searchButton;

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
                if (resultsResponse != null) {
                    adapter.setResults(resultsResponse.getResults());
                }else{
                    //TODO NO SE ENCONTRARON RESULTADOS O FALLO
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
        searchButton = view.findViewById(R.id.fragment_productsearch_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
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
        Navigation.findNavController(getActivity(),R.id.activity_main_navhostfragment).navigate(R.id.detailProductFragment, bundle);
    }
}