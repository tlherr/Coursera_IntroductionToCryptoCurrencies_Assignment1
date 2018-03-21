
package com.tlherr.Coursera.Assignment_1;

import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of handleTxs() should return mutually valid transaction set of maximal size (cant be enlarged simply by adding
 * more transactions). Does not need to compute set of maxumum size (one for which there is no larger mutually valid transaction set).
 *
 * Based on the transaction it has chosen to accept, handleTxs should also update its internal com.tlherr.Coursera.Assignment_1.UTXOPool to reflect the current
 * set of unspent transaction outputs, so that future calls to handleTxs and isValidTX() are able to correctly process/validate
 * transactions that claim outputs from transactions that were accepted in a pervious call to handleTxs()
 */
public class TxHandler {

    /**
     * Have to validate the following:
     *
     * 1) All outputs claimed in Transaction are in the current UTXO pool
     * 2) Signatures on each transaction input are valid
     * 3) No UTXOs are claimed multiple times by Transactions (Prevent Double Spending)
     * 4) None of the transaction output values are negative
     * 5) Sum of the transaction inputs is greater than the sum of the output values
     *
     * If any of these conditions are not met the entire transaction is invalid
     */
    private class TxValidator {

        /**
         * Check if all outputs are in the pool and that no UTXOs are claimed multiple times
         *
         * @param utxoPool  UTXOPool    Current set of unspent transactions
         * @param tx        Transaction Transactions
         * @return          Boolean     True if valid
         */
        public boolean isValid(UTXOPool utxoPool, Transaction tx) {

            int inputSum = 0;
            int outputSum = 0;

            //This set will hold all "claimed" txs, so we can enforce rule 3.
            Set<UTXO> claimedTXs = new HashSet<>();

            //Loop through all transaction inputs
            for(int i = 0; i < tx.numInputs(); i++) {

                Transaction.Input currentInput = tx.getInput(i);

                //If the input is null, then transaction cant be valid
                if(currentInput==null) {
                    return false;
                }

                //Create the UTXO for this input, then check if it exists in the current pool
                UTXO utxo = new UTXO(currentInput.prevTxHash, currentInput.outputIndex);

                //If UTXO made from current TX is not in the pool or has already been claimed by another TX it is invalid
                if(!utxoPool.contains(utxo) || claimedTXs.contains(utxo)) {
                    return false;
                }

                //Generated UTXO was in current pool and not already claimed, so claim it and move on
                claimedTXs.add(utxo);

                //To verify a signature, we need a public key, message (raw unsigned data) and a signature

                Transaction.Output currentOutput = utxoPool.getTxOutput(utxo);


                PublicKey pubkey = currentOutput.address;

                byte[] message = tx.getRawDataToSign(i);

                byte[] signature = currentInput.signature;

                //If signature cannot be validated then return false
                if(!Crypto.verifySignature(pubkey, message, signature)) {
                    return false;
                }

                //If we have made it here then the input is valid (not claimed multiple times, signature checks out)
                inputSum += currentOutput.value;
            }

            //Loop through all transaction outputs
            for(int i = 0; i < tx.numOutputs(); i++) {

                Transaction.Output currentOutput = tx.getOutput(i);
                if(currentOutput.value<0) {
                    return false;
                }
                outputSum+=currentOutput.value;
            }


            return inputSum < outputSum;
        }
    }







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

        TxValidator validator = new TxValidator();

        return validator.isValid(this.currentPool, tx);
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
