package com.example.benfi.hospital.requests;

import com.android.volley.VolleyError;
import com.example.benfi.hospital.interfaces.OnResponseCallback;
import com.example.benfi.hospital.abstracts.AbstractRequest;
import com.example.benfi.hospital.utils.AppUtils;
import com.example.benfi.hospital.requests.GetUserQuestionnairesRequest.OnUserQuestionnairesReady;

import org.json.JSONException;
import org.json.JSONObject;

public final class GetUserIdRequest extends AbstractRequest implements OnResponseCallback {
    private OnUserQuestionnairesReady onUserQuestionnairesReadyListener;

    public GetUserIdRequest(OnUserQuestionnairesReady onUserQuestionnairesReadyListener) {
        super(AppUtils.getUrl() + "/api/user/get-user-id");
        this.onUserQuestionnairesReadyListener = onUserQuestionnairesReadyListener;
        this.getUserId();
    }

    public void getUserId() {
        this.getStringRequestWrapper().setRequestMethod("GET");

        this.getStringRequestWrapper().addRequestHeaders("Authorization", "Bearer " + AppUtils.getAppToken());

        this.getStringRequestWrapper().setOnResponseCallback(this);
        this.getStringRequestWrapper().start();
    }

    @Override
    public void onSuccessResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            AppUtils.setUserId(jsonResponse.getString("user_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetUserQuestionnairesRequest(onUserQuestionnairesReadyListener);
    }

    @Override
    public void onErrorResponse(VolleyError response) {
        AppUtils.parseErrorMessage(response);
    }
}
