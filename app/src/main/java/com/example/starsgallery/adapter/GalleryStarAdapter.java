package com.example.starsgallery.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.starsgallery.R;
import com.example.starsgallery.service.StarDataService; // ✅ IMPORT CORRECT

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GalleryStarAdapter extends RecyclerView.Adapter<GalleryStarAdapter.ViewHolder>
        implements Filterable {

    private final Context appContext;
    private List<StarModel> originalList;
    private List<StarModel> filteredList;
    private SearchFilter searchFilter;

    public GalleryStarAdapter(Context appContext, List<StarModel> originalList) {
        this.appContext = appContext;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>(originalList);
        this.searchFilter = new SearchFilter(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container, int viewType) {
        View itemLayout = LayoutInflater.from(appContext)
                .inflate(R.layout.gallery_item, container, false);
        final ViewHolder viewHolder = new ViewHolder(itemLayout);

        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                displayRatingPopup(clickedView, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        StarModel currentStar = filteredList.get(index);

        viewHolder.nameDisplay.setText(currentStar.getFullName().toUpperCase());
        viewHolder.ratingDisplay.setRating(currentStar.getUserRating());
        viewHolder.idDisplay.setText(String.valueOf(currentStar.getUniqueId()));

        Glide.with(appContext)
                .load(currentStar.getPhotoUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(viewHolder.photoDisplay);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private void displayRatingPopup(View itemView, int itemPosition) {
        View popupLayout = LayoutInflater.from(appContext)
                .inflate(R.layout.dialog_edit_rating, null, false);

        final CircleImageView popupPhoto = popupLayout.findViewById(R.id.dialogPhoto);
        final RatingBar popupRatingBar = popupLayout.findViewById(R.id.dialogRatingBar);
        final TextView popupId = popupLayout.findViewById(R.id.dialogId);

        RatingBar itemRatingBar = itemView.findViewById(R.id.scoreIndicator);
        TextView itemIdView = itemView.findViewById(R.id.hiddenIdField);

        StarModel currentStar = filteredList.get(itemPosition);
        Glide.with(appContext)
                .load(currentStar.getPhotoUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(popupPhoto);

        popupRatingBar.setRating(itemRatingBar.getRating());
        popupId.setText(itemIdView.getText().toString());

        AlertDialog ratingDialog = new AlertDialog.Builder(appContext)
                .setTitle("Modifier l'évaluation")
                .setMessage("Attribuez une note de 1 à 5 étoiles :")
                .setView(popupLayout)
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float newRating = popupRatingBar.getRating();
                        int starId = Integer.parseInt(popupId.getText().toString());

                        StarModel starToUpdate = StarDataService.getInstance().fetchById(starId);
                        if (starToUpdate != null) {
                            starToUpdate.setUserRating(newRating);
                            StarDataService.getInstance().modify(starToUpdate);
                            notifyItemChanged(itemPosition);
                        }
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();

        ratingDialog.show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView photoDisplay;
        TextView nameDisplay;
        RatingBar ratingDisplay;
        TextView idDisplay;

        ViewHolder(@NonNull View itemLayout) {
            super(itemLayout);
            photoDisplay = itemLayout.findViewById(R.id.profilePhoto);
            nameDisplay = itemLayout.findViewById(R.id.celebrityName);
            ratingDisplay = itemLayout.findViewById(R.id.scoreIndicator);
            idDisplay = itemLayout.findViewById(R.id.hiddenIdField);
        }
    }

    public class SearchFilter extends Filter {
        private final RecyclerView.Adapter parentAdapter;

        public SearchFilter(RecyclerView.Adapter parentAdapter) {
            this.parentAdapter = parentAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence searchQuery) {
            List<StarModel> matchingResults = new ArrayList<>();

            if (searchQuery == null || searchQuery.length() == 0) {
                matchingResults.addAll(originalList);
            } else {
                String queryPattern = searchQuery.toString().toLowerCase().trim();
                for (StarModel star : originalList) {
                    if (star.getFullName().toLowerCase().startsWith(queryPattern)) {
                        matchingResults.add(star);
                    }
                }
            }

            FilterResults filterOutcome = new FilterResults();
            filterOutcome.values = matchingResults;
            filterOutcome.count = matchingResults.size();
            return filterOutcome;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults outcome) {
            filteredList = (List<StarModel>) outcome.values;
            parentAdapter.notifyDataSetChanged();
        }
    }

    public static class StarModel {

        private int uniqueId;
        private String fullName;
        private String photoUrl;
        private float userRating;
        private static int idGenerator = 0;

        public StarModel(String fullName, String photoUrl, float userRating) {
            this.uniqueId = ++idGenerator;
            this.fullName = fullName;
            this.photoUrl = photoUrl;
            this.userRating = userRating;
        }

        public int getUniqueId() {
            return uniqueId;
        }

        public String getFullName() {
            return fullName;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public float getUserRating() {
            return userRating;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public void setUserRating(float userRating) {
            this.userRating = userRating;
        }
    }
}