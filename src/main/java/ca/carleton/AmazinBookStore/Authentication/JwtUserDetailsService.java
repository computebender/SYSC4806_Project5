package ca.carleton.AmazinBookStore.Authentication;

import ca.carleton.AmazinBookStore.User.User;
import ca.carleton.AmazinBookStore.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public JwtUserDetailsService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User" + username + " not found");
        }

        JwtUserDetails userDetails = new JwtUserDetails();
        userDetails.setUserId(user.getId());
        userDetails.setUsername(user.getEmail());
        userDetails.setPassword(user.getPassword());
        return userDetails;
    }
}
