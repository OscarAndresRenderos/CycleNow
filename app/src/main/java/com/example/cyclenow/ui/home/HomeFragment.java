package com.example.cyclenow.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.cyclenow.R;
import com.example.cyclenow.databinding.FragmentHomeBinding;
import com.example.cyclenow.ui.home.RegistrationActivity; // Import for RegistrationActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private GoogleSignInClient googleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button googleSignInButton = binding.googleSignInButton;
        Button registerButton = binding.registerButton; // Button for registration

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("857486136664-4lv4hp0bbdbimc1d6c384ou2glp2koll.apps.googleusercontent.com") // Add your client ID here
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        // Handle register button click
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegistrationActivity.class);
            startActivity(intent);
        });

        return root;
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String firstName = account.getGivenName();
                String lastName = account.getFamilyName();
                String email = account.getEmail();
                String idToken = account.getIdToken();

                // You can now use this information to create or update the user in your backend
                Toast.makeText(getContext(), "Signed in as: " + email, Toast.LENGTH_SHORT).show();

                // Optionally, navigate to another activity or update UI
            }
        } catch (ApiException e) {
            Toast.makeText(getContext(), "Sign-in failed: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}