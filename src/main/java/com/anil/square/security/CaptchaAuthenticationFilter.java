package com.anil.square.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

// import com.anil.square.Utils.RequestWrapper;

/**
 * The filter to verify captcha.
 */

public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
    private String processUrl;
    String hostname;

    public CaptchaAuthenticationFilter(String defaultFilterProcessesUrl, String failureUrl) {
        super(defaultFilterProcessesUrl);
        this.processUrl = defaultFilterProcessesUrl;
       // this.hostname=hostname;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
        if(processUrl.equals(req.getServletPath()) && "POST".equalsIgnoreCase(req.getMethod())){
            String expect = req.getSession().getAttribute("captcha").toString();
        	System.out.println("Generated Captcha-->"+expect);
            req.getSession().removeAttribute("captcha");
            // request=new RequestWrapper(request);
            if (expect != null && !expect.equals(req.getParameter("captcha"))){
            	System.out.println("Entered Captcha-->"+req.getParameter("captcha"));
            	req.getSession().setAttribute("msg", "Invalid Captcha");
                unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException("Enter Valid Captcha"));
                return;
            }
        }
        //added
        String requestUri = req.getRequestURI();
        String requestedWithHeader = req.getHeader("X-Requested-With");
        String queryString = req.getQueryString();
        if (requestUri.endsWith("/") && !requestUri.equals("/") && !requestUri.equals("/gujaratbhawan/") 
        		 && !"XMLHttpRequest".equals(requestedWithHeader) && queryString==null && !requestUri.contains("autoauth")) {
                   
           String redirectUrl = requestUri.substring(0, requestUri.length() - 1);
           res.sendRedirect("/gujaratbhawan/admin");//added
           return;
        } 
        // added end

       /* if ("HEAD".equalsIgnoreCase(req.getMethod()))
        {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        if ("OPTIONS".equalsIgnoreCase(req.getMethod()))
        {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        res.addHeader("Allow", "GET, POST");
        res.addHeader("Strict-Transport-Security", "31536000");

        chain.doFilter(request, response);*/
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            String existingAllowHeader = res.getHeader("Allow");
            if (existingAllowHeader == null) {
                res.addHeader("Allow", "GET, POST");
            } else {
                res.setHeader("Allow", "GET, POST");
            }
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if ("HEAD".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        res.addHeader("Allow", "GET, POST");
        res.addHeader("Strict-Transport-Security", "31536000");
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
