package com.wallet.app.security;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.repo.UserAccountRepo;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserAccountRepo<UserAccount> userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        com.wallet.app.persist.entity.UserAccount user = userService.findUser(authentication.getName());
        if( user != null && !user.isVerify()){
        	SecurityUtils.sendResponse(response, HttpServletResponse.SC_NOT_FOUND, user);
        }
        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, user);
    }
}
