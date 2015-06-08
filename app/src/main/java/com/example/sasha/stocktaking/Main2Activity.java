package com.example.sasha.stocktaking;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sasha.stocktaking.repository.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    public final static String SER_KEY = "com.example.sasha.osmdroid.types.ser";
    RecyclerView recyclerView;
    PlaceRecyclerViewAdapter viewAdapter;
    ArrayList<Item> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        final ActionBar ab = getSupportActionBar();

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        viewAdapter = new PlaceRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(viewAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(),ContinuousCaptureActivity.class),1);
                //startActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
         Item[] itemsArr = (Item[]) data.getSerializableExtra(SER_KEY);
        items.addAll(Arrays.asList(itemsArr));
        viewAdapter.notifyDataSetChanged();
        Log.d(MainActivity.LOG_TAG,Arrays.toString(itemsArr) );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static class PlaceRecyclerViewAdapter
            extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Item> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final TextView textViewTitle;
            public final TextView textViewNotes;
            public final TextView textViewDate;
            public final TextView textViewInvNum;
            public final RatingBar ratingBar;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                textViewNotes = (TextView)view.findViewById(R.id.card_notes);
                textViewTitle = (TextView)view.findViewById(R.id.card_title);
                textViewDate= (TextView)view.findViewById(R.id.card_date);
                textViewInvNum = (TextView)view.findViewById(R.id.card_invnum);
                ratingBar= (RatingBar)view.findViewById(R.id.ratingBar);
            }


        }

        public Item getValueAt(int position) {
            return mValues.get(position);
        }

        public PlaceRecyclerViewAdapter(Context context, List<Item> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            // holder.mBoundString = mValues.get(position);
            Item item = mValues.get(position);
            holder.textViewTitle.setText(item.getTitle());
            holder.textViewNotes.setText(item.getNote());
            holder.textViewInvNum.setText("inventory number: "+ item.getInvNum());
            holder.textViewDate.setText(new SimpleDateFormat("dd MMM yyyy").format(item.getChanged()));
            holder.ratingBar.setRating(item.getState().ordinal()+1);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, CheeseDetailActivity.class);
//                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                    context.startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
