package com.tqs.chateauduvin.controller;

import com.tqs.chateauduvin.config.TokenProvider;
import com.tqs.chateauduvin.dto.AuthTokenDTO;
import com.tqs.chateauduvin.dto.CustomerCreationDTO;
import com.tqs.chateauduvin.dto.CustomerDTO;
import com.tqs.chateauduvin.dto.LogInRequestDTO;
import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3002")
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
    public ResponseEntity<?> generateToken(@RequestBody LogInRequestDTO loginrequest) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginrequest.getUsername(),
                        loginrequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthTokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerCreationDTO cust){
        Boolean bool = storeServ.getCustomerByUsername(cust.getUsername()) != null;
        if(bool) return ResponseEntity.status(409).body("Username already in use.");
        else {
            storeServ.saveCustomer(cust.toCustomerEntity());
            return ResponseEntity.status(200).body("User registered successfully.");
        }
    }

    @GetMapping("/myprofile")
    public ResponseEntity<CustomerDTO> getCustomerDetails(@RequestHeader("authorization") String auth) {
        String token = auth.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Customer cust = storeServ.getCustomerByUsername(username);
        CustomerDTO response = CustomerDTO.fromCustomerEntity(cust);
        return ResponseEntity.ok().body(response);
    }

    // In case we need role authorization
    // @PreAuthorize("hasRole('USER')")
    // @RequestMapping(value="/userping", method = RequestMethod.GET)
    // public String userPing(){
    //     return "Any User Can Read This";
    // }
}
