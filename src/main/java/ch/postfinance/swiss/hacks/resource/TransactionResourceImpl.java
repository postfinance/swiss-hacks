package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.Transaction;
import ch.postfinance.swiss.hacks.resource.beans.FundTransfer;
import ch.postfinance.swiss.hacks.resource.beans.TransferResponse;
import ch.postfinance.swiss.hacks.service.IllegalTransactionException;
import ch.postfinance.swiss.hacks.service.TransactionService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.SUCCESS;

@Authenticated
public class TransactionResourceImpl implements TransactionsResource {

    private static final Logger log = LoggerFactory.getLogger(TransactionResourceImpl.class);

    @Inject
    TransactionService transactionService;

    @Override
    @RolesAllowed("user")
    public TransferResponse transferFunds(FundTransfer fundTransfer) {
        var transferResponse = new TransferResponse();

        try {
            var transactionId = transactionService.transfer(fundTransfer.getFromIban(), fundTransfer.getToIban(), fundTransfer.getAmount(), fundTransfer.getDescription());

            transferResponse.setTransactionId(transactionId);
            transferResponse.setStatus(SUCCESS);
        } catch (IllegalTransactionException e) {
            log.warn("Submitting transaction failed!", e);

            transferResponse.setTransactionId(e.getTransactionId().toString());
            transferResponse.setStatus(e.getStatus());

            throw new BadRequestException(
                    "Submitting transaction failed! Status: " + e.getStatus().toString(), e);
        }

        return transferResponse;
    }

    @Override
    @RolesAllowed("user")
    public Response viewTransactionHistory() {
        log.info("Fetching transaction history");
        List<Transaction> transactions = transactionService.getAllTransactions();
        log.info("Fetched {} transactions", transactions.size());
        return Response.ok(transactions).build();
    }
}
