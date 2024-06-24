package ch.postfinance.swiss.hacks.resource;

import ch.postfinance.swiss.hacks.domain.Transaction;
import ch.postfinance.swiss.hacks.resource.beans.FundTransfer;
import ch.postfinance.swiss.hacks.resource.beans.TransactionHistory;
import ch.postfinance.swiss.hacks.resource.beans.TransferResponse;
import ch.postfinance.swiss.hacks.service.IllegalTransactionException;
import ch.postfinance.swiss.hacks.service.TransactionService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.slf4j.Logger;

import java.util.List;

import static ch.postfinance.swiss.hacks.domain.Login.ROLE_ADMIN;
import static ch.postfinance.swiss.hacks.domain.Login.ROLE_USER;
import static ch.postfinance.swiss.hacks.resource.beans.TransferResponse.Status.SUCCESS;
import static org.slf4j.LoggerFactory.getLogger;

@Authenticated
public class TransactionResourceImpl implements TransactionsResource {

    private static final Logger log = getLogger(TransactionResourceImpl.class);

    @Inject
    TransactionService transactionService;

    private static TransactionHistory convertToDTO(Transaction transaction) {
        var transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(transaction.transactionId);
        transactionHistory.setFromIban(transaction.fromIban);
        transactionHistory.setToIban(transaction.toIban);
        transactionHistory.setAmount(transaction.amount.doubleValue());
        transactionHistory.setDescription(transactionHistory.getDescription());
        return transactionHistory;
    }

    @Override
    @RolesAllowed(ROLE_USER)
    public TransferResponse transferFunds(FundTransfer fundTransfer) {
        var transferResponse = new TransferResponse();

        try {
            var transactionId = transactionService.transfer(fundTransfer.getFromIban(), fundTransfer.getToIban(), fundTransfer.getAmount(), fundTransfer.getDescription());

            transferResponse.setTransactionId(transactionId);
            transferResponse.setStatus(SUCCESS);
        } catch (IllegalTransactionException e) {
            log.warn("Submitting transaction failed!", e);

            transferResponse.setTransactionId(e.getTransactionId());
            transferResponse.setStatus(e.getStatus());

            throw new BadRequestException(
                    "Submitting transaction failed! Status: " + e.getStatus().toString(), e);
        }

        return transferResponse;
    }

    @Override
    @RolesAllowed({ROLE_ADMIN, ROLE_USER})
    public List<TransactionHistory> viewTransactionHistory() {
        return transactionService.getAllTransactions().stream()
                .map(TransactionResourceImpl::convertToDTO)
                .toList();
    }
}
