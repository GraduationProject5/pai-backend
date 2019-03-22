package backend.util.loginhelper;

import backend.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserSession {

    private static List<String> userList = new ArrayList<>();

    public static boolean online(User user){
        String userEmail = user.getEmail() ;
        for(String unit : userList )
        {
            if(userEmail.equals(unit)) {
                return false;
            }
        }
        return userList.add(userEmail) ;
    }

    public static boolean offline(String userEmail){
        boolean op = false ;
        for(String unit : userList) {
            if(userEmail.equals(unit) )
            {
                op = true;
                break ;
            }
        }
        if(op) {
            userList.remove(userEmail);
        }
        return true ;
    }


}
