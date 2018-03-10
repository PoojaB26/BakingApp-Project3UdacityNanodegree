package poojab26.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import poojab26.bakingapp.RecipeListActivity;
import poojab26.bakingapp.R;
import poojab26.bakingapp.model.Recipe;


/**
 * Created by poojab26 on 02-Mar-18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    public RecipeAdapter(List<Recipe> recipes, OnItemClickListener listener) {
        recipeList = recipes;
        mListener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private final List<Recipe> recipeList;
    private final OnItemClickListener mListener;


    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_list_content, parent, false);
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

        TextView tvServings, tvName;
        ImageView ivRecipeImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvServings = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tvName);
            ivRecipeImage = itemView.findViewById(R.id.ivRecipeImage);
        }

        public void bind(final int position, final OnItemClickListener listener) {
            tvServings.setText(Integer.toString(recipeList.get(position).getServings())+ " Servings");
            tvName.setText(recipeList.get(position).getName());
            String imgPath = recipeList.get(position).getImage();
            if(!imgPath.equals("")) {
                Picasso.with(itemView.getContext())
                        .load(imgPath)
                        .into(ivRecipeImage);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);

                }
            });
        }
    }
}
