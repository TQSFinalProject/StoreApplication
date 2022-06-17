package com.tqs.chateauduvin.controller;

import com.tqs.chateauduvin.config.TokenProvider;
import com.tqs.chateauduvin.model.AuthToken;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.model.LogInReq;
import com.tqs.chateauduvin.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private StoreService storeServ;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LogInReq loginrequest) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginrequest.getUsername(),
                        loginrequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveCustomer(@RequestBody Customer cust){
        Boolean bool = storeServ.getCustomerByUsername(cust.getUsername()) != null;
        if(bool) return ResponseEntity.status(409).body("Username already in use.");
        else {
            storeServ.saveCustomer(cust);
            return ResponseEntity.status(200).body("User registered successfully.");
        }
    }

    // In case we need role authorization
    // @PreAuthorize("hasRole('USER')")
    // @RequestMapping(value="/userping", method = RequestMethod.GET)
    // public String userPing(){
    //     return "Any User Can Read This";
    // }
}
