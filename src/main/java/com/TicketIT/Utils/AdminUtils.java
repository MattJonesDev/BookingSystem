package com.TicketIT.Utils;

import com.TicketIT.DataAccessObject.MongoDBMemberDAO;
import javax.servlet.http.Cookie;

public class AdminUtils {

    /**
     * Checks if the user is allowed to access admin pages.
     *
     * @param memberDAO Member Data Access Object.
     * @param cookies A list of the user's cookies.
     * @return True/False whether they are allowed to access.
     */
    public static Boolean IsMemberAllowedAccess(MongoDBMemberDAO memberDAO, Cookie[] cookies){
        try{
            // Find the memberId cookie
            String cookieMemberId = "none";
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("memberId"))
                    cookieMemberId = cookie.getValue();
            }

            // If the memberId cookie doesn't exist, they are not allowed.
            if(cookieMemberId.equals("none"))
                return false;

            // If the member is not an admin, they are not allowed here.
            if(!memberDAO.GetMemberById(cookieMemberId).getIsAdmin())
                return false;

            // If the member is an admin, they are allowed.
            if(memberDAO.GetMemberById(cookieMemberId).getIsAdmin())
                return true;
        }catch(Exception ex){
            // If there is any error, they are not allowed here.
            return false;
        }

        return false;
    }
}
