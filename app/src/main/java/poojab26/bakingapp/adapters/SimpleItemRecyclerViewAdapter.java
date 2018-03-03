package poojab26.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import poojab26.bakingapp.RecipeItemDetailActivity;
import poojab26.bakingapp.RecipeItemDetailFragment;
import poojab26.bakingapp.RecipeListActivity;
import poojab26.bakingapp.R;
import poojab26.bakingapp.dummy.DummyContent;

/**
 * Created by poojab26 on 02-Mar-18.
 */

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final RecipeListActivity mParentActivity;
    private final List<DummyContent.DummyItem> mValues;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(RecipeItemDetailFragment.ARG_ITEM_ID, Integer.toString(5));
                RecipeItemDetailFragment fragment = new RecipeItemDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeItemDetailActivity.class);
                intent.putExtra(RecipeItemDetailFragment.ARG_ITEM_ID, 5);

                context.startActivity(intent);
            }
        }
    };

    public SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                  List<DummyContent.DummyItem> items,
                                  boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

       // holder.mIdView.setText(mValues.get(position).id);
       // holder.mContentView.setText(mValues.get(position).content);
        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //final TextView mIdView;
      //  final TextView mContentView;

        ViewHolder(View view) {
            super(view);
         //   mIdView = (TextView) view.findViewById(R.id.id_text);
          //  mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}