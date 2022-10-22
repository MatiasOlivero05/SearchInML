package ar.com.maol.searchinml.views;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ar.com.maol.searchinml.R;
import ar.com.maol.searchinml.models.Result;
import ar.com.maol.searchinml.util.Constants;
import ar.com.maol.searchinml.util.Util;

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


        ImageView fragment_detail_product_thumbnail = view.findViewById(R.id.fragment_detail_product_thumbnail);
        if (mResult.getThumbnail() != null && !mResult.getThumbnail().isEmpty()) {
            String imageUrl = mResult.getThumbnail().replace("http://", "https://");
            Glide.with(view)
                    .load(imageUrl)
                    .into(fragment_detail_product_thumbnail);
        }

        TextView fragment_detail_product_title = view.findViewById(R.id.fragment_detail_product_title);
        try {
            fragment_detail_product_title.setText(mResult.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragment_detail_product_price = view.findViewById(R.id.fragment_detail_product_price);
        try {
            fragment_detail_product_price.setText(Util.getStringCurrencyAndPriceFormated(mResult.getCurrency_id(), mResult.getPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragment_detail_product_condition = view.findViewById(R.id.fragment_detail_product_condition);
        try {
            String condition = Util.getStringCondition(requireContext(), mResult.getCondition());
            fragment_detail_product_condition.setText(condition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragment_detail_product_accepts_mercadopago = view.findViewById(R.id.fragment_detail_product_accepts_mercadopago);
        try {
            if (mResult.isAccepts_mercadopago()) {
                fragment_detail_product_accepts_mercadopago.setText(requireContext().getResources().getString(R.string.acepta_ml));
                fragment_detail_product_accepts_mercadopago.setTextColor(Color.GREEN);
            } else {
                fragment_detail_product_accepts_mercadopago.setText(requireContext().getResources().getString(R.string.no_acepta_ml));
                fragment_detail_product_accepts_mercadopago.setTextColor(Color.DKGRAY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragment_detail_product_seller_address = view.findViewById(R.id.fragment_detail_product_seller_address);
        try {
            if (mResult.getSeller_address() != null
                    && mResult.getSeller_address().getCity() != null && !mResult.getSeller_address().getCity().getName().isEmpty()
                    && mResult.getSeller_address().getState() != null && !mResult.getSeller_address().getState().getName().isEmpty()
                    && mResult.getSeller_address().getCountry() != null && !mResult.getSeller_address().getCountry().getName().isEmpty()) {

                StringBuilder sbSellerAddress = new StringBuilder().append(mResult.getSeller_address().getCity().getName()).append(" ").append(mResult.getSeller_address().getState().getName()).append(" ").append(mResult.getSeller_address().getCountry().getName());
                fragment_detail_product_seller_address.setText(sbSellerAddress.toString());
            } else {
                fragment_detail_product_seller_address.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragment_detail_product_permalink = view.findViewById(R.id.fragment_detail_product_permalink);
        try {
            if (mResult.getPermalink() != null && !mResult.getPermalink().isEmpty()) {
                fragment_detail_product_permalink.setText(mResult.getPermalink());
            } else {
                fragment_detail_product_permalink.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}