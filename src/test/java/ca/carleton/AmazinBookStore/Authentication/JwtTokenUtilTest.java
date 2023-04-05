package ca.carleton.AmazinBookStore.Authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "test-secret");
        ReflectionTestUtils.setField(jwtTokenUtil, "expiration", 604800000L);

        userDetails = new UserDetailsImpl("testUser");
    }

    @Test
    void generateToken() {
        String token = jwtTokenUtil.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void extractUsername() {
        String token = jwtTokenUtil.generateToken(userDetails);
        String username = jwtTokenUtil.extractUsername(token);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void extractExpiration() {
        String token = jwtTokenUtil.generateToken(userDetails);
        Date expiration = jwtTokenUtil.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    void validateToken() {
        String token = jwtTokenUtil.generateToken(userDetails);
        assertTrue(jwtTokenUtil.validateToken(token, userDetails));
    }

    @Test
    void validateToken_invalid() {
        String invalidToken = "invalidToken";
        assertFalse(jwtTokenUtil.validateToken(invalidToken, userDetails));
    }

    @Test
    void isTokenExpired() {
        String token = jwtTokenUtil.generateToken(userDetails);
        assertFalse(jwtTokenUtil.isTokenExpired(token));
    }



    private static class UserDetailsImpl implements UserDetails {
        private final String username;

        UserDetailsImpl(String username) {
            this.username = username;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList();
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
