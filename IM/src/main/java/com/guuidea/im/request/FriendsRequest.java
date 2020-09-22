package com.guuidea.im.request;

import com.google.gson.JsonObject;
import com.guuidea.im.api.APIConstant;
import com.guuidea.im.config.FLyIMClient;
import com.guuidea.net.CallBackUtil;
import com.guuidea.net.UrlHttpUtil;

import java.util.Map;

public class FriendsRequest {

    private static FriendsRequest friendsRequest;

    private static Map<String, String> header;

    public static FriendsRequest getInstance() {
        if (friendsRequest == null) {
            try {
                friendsRequest = new FriendsRequest();
                header.put("appKey", FLyIMClient.getAppKey());
                header.put("authToken", FLyIMClient.getAuthToken());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return friendsRequest;
    }


    public void addFriend(String friendUserId, final FriendsCallBack<String> callBack) {
        JsonObject json = new JsonObject();
        json.addProperty("friendUserId", friendUserId);
        UrlHttpUtil.postJson(APIConstant.addFriend, json.toString()
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 0) {
                            callBack.success("success");
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public void deleteFriend(String friendUserId,final FriendsCallBack<String> callBack) {
        JsonObject json = new JsonObject();
        json.addProperty("friendUserId", friendUserId);
        UrlHttpUtil.postJson(APIConstant.deleteFriend, json.toString()
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 0) {
                            callBack.success("success");
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public void getUserFriendsList(final FriendsCallBack<String> callBack) {
        UrlHttpUtil.postJson(APIConstant.getUserFriendsList, null
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 0) {
                            callBack.success("success");
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public void acceptFriendApply(String friendUserId,final FriendsCallBack<String> callBack) {
        JsonObject json = new JsonObject();
        json.addProperty("friendUserId", friendUserId);
        UrlHttpUtil.postJson(APIConstant.acceptFriendApply, json.toString()
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 0) {
                            callBack.success("success");
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public void rejectFriendApply(String friendUserId,final FriendsCallBack<String> callBack) {
        JsonObject json = new JsonObject();
        json.addProperty("friendUserId", friendUserId);
        UrlHttpUtil.postJson(APIConstant.rejectFriendApply, json.toString()
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 0) {
                            callBack.success("success");
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public void myApplyList(final FriendsCallBack<String> callBack) {
        JsonObject json = new JsonObject();
        UrlHttpUtil.postJson(APIConstant.rejectFriendApply, null
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 0) {
                            callBack.success("success");
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public void toMeApplyList(final FriendsCallBack<String> callBack) {
        JsonObject json = new JsonObject();
        UrlHttpUtil.postJson(APIConstant.rejectFriendApply, null
                , header, new CallBackUtil() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callBack.onFail(throwable);
                    }

                    @Override
                    public void onResponse(JsonObject response) {
                        if (response.get("code").getAsInt() == 0) {
                            callBack.success("success");
                        } else {
                            callBack.onFail(new Throwable(response.get("msg").getAsString()));
                        }
                    }
                });
    }

    public interface FriendsCallBack<T> {
        void success(T t);

        void onFail(Throwable throwable);
    }
}
