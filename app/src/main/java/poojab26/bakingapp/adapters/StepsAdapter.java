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

import java.util.ArrayList;

import poojab26.bakingapp.Fragments.RecipeItemDetailFragment;
import poojab26.bakingapp.Fragments.StepDetailFragment;
import poojab26.bakingapp.R;
import poojab26.bakingapp.RecipeItemDetailActivity;
import poojab26.bakingapp.StepDetailActivity;
import poojab26.bakingapp.Utils.Constants;
import poojab26.bakingapp.model.Step;


/**
 * Created by poojab26 on 02-Mar-18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{
    public StepsAdapter(RecipeItemDetailActivity parentActivity, boolean twoPane, ArrayList<Step> steps, OnItemClickListener listener) {
        mTwoPane = twoPane;
        stepArrayList = steps;
        mListener = listener;
        mParentActivity = parentActivity;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private final RecipeItemDetailActivity mParentActivity;
    private final boolean mTwoPane;
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
            tvStepDescription.setText(stepArrayList.get(position).getShortDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);

                    Bundle bundle = new Bundle();
                    bundle.putInt(StepDetailFragment.ARG_STEP_POSITION_ID, position);
                    bundle.putParcelableArrayList(RecipeItemDetailFragment.ARG_STEPS, stepArrayList);

                    if(mTwoPane){
                        StepDetailFragment fragment = new StepDetailFragment();
                        fragment.setPosition(position);
                        fragment.setSteps(stepArrayList);
                        fragment.setTwoPane(true);

                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.sw600, fragment, null)
                                .commit();

                    }else {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, StepDetailActivity.class);
                        intent.putExtra(Constants.BUNDLE_RECIPE, bundle);
                        context.startActivity(intent);

                    }

                }
            });
        }
    }
}
