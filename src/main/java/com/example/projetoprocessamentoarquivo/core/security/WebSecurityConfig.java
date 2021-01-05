package com.example.projetoprocessamentoarquivo.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }




        @Override
        protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers(HttpMethod.POST,"/api/v1/usuarios/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT,"/api/v1/usuarios/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/api/v1/usuarios/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.POST,"/api/v1/operadores/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT,"/api/v1/operadore/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/api/v1/operadore/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.POST,"/api/v1/aplicativodecaptura/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.PUT,"/api/v1/aplicativodecaptura/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/api/v1/aplicativodecaptura/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.GET,"/api/v1/documentos/configuracoesenvio/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.POST,"/api/v1/documentos/arquivo/mobile**")
                        .hasAnyAuthority("ADMIN" , "OPERADORMOBILE")
                        .antMatchers(HttpMethod.POST,"/api/v1/documentos/arquivo/desktop**")
                        .hasAnyAuthority("ADMIN" , "OPERADORDESKTOP")
                        .antMatchers(HttpMethod.POST,"/api/v1/documentos/arquivo/web**")
                        .hasAnyAuthority("ADMIN" , "OPERADORWEB")
                        .anyRequest().authenticated()
                .and()
                .cors().and()
                .oauth2ResourceServer()
                    .jwt()
                    .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        var secretKey = new SecretKeySpec("nwnfxbrebebugbevevhbevgrbubtevbeyteyvbgeuyvbetr".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null){
                authorities = Collections.emptyList();
            }

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return jwtAuthenticationConverter;
    }


}
