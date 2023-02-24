package kr.co.book.list.api.config

import kr.co.book.list.domain.repository.user.UserRepository
import kr.co.book.list.lib.security.UserDetailsServiceImpl
import kr.co.book.list.lib.security.jwt.JwtAuthFilter
import kr.co.book.list.lib.security.jwt.JwtProcessor
import kr.co.book.list.lib.security.jwt.JwtProperties
import kr.co.book.list.lib.utils.MessageUtil
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 스프링 시큐리티 설정
 */
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfig(
    private val jwtProcessor: JwtProcessor,
    private val userRepository: UserRepository,
) : WebSecurityConfigurerAdapter() {

    /**
     * 규칙설정
     */
    override fun configure(http: HttpSecurity) {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf()
            .disable()
            // API 통신 들어왔을 때 filter
            .addFilterBefore(JwtAuthFilter(jwtProcessor), UsernamePasswordAuthenticationFilter::class.java)
            // 예외처리 및 접근 권한이 없을 때
            .exceptionHandling()
            .authenticationEntryPoint(JwtAuthenticationEntryPoint())
            .accessDeniedHandler(CustomAccessDeniedHandler())
            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            // API 통신이 가능한 목록 정의
            .antMatchers(*swaggerAllowedList()).permitAll()
            .antMatchers(*devAllowedList()).permitAll()
            .antMatchers(HttpMethod.GET, *signAllowedList()).permitAll()
            .antMatchers(HttpMethod.POST, *signAllowedList()).permitAll()
            .antMatchers(HttpMethod.PATCH, *signAllowedList()).permitAll()
            .antMatchers(HttpMethod.PUT, *signAllowedList()).permitAll()
            .antMatchers(*sysAdminAllowedList()).hasAnyRole("SYS_ADMIN")
            .anyRequest().authenticated()
            .and()
            .headers()
            .addHeaderWriter(XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))

    }

    /**
     * 인증, 인가 실패 시 예외처리
     **/
    class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
        override fun commence(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authException: AuthenticationException?
        ) {
            response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageUtil.getMessage("UNAUTHENTICATED_USER"))
        }
    }

    /**
     * 시큐리티 인가
     */
    class CustomAccessDeniedHandler() : AccessDeniedHandler {
        override fun handle(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            accessDeniedException: AccessDeniedException?
        ) {
            response?.sendError(HttpServletResponse.SC_FORBIDDEN,MessageUtil.getMessage("UNAUTHORIZED_ACCESS"))
        }
    }

    /**
     * 비밀번호 Encode
     **/
    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()


    /**
     * Cors 설정
     **/
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.addAllowedOriginPattern("http://localhost:3040")
        configuration.addAllowedMethod("*")
        configuration.addAllowedHeader("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L
        configuration.exposedHeaders = listOf("Content-Disposition")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    /**
     * 로그인 인증처리
     */
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(UserDetailsServiceImpl(userRepository))
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


    /**
     * 로그인이 없어도 API 통신이 필요 한 회원관리 API 목록
     **/
    private fun signAllowedList(): Array<String> {
        return arrayOf(
            "/api/sign-up",
            "/api/sign-in",
            "/api/find-password",
            "/api/answer-password",
            "/api/change-password/**",
            "/api/find/password",
            "/api/{userId}/change-password-after-find",
            "/api/captcha/success",
            "/api/sign-up/check"
        )
    }

    /**
     * Swagger 사용을 위한 허용 목록
     **/
    private fun swaggerAllowedList(): Array<String> {
        return arrayOf(
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/management/health",
            "/management/info"
        )
    }

    /**
     * 개발시에만 확인 필요한 API 허용 목록
     **/
    private fun devAllowedList(): Array<String> {
        return arrayOf(
            "/h2-console/**"
        )
    }

    /**
     * 관리자 API 호출 시 권한 체크
     **/
    private fun sysAdminAllowedList(): Array<String> {
        return arrayOf(
            "/api/admin/**",
        )
    }

}
