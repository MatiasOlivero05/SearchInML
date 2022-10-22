package ar.com.maol.searchinml.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ar.com.maol.searchinml.R;
import ar.com.maol.searchinml.models.Result;
import ar.com.maol.searchinml.util.Util;

public class ProductSearchResultsAdapter extends RecyclerView.Adapter<ProductSearchResultsAdapter.ProductoSearchResultHolder> {
    private List<Result> results = new ArrayList<>();
    private ItemClickListener mClickListener;

    @NonNull
    @Override
    public ProductoSearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new ProductoSearchResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoSearchResultHolder holder, int position) {
        Result result = results.get(position);

        //IMAGE
        if (result.getThumbnail() != null && !result.getThumbnail().isEmpty()) {
            String imageUrl = result.getThumbnail().replace("http://", "https://");
            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.smallThumbnailImageView);
        }

        //TITLE
        String title;
        if (result.getTitle() != null && !result.getTitle().isEmpty()) {
            title = result.getTitle();
        }else{
            title = Resources.getSystem().getString(R.string.not_title);
        }
        holder.productTitleTextView.setText(title);

        //SUBTITLE
        holder.productSubtitleTextView.setText(Util.getStringCurrencyAndPriceFormated(result.getCurrency_id(), result.getPrice()));

        // DESCRITION
        String condition = Util.getStringCondition(holder.itemView.getContext(), result.getCondition());
        holder.productDescriptionTextView.setText(condition);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class ProductoSearchResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView productTitleTextView;
        private TextView productSubtitleTextView;
        private TextView productDescriptionTextView;
        private ImageView smallThumbnailImageView;

        public ProductoSearchResultHolder(@NonNull View itemView) {
            super(itemView);

            productTitleTextView = itemView.findViewById(R.id.product_item_title);
            productSubtitleTextView = itemView.findViewById(R.id.product_item_subtitle);
            productDescriptionTextView = itemView.findViewById(R.id.product_item_description);
            smallThumbnailImageView = itemView.findViewById(R.id.product_item_thumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(results.get(getAdapterPosition()));
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(Result result);
    }
}
