package android.daehoshin.com.firebasechatting.common.domain;

import android.daehoshin.com.firebasechatting.common.Db;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static void getRoom(String userId, final Room_Callback callback){
        getInstance().user_roomRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                String[] roomids = map.get("room_id").split(",");

                for(String roomid : roomids){
                    getInstance().roomRef.child(roomid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                             dataSnapshot.getValue(Room.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }




                //callback.getRooms(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.getRooms(null);
            }
        });
    }


    private Manager(){

    }

    private User currentUser;
    private List<User> myFriends = null;
    private DatabaseReference myFriendRef = null;
    private List<Room> myRooms = null;
    private DatabaseReference myRoomRef = null;
    private DatabaseReference userRef = Db.getInstance().getReference("user");
    private DatabaseReference user_emailRef = Db.getInstance().getReference("user_email");
    private DatabaseReference user_roomRef = Db.getInstance().getReference("user_room");
    private DatabaseReference roomRef = Db.getInstance().getReference("room");

    public void setCurrentUser(User user){
        currentUser = user;
        myFriends = null;
        myFriendRef = null;
        myRooms = null;
        myRoomRef = null;
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
        if(myFriends == null || myFriendRef == null) {
            isReload = true;
            myFriendRef = Db.getInstance().getReference("user_friend/" + currentUser.id);
            myFriends = new ArrayList<>();
        }

        myFriends.clear();

        if(isReload) {
            myFriendRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        myFriends.add(item.getValue(User.class));
                    }

                    callback.getFriendList(myFriends);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else callback.getFriendList(myFriends);
    }

    public void addRoom(Room room, List<User> friends){
        // room 추가
        String key = roomRef.push().getKey();
        room.setId(key);
        roomRef.child(key).setValue(room);

        // user room 추가
        DatabaseReference nRoomRef = Db.getInstance().getReference("user_room/" + currentUser.getId() + "/" + room.getId());
        nRoomRef.setValue(room);

        // room member 추가
        DatabaseReference temp = Db.getInstance().getReference("room_member/" + room.getId() + "/" + currentUser.getId());
        temp.setValue(currentUser);
        for(User friend : friends){
            temp = Db.getInstance().getReference("room_member/" + room.getId() + "/" + friend.getId());
            temp.setValue(friend);
        }
    }

    public void getRoom(boolean isReload, final Room_Callback callback){
        if(myRooms == null || myRoomRef == null) {
            isReload = true;
            myRoomRef = Db.getInstance().getReference("user_room/" + currentUser.id);
            myRooms = new ArrayList<>();
        }

        myRooms.clear();

        if(isReload) {
            myRoomRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        myRooms.add(item.getValue(Room.class));
                    }

                    callback.getRooms(myRooms);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else callback.getRooms(myRooms);
    }

    public interface Friend_Callback{
        void getFriendList(List<User> friends);
    }
    public interface User_Callback{
        void getUser(User user);
    }
    public interface Room_Callback{
        void getRooms(List<Room> rooms);
    }
}
