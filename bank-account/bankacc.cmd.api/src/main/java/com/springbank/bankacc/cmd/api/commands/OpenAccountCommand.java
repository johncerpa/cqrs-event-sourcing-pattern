package com.springbank.bankacc.cmd.api.commands;

import com.springbank.bankacc.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

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

    private String accountHolderId;

    private AccountType accountType;

    private double openingBalance;
}
