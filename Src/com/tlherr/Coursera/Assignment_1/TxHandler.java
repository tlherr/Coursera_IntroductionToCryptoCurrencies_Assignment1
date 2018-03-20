
package com.tlherr.Coursera.Assignment_1;

import java.util.Iterator;

/**
 * Implementation of handleTxs() should return mutually valid transaction set of maximal size (cant be enlarged simply by adding
 * more transactions). Does not need to compute set of maxumum size (one for which there is no larger mutually valid transaction set).
 *
 * Based on the transaction it has chosen to accept, handleTxs should also update its internal com.tlherr.Coursera.Assignment_1.UTXOPool to reflect the current
 * set of unspent transaction outputs, so that future calls to handleTxs and isValidTX() are able to correctly process/validate
 * transactions that claim outputs from transactions that were accepted in a pervious call to handleTxs()
 */
public class TxHandler {

    private UTXOPool currentPool;

    private void setCurrentPool(UTXOPool currentPool) {
        this.currentPool = currentPool;
    }

    private UTXOPool getCurrentPool() {
        return this.currentPool;
    }

    /**
     * Creates a public ledger whose current com.tlherr.Coursera.Assignment_1.UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the com.tlherr.Coursera.Assignment_1.UTXOPool(com.tlherr.Coursera.Assignment_1.UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {

        //Make defensive copy of provided pool
        this.setCurrentPool(new UTXOPool(utxoPool));
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current com.tlherr.Coursera.Assignment_1.UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no com.tlherr.Coursera.Assignment_1.UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {

        UTXOPool currentPool = getCurrentPool();

        //Check that all outputs in tx are in the current pool
        for (Iterator<Transaction.Output> i = tx.getOutputs().iterator(); i.hasNext();) {
            Transaction.Output current = i.next();


        }


        return false;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current com.tlherr.Coursera.Assignment_1.UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS

        return null;
    }

}
