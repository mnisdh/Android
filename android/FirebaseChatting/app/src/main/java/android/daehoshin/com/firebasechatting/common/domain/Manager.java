package android.daehoshin.com.firebasechatting.common.domain;

import android.daehoshin.com.firebasechatting.common.Db;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 11. 3..
 */

public class Manager {
    private static Manager manager;
    public static Manager getInstance(){
        if(manager == null) manager = new Manager();

        return manager;
    }

    public static boolean existUser(String email){
        email = email.replace(".", "_");
        //getInstance().user_emailRef.child(email)
        return false;
    }

    public static void getUser(String email, final User_Callback callback){
        email = email.replace(".", "_");
        getInstance().user_emailRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.getUser(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.getUser(null);
            }
        });
    }

    public static void getUser(FirebaseUser user, final User_Callback callback){
        getInstance().userRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.getUser(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.getUser(null);
            }
        });
    }

    public static void addUser(User user){
        getInstance().userRef.child(user.getId()).setValue(user);
        getInstance().user_emailRef.child(user.getEmail().replace(".", "_")).setValue(user);
    }


    private Manager(){

    }

    private User currentUser;
    private List<User> friends = null;
    private DatabaseReference friendRef = null;
    private DatabaseReference userRef = Db.getInstance().getReference("user");
    private DatabaseReference user_emailRef = Db.getInstance().getReference("user_email");

    public void setCurrentUser(User user){
        currentUser = user;
        friends = null;
        friendRef = null;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    //    public void addFriend(String email){
    //        //Db.getInstance().getReference("user/"+)
    //    }
    public void addFriend(User user){
        DatabaseReference nfriendRef = Db.getInstance().getReference("user_friend/" + currentUser.getId() + "/" + user.getId());
        nfriendRef.setValue(user);
    }

    public void getFriend(boolean isReload, final Friend_Callback callback){
        if(friends == null || friendRef == null) {
            isReload = true;
            friendRef = Db.getInstance().getReference("user_friend/" + currentUser.id);
            friends = new ArrayList<>();
        }

        friends.clear();

        if(isReload) {
            friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        friends.add(item.getValue(User.class));
                    }

                    callback.getFriendList(friends);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else callback.getFriendList(friends);
    }


    public interface Friend_Callback{
        void getFriendList(List<User> friends);
    }
    public interface User_Callback{
        void getUser(User user);
    }
}
