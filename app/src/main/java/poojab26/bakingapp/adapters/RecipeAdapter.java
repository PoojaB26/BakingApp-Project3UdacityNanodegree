package poojab26.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import poojab26.bakingapp.ItemDetailActivity;
import poojab26.bakingapp.ItemDetailFragment;
import poojab26.bakingapp.ItemListActivity;
import poojab26.bakingapp.R;
import poojab26.bakingapp.model.Recipe;


/**
 * Created by poojab26 on 02-Mar-18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    public RecipeAdapter(List<Recipe> recipes, OnItemClickListener listener, ItemListActivity parentActivity, boolean twoPane) {
        recipeList = recipes;
        mListener = listener;
        mParentActivity = parentActivity;
        mTwoPane = twoPane;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private final ItemListActivity mParentActivity;
    private final boolean mTwoPane;
    private final List<Recipe> recipeList;
    private final OnItemClickListener mListener;


    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        holder.bind(position, mListener);

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvId, tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tvName);
        }

        public void bind(final int position, final OnItemClickListener listener) {
            tvId.setText(Integer.toString(recipeList.get(position).getId()));
            tvName.setText(recipeList.get(position).getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                    Log.d("TAG", "clicked " + position + mTwoPane);

                    if (mTwoPane) {
                       /* Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, Integer.toString(5));*/
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setId(5);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Log.d("TAG", "twopane " +  mTwoPane);

                        /*Context context = view.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, position);

                        context.startActivity(intent);*/
                    }
                }
            });
        }
    }
}
