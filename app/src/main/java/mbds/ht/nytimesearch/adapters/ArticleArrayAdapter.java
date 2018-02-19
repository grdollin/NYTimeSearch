package mbds.ht.nytimesearch.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import mbds.ht.nytimesearch.R;
import mbds.ht.nytimesearch.models.Article;

/**
 * Created by Administrator on 2/14/2018.
 */

public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {
    private List<Article> mArticless;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;
        public TextView tvTitle;
       // public TextView tvAuthor;


        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.imageItem);
            tvTitle = (TextView)itemView.findViewById(R.id.tvtileItem);
           // tvAuthor = (TextView)itemView.findViewById(R.id.tvAuthor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener!=null)
                clickListener.onClick(view,getAdapterPosition());

        }
    }

    public void setClickListener(ItemClickListenerInterface   clickListener) {
        this.clickListener = clickListener;
    }

    private ItemClickListenerInterface  clickListener;



    public ArticleArrayAdapter(@NonNull Context context, List<Article> articles) {
        mArticless=articles;
        mContext=context;
        Log.d("adapter","testOK");
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ArticleArrayAdapter.ViewHolder viewHolder = new ArticleArrayAdapter.ViewHolder(articleView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticless.get(position);

        // Populate data into the template view using the data object
        viewHolder.tvTitle.setText(article.getHeadline());
        //viewHolder.thumbNailr.setText(book.getAuthor());
        String thumbNail =article.getThumbNail();
/*
        if(!TextUtils.isEmpty(thumbNail)){
            Picasso.with(getContext()).load(article.getThumbNail()).into(image);
            Log.d("text","test");
        }*/
        Glide.with(getContext())
                .load(Uri.parse(article.getThumbNail()))
                .placeholder(R.drawable.ic_nocover)
                .into(viewHolder.image);
        // Return the completed view to render on screen
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticless.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
}



