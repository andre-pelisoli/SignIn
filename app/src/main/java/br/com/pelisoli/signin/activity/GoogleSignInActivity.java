package br.com.pelisoli.signin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Activity that is the basis of google account sign in.
 */
public abstract class GoogleSignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
	private GoogleSignInOptions mGoogleSignInOptions;

	public static final int RC_SIGN_IN = 9001;


	public GoogleSignInOptions getGoogleSignInOption(){
		if(mGoogleSignInOptions == null){
			// Configure sign-in to request the user's ID, email address, and basic
			// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
			mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
					.requestEmail()
					.build();
		}

		return mGoogleSignInOptions;
	}

	public GoogleApiClient getGoogleApiClient(){
			// Build activity_login GoogleApiClient with access to the Google Sign-In API and the
			// options specified by gso.
			return new GoogleApiClient.Builder(this)
					.enableAutoManage(this, this)
					.addApi(Auth.GOOGLE_SIGN_IN_API, getGoogleSignInOption())
					.build();

	}

	public void signInGoogle(GoogleApiClient googleApiClient) {
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}

	public void signOutGoogle(GoogleApiClient googleApiClient) {
		if(googleApiClient.isConnected()) {
			Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
					new ResultCallback<Status>() {
						@Override
						public void onResult(Status status) {
							finish();
						}
					});
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}

	public static int getRcSignIn() {
		return RC_SIGN_IN;
	}
}
