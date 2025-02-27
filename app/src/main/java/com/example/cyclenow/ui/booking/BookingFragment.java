package com.example.cyclenow.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cyclenow.databinding.FragmentBookingBinding; // Correct import

public class BookingFragment extends Fragment {

    private FragmentBookingBinding binding; // Correct binding class

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookingViewModel bookingViewModel =
                new ViewModelProvider(this).get(BookingViewModel.class);

        binding = FragmentBookingBinding.inflate(inflater, container, false); // Correct binding class
        View root = binding.getRoot();

        final TextView textView = binding.textGallery; // Ensure this ID matches your XML layout
        bookingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}