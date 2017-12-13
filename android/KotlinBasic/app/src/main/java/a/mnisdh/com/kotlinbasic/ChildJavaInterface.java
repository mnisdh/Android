package a.mnisdh.com.kotlinbasic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by daeho on 2017. 12. 12..
 */

public class ChildJavaInterface implements IParent {
    @NotNull
    @Override
    public String get() {
        return null;
    }

    @Nullable
    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(@Nullable String s) {
        User user = new User("", 1, "");
    }

    @Override
    public void set(@NotNull String a) {

    }
}

