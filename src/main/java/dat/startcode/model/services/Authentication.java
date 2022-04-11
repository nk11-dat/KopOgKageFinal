package dat.startcode.model.services;

import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Authentication
{
    public static boolean isRoleAllowed(int requiredRoleId, HttpServletRequest request) throws DatabaseException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRoleId() == requiredRoleId)
            return true;
        else
            throw new DatabaseException("Du har ikke de påkrævede rettigheder til at tilgå denne side!");
    }
}
