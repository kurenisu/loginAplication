package com.example.conf;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// フォーム認証の設定記述開始
        http.formLogin(login -> login
        		// ログイン画面のURL        		
        		.loginPage("/login").permitAll()
        		// ログイン成功後のリダイレクト先URL        		
        		.defaultSuccessUrl("/toppage",true)
        		// ログインページで指定したユーザ名を入力する項目
        		.usernameParameter("accountId")
        		// ログインページで指定したパスワードを入力する項目
        		.passwordParameter("pw")
        		)
		// ログアウトの設定記述開始
        .logout(logout -> logout
        		// ログアウト成功後のリダイレクト先URL
        		.logoutSuccessUrl("/login?logout")
        		// ログアウト時にHTTPセッションを無効
        		.invalidateHttpSession(true)
        		)
		// URLごとの認可設定記述開始
        .authorizeHttpRequests(authz -> authz
        		// "/css/**"などはログイン無しでもアクセス可能
        		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        		// "/signup"はログイン無しでもアクセス可能
        		.requestMatchers("/signup").permitAll()
        		// "/user"はROLE_USERのみアクセス可能
        		.requestMatchers("/user").hasRole("USER")
        		// "/admin"はROLE_ADMINのみアクセス可能
        		.requestMatchers("/admin").hasRole("ADMIN")
        		// 他のURLはログイン後にアクセス可能
        		.anyRequest().authenticated()
        );
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
