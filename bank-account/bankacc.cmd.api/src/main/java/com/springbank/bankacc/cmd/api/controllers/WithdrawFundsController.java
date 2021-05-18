package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.WithdrawFundCommand;
import com.springbank.bankacc.cmd.api.dtos.OpenAccountResponse;
import com.springbank.bankacc.core.dtos.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/withdrawFunds")
public class WithdrawFundsController {
    private final CommandGateway commandGateway;

    public WithdrawFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable String id, @Valid @RequestBody WithdrawFundCommand command) {
        try {
            command.setId(id);
            /*
             * When this command runs it might throw an exception if account has insufficient balance,
             * get() blocks the thread so we can handle the exception inside catch(), instead of just returning a successful response
             */
            commandGateway.send(command).get();
            return new ResponseEntity<>(new OpenAccountResponse(id, "Withdrawal successful"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing to request to withdraw funds on a bank account, for id " + id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new OpenAccountResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
