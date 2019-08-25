package com.royalcommission.bs.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royalcommission.bs.R;

import java.util.List;

/**
 * Created by Prashant on 10/17/2018.
 */
public class HospitalGuideMenuAdapter extends RecyclerView.Adapter<HospitalGuideMenuAdapter.HospitalGuideMenuHolder> {

    private Context mContext;
    private List<String> hospitalGuideMenuList;
    private HospitalGuideMenuAdapterClickListener mMenuItemClickListener;
    private int[] imageResources = {
            R.drawable.ic_admission_info, R.drawable.ic_discharge_information,
            R.drawable.ic_hospital_info, R.drawable.ic_surveys
    };

    public HospitalGuideMenuAdapter(Context context, List<String> guideMenuList, HospitalGuideMenuAdapterClickListener menuItemClickListener) {
        this.mContext = context;
        this.hospitalGuideMenuList = guideMenuList;
        this.mMenuItemClickListener = menuItemClickListener;
    }

    @NonNull
    @Override
    public HospitalGuideMenuHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_hospital_guide_row_menu, viewGroup, false);
        return new HospitalGuideMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HospitalGuideMenuHolder hospitalGuideMenuHolder, int position) {
        String name = hospitalGuideMenuList.get(position);
        hospitalGuideMenuHolder.subMenuName.setText(name);
        hospitalGuideMenuHolder.subMenuImage.setImageResource(imageResources[position]);
        hospitalGuideMenuHolder.itemView.setOnClickListener(v -> {
            mMenuItemClickListener.onMenuItemClickListener(hospitalGuideMenuHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return hospitalGuideMenuList.size();
    }

    class HospitalGuideMenuHolder extends RecyclerView.ViewHolder {
        private TextView subMenuName;
        private ImageView subMenuImage;

        HospitalGuideMenuHolder(@NonNull View itemView) {
            super(itemView);
            subMenuName = itemView.findViewById(R.id.menu_name);
            subMenuImage = itemView.findViewById(R.id.menu_image);
        }
    }

    public interface HospitalGuideMenuAdapterClickListener {
        void onMenuItemClickListener(int position);
    }
}
