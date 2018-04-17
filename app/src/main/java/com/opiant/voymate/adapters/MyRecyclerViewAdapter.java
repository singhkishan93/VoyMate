package com.opiant.voymate.adapters;

/**
 * Created by user on 25-11-2015.
 */
import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.opiant.voymate.R;
import com.opiant.voymate.utils.Author;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder>   {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Author> mDataset;
    private static MyClickListener myClickListener;
    private LayoutInflater inflater;
    private Context context;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Fragment mFragment;
    Bundle mBundle;
    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        ImageView imageView1;
        public CardView cardView;
        //private AdapterViewCompat.OnItemClickListener listener;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageView1=(ImageView)itemView.findViewById(R.id.icon1);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(Context ctx, List<Author> myDataset) {
        inflater = LayoutInflater.from(ctx);
        mDataset = myDataset;

    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = view.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        DataObjectHolder mainHolder = (DataObjectHolder) holder;
        final Author item = mDataset.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Bundle bundle = new Bundle();
                bundle.putString("Query", item.getFullName());
                bundle.putString("isAuthor", "true");
                SearchFragment fragment = new SearchFragment();
                fragment.setArguments(bundle);
                android.app.FragmentManager manager = ((Activity) context).getFragmentManager();
                mFragmentTransaction = manager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView1, fragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();*/

            }
        });

        holder.imageView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                /*Bundle bundle = new Bundle();
                bundle.putString("Query", item.getFullName());
                bundle.putString("isAuthor", "true");
                SearchFragment fragment = new SearchFragment();
                fragment.setArguments(bundle);
                android.app.FragmentManager manager = ((Activity) context).getFragmentManager();
                mFragmentTransaction = manager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView1, fragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();*/

            }

        });
        /*final Author rowItem = mDataset.get(position);
        Picasso.with(context).load(rowItem.Thumbnailurl).into(holder.imageView1);*/
    }

    public void addItem(Author dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
    @Override
    public int getItemCount() {
        return (null != mDataset ? mDataset.size() : 0);

    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}