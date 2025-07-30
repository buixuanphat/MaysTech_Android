package com.example.maystech.ui.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.maystech.R;
import com.example.maystech.data.model.Comment;
import com.example.maystech.data.model.Feedback;
import com.example.maystech.data.model.Rating;
import com.example.maystech.data.model.User;
import com.example.maystech.data.repository.CommentRepository;
import com.example.maystech.data.repository.RatingRepository;
import com.example.maystech.databinding.ActivityFeedbackBinding;
import com.example.maystech.utils.SharedPrefManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FeedbackActivity extends AppCompatActivity {

    ActivityFeedbackBinding binding;
    List<Feedback> productList;
    FeedbackViewModel viewModel;
    SharedPrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        prefManager = SharedPrefManager.getInstance(this);
        User user = prefManager.getUserInfo();

        viewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);


        productList = new ArrayList<>();
        FeedbackAdapter feedbackAdapter = new FeedbackAdapter(this);
        binding.rvFeedback.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFeedback.setAdapter(feedbackAdapter);

        Intent intent = getIntent();
        viewModel.fetchListProduct(intent.getIntExtra("deliveryId", -1));

        viewModel.getProductList().observe(this, l ->
        {
            productList.clear();
            List<Feedback> newList = new ArrayList<>();
            for (int i = 0; i < l.size(); i++) {
                newList.add(new Feedback(l.get(i), null, null));
            }
            productList.addAll(newList);
            feedbackAdapter.setData(productList);
        });


        binding.btnFeedback.setOnClickListener(v ->
        {
            AtomicInteger countItem = new AtomicInteger(0);
            productList.forEach(feedback ->
            {
                if (feedback.getComment() != null && !feedback.getComment().trim().isEmpty() && feedback.getComment().trim().length() <= 100) {
                    countItem.incrementAndGet();
                }
            });
            if (countItem.get() < productList.size()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                productList.forEach(feedback ->
                {
                    Comment comment = new Comment();
                    comment.setContent(feedback.getComment());
                    comment.setProductId(feedback.getProduct().getProductId());
                    viewModel.createComments(user.getId(), comment);

                    Rating rating = new Rating();
                    rating.setRating(feedback.getRating());
                    rating.setProductId(feedback.getProduct().getProductId());
                    viewModel.createRating(user.getId(), rating);
                });
            }

            viewModel.getTaskSuccess().observe(this, t ->
            {
                if (t == -1) {
                    Toast.makeText(this, "Lỗi! Đánh giá không thành công", Toast.LENGTH_SHORT).show();
                } else if (t == productList.size() * 2) {
                    viewModel.updateFeedbackStatus(intent.getIntExtra("deliveryId", -1));
                } else if (t == productList.size() * 2 + 1) {
                    Toast.makeText(this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        });


    }
}