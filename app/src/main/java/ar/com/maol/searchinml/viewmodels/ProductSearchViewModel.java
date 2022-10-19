package ar.com.maol.searchinml.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ar.com.maol.searchinml.models.ResultsResponse;
import ar.com.maol.searchinml.repositories.ProductRepository;

public class ProductSearchViewModel  extends AndroidViewModel {
    private ProductRepository productRepository;
    private LiveData<ResultsResponse> resultsResponseLiveData;

    public ProductSearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        productRepository = new ProductRepository();
        resultsResponseLiveData = productRepository.getResultsResponseLiveData();
    }

    public void searchVolumes(String keyword) {
        productRepository.searchVolumes(keyword);
    }

    public LiveData<ResultsResponse> getResultsResponseLiveData() {
        return resultsResponseLiveData;
    }
}