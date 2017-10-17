package android.daehoshin.com.jsondata;

import android.daehoshin.com.jsondata.domain.User;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();

    }

    private void load(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return Remote.getData("https://api.github.com/users");
            }

            @Override
            protected void onPostExecute(String jsonString) {
                // jsonString 을 parsing
                Log.d("RESULT", jsonString);
                data = parseGson(jsonString);

                setList();
            }
        }.execute();
    }

    private List<User> parseGson(String string){
        List<User> result = new ArrayList<>();

        Gson gson = new Gson();
        result = gson.fromJson(string, result.getClass());

        return result;
    }

    private List<User> parse(String string){
        List<User> result = new ArrayList<>();

        string = string.substring(string.indexOf("{") + 1, string.lastIndexOf("}"));
        String[] users = string.split("\\},\\{");
        for(String items : users){
            User user = new User();

            user.login = getValue(items, "login");
            user.id = Integer.parseInt(getValue(items, "id"));
            user.avatar_url = getValue(items, "avatar_url");


            result.add(user);
        }

        return result;
    }

    private String getValue(String items, String findKeyName){
        findKeyName = "\"" + findKeyName + "\"";
        items = items.substring(items.indexOf(findKeyName));
        items = items.substring(findKeyName.length() + 1, items.indexOf(","));
        if(items.startsWith("\"") && items.endsWith("\"")) items = items.substring(1, items.length() - 1);

        return items;
    }

    List<User> data;
    private void setList(){
        UserAdapter adapter = new UserAdapter(data);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
