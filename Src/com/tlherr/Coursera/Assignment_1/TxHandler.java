
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
        UTXOPool utxoPool = getCurrentPool();

        System.out.println(String.format("Validating Transaction. %s Inputs %s Outputs", tx.numInputs(), tx.numOutputs()));

        int inputSum = 0;
        int outputSum = 0;

        //This set will hold all "claimed" txs, so we can enforce rule 3.
        Set<UTXO> claimedTXs = new HashSet<>();

        //Loop through all transaction inputs
        for(int i = 0; i < tx.numInputs(); i++) {

            Transaction.Input currentInput = tx.getInput(i);

            //If the input is null, then transaction cant be valid
            if(currentInput==null) {
                System.out.println("Input was null, invalid transaction");
                return false;
            }

            //Create the UTXO for this input, then check if it exists in the current pool
            UTXO utxo = new UTXO(currentInput.prevTxHash, currentInput.outputIndex);

            //If UTXO made from current TX is not in the pool or has already been claimed by another TX it is invalid
            if(!utxoPool.contains(utxo) || claimedTXs.contains(utxo)) {
                System.out.println("UTXO for current TX either not in pool or already claimed, invalid transaction");
                return false;
            }

            //Generated UTXO was in current pool and not already claimed, so claim it and move on
            claimedTXs.add(utxo);

            Transaction.Output currentOutput = utxoPool.getTxOutput(utxo);

            //To verify a signature, we need a public key, message (raw unsigned data) and a signature
            PublicKey pubkey = currentOutput.address;
            byte[] message = tx.getRawDataToSign(i);
            byte[] signature = currentInput.signature;

            //No signature = not valid transaction
            if(signature==null) {
                System.out.println("Unsigned input, invalid transaction");
                return false;
            }

            //If signature cannot be validated then return false
            if(!Crypto.verifySignature(pubkey, message, signature)) {
                System.out.println("Invalid Signature, invalid transaction");
                return false;
            }

            //If we have made it here then the input is valid (not claimed multiple times, signature checks out)
            System.out.println(String.format("Adding: %s to Input", currentOutput.value));
            inputSum += currentOutput.value;
        }

        //Loop through all transaction outputs
        for(int i = 0; i < tx.numOutputs(); i++) {

            Transaction.Output currentTxOutput = tx.getOutput(i);
            if(currentTxOutput.value<0) {
                System.out.println("Output values cannot be negative, invalid transaction");
                return false;
            }

            System.out.println(String.format("Adding: %s to Output", currentTxOutput.value));
            outputSum += currentTxOutput.value;
        }


        System.out.println(String.format("Checking Sums. Input: %s Output %s", inputSum, outputSum));
        //Sum of the transaction inputs is greater than the sum of the output values

        return outputSum <= inputSum;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current com.tlherr.Coursera.Assignment_1.UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS

        //Iterate over possible transactions
        //Pass each to validate
        //If it validates add it to an array of "accepted" transactions
        //and update the UTXO pool (collection of unspent transaction ouputs) so remove from pool
        //if the transaction is being "spent" and added to "accepted" transactions

        return null;
    }

}
