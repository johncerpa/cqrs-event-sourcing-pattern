package com.springbank.bankacc.cmd.api.commands;

import com.springbank.bankacc.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OpenAccountCommand {
    /**
     * Identifier of entity instance that will handle a command
     * Each instance keeps track of the events that modified its own state
     * With this the state can be replayed at any time
     */
    @TargetAggregateIdentifier
    private String id;

    @NotNull(message = "accountHolderId was not supplied")
    private String accountHolderId;

    @NotNull(message = "accountType was not supplied")
    private AccountType accountType;

    @Min(value = 50, message = "openingBalance must be at least 50")
    private double openingBalance;
}
