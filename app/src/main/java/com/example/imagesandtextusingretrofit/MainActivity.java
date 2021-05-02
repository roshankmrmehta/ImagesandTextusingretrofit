package com.example.imagesandtextusingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<ImagesResponse> imagesResponseList = new ArrayList<>();

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        getAllImages();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               startActivity(new Intent(MainActivity.this,ClickedActivity.class).putExtra("data", imagesResponseList.get(i)));
            }
        });
    }

    public void getAllImages(){
        Call<List<ImagesResponse>> imagesresponse = ApiClient.getInterface().getAllImages();

        imagesresponse.enqueue(new Callback<List<ImagesResponse>>() {
            @Override
            public void onResponse(Call<List<ImagesResponse>> call, Response<List<ImagesResponse>> response) {

                if(response.isSuccessful()){
                    String message = "Request Successful.. ";
                    Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();

                    imagesResponseList = response.body();
                    CustomAdapter  customAdapter = new CustomAdapter(imagesResponseList,MainActivity.this);

                    gridView.setAdapter(customAdapter);

                }else{
                    String message = "An error occurred try again later ..";
                    Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ImagesResponse>> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomAdapter extends BaseAdapter{

        private List<ImagesResponse> imagesResponseList;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(List<ImagesResponse> imagesResponseList, Context context) {
            this.imagesResponseList = imagesResponseList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagesResponseList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view == null){
                view = layoutInflater.inflate(R.layout.row_grid_items,viewGroup,false);
            }

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView textView = view.findViewById(R.id.textView);

            textView.setText(imagesResponseList.get(i).getName());

            Glide.with(context)
                    .load(imagesResponseList.get(i).getUrl())
                    .into(imageView);

            return view;
        }
    }
}