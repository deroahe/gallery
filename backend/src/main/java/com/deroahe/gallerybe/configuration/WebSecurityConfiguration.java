//package com.deroahe.gallerybe.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("password")).roles("USER", "ADMIN");
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
//                .antMatchers(HttpMethod.DELETE,"/api/**").permitAll()
//                .antMatchers(HttpMethod.PUT,"/api/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin()
//                .loginPage("/api/login").permitAll();
//    }
//
////    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().disable().authorizeRequests()
////                .antMatchers("/", "/users").access("hasRole('USER')")
////                .antMatchers("/api/**").hasRole("ADMIN")
////                .anyRequest().authenticated()
////                .and().formLogin()
////                .loginPage("/api/login").permitAll()
////                .and().httpBasic();
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests()
////                .antMatchers("/", "/users").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .loginPage("/api/login")
////                .permitAll()
////                .and()
////                .logout()
////                .permitAll();
////    }
////
////    @Bean
////    @Override
////    public UserDetailsService userDetailsService() {
////        UserDetails user =
////                User.withDefaultPasswordEncoder()
////                        .username("user")
////                        .password("password")
////                        .roles("USER")
////                        .build();
////
////        return new InMemoryUserDetailsManager(user);
////    }
//
////    @Autowired
////    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
////
////    @Autowired
////    private LoginSuccessHandler loginSuccessHandler;
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests().antMatchers("/api/**").authenticated()
////                .and()
////                .httpBasic()
////                .and()
////                .exceptionHandling()
////                .authenticationEntryPoint(restAuthenticationEntryPoint)
////                .and()
////                .formLogin()
////                .loginProcessingUrl("/api/login")
////                .successHandler(loginSuccessHandler)
////                .failureHandler(new SimpleUrlAuthenticationFailureHandler());
////        http.csrf().ignoringAntMatchers("/api/login").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
////    }
////
////    @Bean
////    public UserDetailsService userDetailsService() {
////        String username = "user";
////        String password = "password";
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        String encodedPassword = passwordEncoder().encode(password);
////        manager.createUser(User.withUsername(username).password(encodedPassword).roles("USER").build());
////        return manager;
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//}