package com.example.starsgallery.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starsgallery.R;
import com.example.starsgallery.adapter.GalleryStarAdapter;
import com.example.starsgallery.service.StarDataService;

public class MainGalleryActivity extends AppCompatActivity {

    private RecyclerView galleryRecyclerView;
    private GalleryStarAdapter starAdapter;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_gallery);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("⭐ Galerie de Stars");
        }

        galleryRecyclerView = findViewById(R.id.galleryRecyclerView);
        galleryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        starAdapter = new GalleryStarAdapter(this,
                StarDataService.getInstance().fetchAll());
        galleryRecyclerView.setAdapter(starAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchField = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchField.setQueryHint("Rechercher une star...");
        searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newQuery);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        int itemId = selectedItem.getItemId();

        if (itemId == R.id.menu_share) {
            String shareContent = "Découvrez la Galerie de Stars ! 🌟";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle("Partager via...")
                    .setText(shareContent)
                    .startChooser();
            Toast.makeText(this, "Partage de l'application", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(selectedItem);
    }
}