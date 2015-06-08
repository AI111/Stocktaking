
package com.example.sasha.stocktaking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.example.sasha.stocktaking.repository.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PlacesListFragment extends Fragment {

    ArrayList<Place> places = new ArrayList<>(Arrays.asList(new Place("Title", Place.Type.LABORATORY,new Date())
    ,new Place("Title1", Place.Type.AUDITORY,new Date())
    ,new Place("Title2", Place.Type.UTILITY_ROOM,new Date())
    ,new Place("Title3", Place.Type.LABORATORY,new Date())
    ,new Place("Title4", Place.Type.AUDITORY,new Date())
            ,new Place("Title1", Place.Type.AUDITORY,new Date())
            ,new Place("Title2", Place.Type.UTILITY_ROOM,new Date())
            ,new Place("Title3", Place.Type.LABORATORY,new Date())
            ,new Place("Title4", Place.Type.AUDITORY,new Date())
    ,new Place("Title5", Place.Type.LABORATORY,new Date())));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_places_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new PlaceRecyclerViewAdapter(getActivity(),
               places));
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class PlaceRecyclerViewAdapter
            extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Place> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image_view);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public Place getValueAt(int position) {
            return mValues.get(position);
        }

        public PlaceRecyclerViewAdapter(Context context, List<Place> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
           // holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position).getTitle());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, Main2Activity.class);
                    //intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);

                    context.startActivity(intent);
                }
            });

            TextDrawable drawable = null;
           switch (mValues.get(position).getType()){
               case AUDITORY:
                    drawable = TextDrawable.builder()
                           .buildRound("A",Color.parseColor("#f44336"));
                   break;
               case LABORATORY:
                    drawable = TextDrawable.builder()
                           .buildRound("L", Color.parseColor("#cddc39"));
                   break;
               case UTILITY_ROOM:
                   drawable = TextDrawable.builder()
                           .buildRound("U", Color.parseColor("#00bcd4"));
                   break;
           }
            holder.mImageView.setImageDrawable(drawable);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
