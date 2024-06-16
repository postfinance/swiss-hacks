package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status;

import java.util.UUID;

public class IllegalTransactionException extends Throwable {

    private final UUID transactionId;
    private final Status status;

    IllegalTransactionException(UUID transactionId, Status status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public Status getStatus() {
        return status;
    }
}
