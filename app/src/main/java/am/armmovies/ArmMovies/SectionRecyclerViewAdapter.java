package am.armmovies.ArmMovies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by argishti on 1/16/17.
 */

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.itemRow> {
    private ArrayList<SectionViewModel> dataList;
    private Context context;

    public SectionRecyclerViewAdapter(Context context, ArrayList<SectionViewModel> dataList) {
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public itemRow onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list_item, null);
        itemRow mh = new itemRow(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(itemRow holder, int position) {

        final String sectionName = dataList.get(position).getSectionTitle();
        ArrayList singleSectionItems = dataList.get(position).getAllMoviesInSection();
        holder.setSectionTitle(sectionName);
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(context, singleSectionItems);
        holder.getRecyclerViewList().setHasFixedSize(true);
        holder.getRecyclerViewList().setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.getRecyclerViewList().setAdapter(itemListDataAdapter);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class itemRow extends RecyclerView.ViewHolder {

        private TextView sectionTitle;
        private RecyclerView recyclerViewList;

        public itemRow(View view) {
            super(view);

            this.sectionTitle = (TextView) view.findViewById(R.id.section_title);
            this.recyclerViewList = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        }

        public TextView getSectionTitle() {
            return sectionTitle;
        }

        public void setSectionTitle(String sectionTitle) {
            this.sectionTitle.setText(sectionTitle);
        }

        public RecyclerView getRecyclerViewList() {
            return recyclerViewList;
        }

        public void setRecyclerViewList(RecyclerView recyclerViewList) {
            this.recyclerViewList = recyclerViewList;
        }
    }
}
