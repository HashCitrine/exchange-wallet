package com.exchange.api;

import com.exchange.postgres.entity.Bankstatement;
import com.exchange.postgres.service.BankstatementService;
import com.exchange.postgres.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BankstatementController {

    private final MemberService memberService;
    private final BankstatementService bankstatementService;

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public String getToken(@RequestParam("memberId") String memberId, @RequestParam("password") String password) {
        return memberService.getToken(memberId, password);
    }

    /*@PostMapping("/token/check")
    @ResponseStatus(HttpStatus.OK)
    public String tokenCheck(@RequestParam("token") String token){
        String result = memberService.getMemberRole(token);
        log.info("Role : " + result);
        return result;
    }*/

    @PostMapping("/member/depositAndWithdraw")
    @ResponseStatus(HttpStatus.OK)
    public String depositAndWithdraw(@RequestBody Bankstatement bankStatement){
        return bankstatementService.depositAndWithdraw(bankStatement);
    }
}