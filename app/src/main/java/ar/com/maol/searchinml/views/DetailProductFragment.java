package ar.com.maol.searchinml.views;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


        ImageView fragment_detail_product_thumbnailImageView = view.findViewById(R.id.fragment_detail_product_thumbnail);
        if (mResult.getThumbnail() != null && !mResult.getThumbnail().isEmpty()) {
            String imageUrl = mResult.getThumbnail().replace("http://", "https://");
            Glide.with(view)
                    .load(imageUrl)
                    .fitCenter()
                    .into(fragment_detail_product_thumbnailImageView);
        }

        TextView fragmentDetailProductTitleTextView = view.findViewById(R.id.fragment_detail_product_title);
        try {
            fragmentDetailProductTitleTextView.setText(mResult.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragmentDetailProductPriceTextView = view.findViewById(R.id.fragment_detail_product_price);
        try {
            fragmentDetailProductPriceTextView.setText(Util.getStringCurrencyAndPriceFormated(mResult.getCurrency_id(), mResult.getPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragmentDetailProductConditionTextView = view.findViewById(R.id.fragment_detail_product_condition);
        try {
            String condition = Util.getStringCondition(requireContext(), mResult.getCondition());
            fragmentDetailProductConditionTextView.setText(condition);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragmentDetailProductAcceptsMercadopagoTextView = view.findViewById(R.id.fragment_detail_product_accepts_mercadopago);
        try {
            if (mResult.isAccepts_mercadopago()) {
                fragmentDetailProductAcceptsMercadopagoTextView.setText(requireContext().getResources().getString(R.string.acepta_ml));
                fragmentDetailProductAcceptsMercadopagoTextView.setTextColor(Color.rgb(131,177,244));
            } else {
                fragmentDetailProductAcceptsMercadopagoTextView.setText(requireContext().getResources().getString(R.string.no_acepta_ml));
                fragmentDetailProductAcceptsMercadopagoTextView.setTextColor(Color.DKGRAY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView fragmentDetailProductSellerAddressTextView = view.findViewById(R.id.fragment_detail_product_seller_address);
        try {
            String adressFormated = Util.getStringAdressFormated(mResult.getSeller_address());
            if (!adressFormated.isEmpty()){
                fragmentDetailProductSellerAddressTextView.setText(adressFormated);
            } else {
                fragmentDetailProductSellerAddressTextView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button fragmentDetailProductPermalinkButton = view.findViewById(R.id.fragment_detail_product_permalink);
        try {
            if (mResult.getPermalink() != null && !mResult.getPermalink().isEmpty()) {
                fragmentDetailProductPermalinkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Uri uri = Uri.parse(mResult.getPermalink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                fragmentDetailProductPermalinkButton.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}