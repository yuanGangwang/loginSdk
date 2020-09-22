package com.guuidea.im.api;

public class APIConstant {
    //    private final static String serverPath = "http://47.110.12.104:9000";
    private final static String serverPath = "http://api-gateway.globalneutron.com";

    //添加好友
    public final static String addFriend = serverPath + "/player/social/friends/add-friend";

    //删除好友
    public final static String deleteFriend = serverPath+"/player/social/friends/delete-friend";

    //获取用户的好友列表
    public final static String getUserFriendsList = serverPath+"/player/social/friends/list";

    //同意好友申请
    public final static String acceptFriendApply = serverPath + "/player/social/friends/accept-friend-apply";

    //拒绝好友申请
    public final static String rejectFriendApply = serverPath+"/player/social/friends/reject-friend-apply";

    //好友申请列表(我申请别人)
    public final static String myApplyList = serverPath+"/player/social/friends/apply-list-me-to-others";

    // 我的申请列表
    public final static String toMeApplyList = serverPath+"/player/social/friends/apply-list-others-to-me";

}
