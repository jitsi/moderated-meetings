/*
 * Moderated meetings.
 *
 * Copyright @ 2023 - present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jitsi.moderated;

import com.fasterxml.jackson.databind.*;
import org.apache.commons.logging.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.web.authentication.*;

import javax.servlet.http.*;
import java.net.*;
import java.util.*;

/**
 * Configures the web security if TOKEN_AUTH_URL is set.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Log logger = LogFactory.getLog(this.getClass());

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // skip security setting if not configured
        if (Config.getTokenAuthUrl() == null) {
            return;
        }

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            try {
                                String requestPath = new URI(request.getRequestURI()).getPath();

                                if (requestPath.startsWith("/")) {
                                    requestPath = requestPath.substring(1);
                                }

                                String url = Config.getTokenAuthUrl();

                                Map<String,String> payload = new HashMap<>();
                                payload.put("room", requestPath);

                                url = url.replace("{state}", new ObjectMapper().writeValueAsString(payload));

                                response.setHeader("Location", new URI(null, url, null).toASCIIString());
                                response.setStatus(HttpStatus.FOUND.value());

                                return;

                            } catch (URISyntaxException e) {
                                logger.error(e);
                            }

                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Our public endpoints
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/app.js").permitAll()
                .antMatchers("/assets/*").permitAll()
                .antMatchers("/rest/rooms").permitAll()
                .antMatchers("/rest/config").permitAll()
                // Our private endpoints
                .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
