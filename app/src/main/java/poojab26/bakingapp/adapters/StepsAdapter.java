package poojab26.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import poojab26.bakingapp.R;
import poojab26.bakingapp.model.Step;


/**
 * Created by poojab26 on 02-Mar-18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{
    public StepsAdapter(ArrayList<Step> steps, OnItemClickListener listener) {
        stepArrayList = steps;
        mListener = listener;
       // mParentActivity = parentActivity;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
  //  private final RecipeListActivity mParentActivity;
    private final ArrayList<Step> stepArrayList;
    private final OnItemClickListener mListener;


    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_card_layout, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        holder.bind(position, mListener);

    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvStepDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            tvStepDescription = itemView.findViewById(R.id.tv_steps_item);
        }

        public void bind(final int position, final OnItemClickListener listener) {
            tvStepDescription.setText(stepArrayList.get(position).getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                  //  Log.d("TAG", "clicked " + position + mTwoPane);

                 /*   if (mTwoPane) {
                       *//* Bundle arguments = new Bundle();
                        arguments.putString(RecipeItemDetailFragment.ARG_ITEM_ID, Integer.toString(5));*//*
                        RecipeItemDetailFragment fragment = new RecipeItemDetailFragment();
                        fragment.setId(5);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Log.d("TAG", "twopane " +  mTwoPane);

                        *//*Context context = view.getContext();
                        Intent intent = new Intent(context, RecipeItemDetailActivity.class);
                        intent.putExtra(RecipeItemDetailFragment.ARG_ITEM_ID, position);

                        context.startActivity(intent);*//*
                    }*/
                }
            });
        }
    }
}
