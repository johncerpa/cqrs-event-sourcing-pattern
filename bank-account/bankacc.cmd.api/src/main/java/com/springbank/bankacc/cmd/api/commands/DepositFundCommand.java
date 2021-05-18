package com.springbank.bankacc.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

@Data
@Builder
public class DepositFundCommand {
    @TargetAggregateIdentifier
    private String id;

    @Min(value = 1, message = "The deposit amount must be greater than 0")
    private double amount;


}
