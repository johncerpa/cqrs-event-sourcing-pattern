package com.springbank.bankacc.cmd.api.controllers;

import com.springbank.bankacc.cmd.api.commands.DepositFundCommand;
import com.springbank.bankacc.cmd.api.dtos.OpenAccountResponse;
import com.springbank.bankacc.core.dtos.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/depositFundsController")
public class DepositFundsController {
    private final CommandGateway commandGateway;

    public DepositFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id, @Valid @RequestBody DepositFundCommand command) {
        try {
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new OpenAccountResponse(id, "Funds successfully deposited"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing to request to deposit funds into a bank account, for id " + id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new OpenAccountResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
